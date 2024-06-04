package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.mde.miColegio.entidades.Lugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import es.mde.miColegio.entidades.Reserva;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional(readOnly = true)
public class ReservaDAOImpl implements ReservaDAOCustom {

  @Autowired
  private LugarDAO lugarDAO;
  @Autowired
  private ReservaDAO reservaDAO;
  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Función que devuelve si un lugar está disponible o no para una franja horaria determinada
   * @param lugar el lugar de la reserva
   * @param fecha fecha de la franja horaria
   * @param hora hora de la franja horaria
   * @return booleano
   * */
  @Override
  public boolean isLugarDisponible(Lugar lugar, LocalDate fecha, int hora) {
    return (reservaDAO.findByLugarAndFechaAndHora(lugar,fecha,hora).isEmpty());
  }

  /**
   * Función que devuelve las reservas de un profesor entre dos fechas dadas
   * @param fechaInicio fecha de inicio del intervalo para la búsqueda de reservas
   * @param fechaFin fecha de fin del intervalo para la búsqueda de reservas
   * @param profesor id del profesor para la búsqueda
   * @return Lista de reservas
   * */
  @Override
  public List<Reserva> getReservasDeProfesorEntreFechas(int profesor, LocalDate fechaInicio,
      LocalDate fechaFin) {
    List<Reserva> reservas = new ArrayList<>();
    reservas = reservaDAO.findByProfesor(profesor)
                         .stream()
                         .filter(p -> p.getFecha().isAfter(fechaInicio))
                         .filter(p -> p.getFecha().isBefore(fechaFin) || p.getFecha().isEqual(fechaFin))
                         .collect(Collectors.toList());
    return reservas;
  }

  /**
   * Función que devuelve las reservas de un grupo entre dos fechas dadas
   * @param fechaInicio fecha de inicio del intervalo para la búsqueda de reservas
   * @param fechaFin fecha de fin del intervalo para la búsqueda de reservas
   * @param grupo id del grupo para la búsqueda
   * @return Lista de reservas
   * */
  @Override
  public List<Reserva> getReservasDeGrupoEntreFechas(int grupo, LocalDate fechaInicio,
      LocalDate fechaFin) {
    List<Reserva> reservas = new ArrayList<>();
    reservas = reservaDAO.findByGrupo(grupo)
                         .stream()
                         .filter(p -> p.getFecha().isAfter(fechaInicio))
                         .filter(p -> p.getFecha().isBefore(fechaFin) ||
                             p.getFecha().isEqual(fechaFin))
                         .collect(Collectors.toList());
    return reservas;
  }

  /**
   * Función que devuelve las fechas y horas a las que no es posible reservar para ningún grupo
   * del listado de grupos enviado
   * @param grupos listado de ids de los grupos sobre los que se quiere hacer la consulta
   * @param fechaInicio la fecha de inicio de la consulta
   * @param fechaFin la fecha de fin de la consulta
   * @return un mapa de la fechas fechas y horas correspondientes a las que no se puede reservar
   * para ninguno de los grupos dados
   * */
  @Override
  public Map<LocalDate, List<Integer>> getFechasHorasReservadasPorGrupos(List<Integer> grupos,
      LocalDate fechaInicio, LocalDate fechaFin) {
    Map<LocalDate, List<Integer>> fechasHorasReservadas = new HashMap<>();
    int horaInicio = 9;
    int horaFin = 14;
    LocalDate fechaActual = fechaInicio;

    while (!fechaActual.isAfter(fechaFin)) {
      List<Integer> horasReservadas = new ArrayList<>();
      for (int hora = horaInicio; hora <= horaFin; hora++) {
        boolean todasLasReservasHechas = true;
        for (Integer grupo : grupos) {
          List<Reserva> reservas = reservaDAO.findByGrupoAndFechaAndHora(grupo, fechaActual, hora);
          if (reservas.isEmpty()) {
            todasLasReservasHechas = false;
            break;
          }
        }
        if (todasLasReservasHechas) {
          horasReservadas.add(hora);
        }
      }
      fechasHorasReservadas.put(fechaActual, horasReservadas);
      fechaActual = fechaActual.plusDays(1);
    }

    return fechasHorasReservadas;
  }

  @Override
  public Map<LocalDate, List<Integer>> getFechasHorasReservadasPorLugares(List<Integer> lugares,
      LocalDate fechaInicio, LocalDate fechaFin) {
    Map<LocalDate, List<Integer>> fechasHorasReservadas = new HashMap<>();
    int horaInicio = 9;
    int horaFin = 14;
    LocalDate fechaActual = fechaInicio;

    while (!fechaActual.isAfter(fechaFin)) {
      List<Integer> horasReservadas = new ArrayList<>();
      for (int hora = horaInicio; hora <= horaFin; hora++) {
        boolean todasLasReservasHechas = true;
        for (Integer lugarId : lugares) {
          Lugar lugar = lugarDAO.getById(Long.valueOf(lugarId));
          if (reservaDAO.isLugarDisponible(lugar,fechaActual,hora)) {
            todasLasReservasHechas = false;
            break;
          }
        }
        if (todasLasReservasHechas) {
          horasReservadas.add(hora);
        }
      }
      fechasHorasReservadas.put(fechaActual, horasReservadas);
      fechaActual = fechaActual.plusDays(1);
    }

    return fechasHorasReservadas;
  }

}
