package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.mde.miColegio.BackendApplication;
import es.mde.miColegio.entidades.Asignatura;
import es.mde.miColegio.entidades.Lugar;
import es.mde.miColegio.entidades.Profesor;
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

  @Override
  public boolean isGrupoDisponible(int grupoId, LocalDate fecha, int hora) {
    return (reservaDAO.findByGrupoAndFechaAndHora(grupoId,fecha,hora).isEmpty());
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

  /**
   * Función que devuelve un mapa que asocia fechas a los lugares reservados
   * @param lugares listado de ids de los lugares sobre los que se quiere hacer la consulta
   * @param fechaInicio la fecha de inicio de la consulta
   * @param fechaFin la fecha de fin de la consulta
   * @return un mapa de la fechas fechas y horas correspondientes a las que no se puede reservar
   * para ninguno de los lugares dados
   * */
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

  @Override
  public List<Reserva> consultarDisponible(int profesorId, LocalDate fechaInicio,
      LocalDate fechaFin) {

    int horaInicio = 9;
    int horaFin = 14;
    List<Reserva> reservasPosibles = new ArrayList<>();
    Profesor profesor = BackendApplication.getProfesores().stream()
        .filter(p -> p.getId() == profesorId)
        .findFirst()
        .orElse(null);

    if (profesor == null) {
      return reservasPosibles;
    }
    List<Integer> asignaturasProfesor = profesor.getAsignaturas();
    for (int asignaturaId : asignaturasProfesor) {
      Asignatura asignatura = BackendApplication.getAsignaturas().stream()
          .filter(a -> a.getId() == asignaturaId)
          .findFirst()
          .orElse(null);

      if (asignatura == null) {
        continue;
      }

      List<Integer> gruposAsignatura = asignatura.getGrupos();
      List<Long> lugaresAsignatura = asignatura.getLugares();
      List<Lugar> lugares = new ArrayList<>();
      lugaresAsignatura.forEach(l -> lugares.add(lugarDAO.getById(l)));

      for (int grupoId : gruposAsignatura) {
        for (Lugar lugar : lugares) {
        LocalDate fechaActual = fechaInicio;
        while (!fechaActual.isAfter(fechaFin)) {
          for (int hora = horaInicio; hora <= horaFin; hora++) {
            if (isLugarDisponible(lugar, fechaActual, hora) && isGrupoDisponible(grupoId, fechaActual, hora)) {
              Reserva reserva = new Reserva(profesorId, grupoId, asignaturaId, lugar, fechaActual, hora);
              reservasPosibles.add(reserva);
            }
          }
          fechaActual = fechaActual.plusDays(1);
        }

        }
      }
    }

    return reservasPosibles;
  }



  /**
   * Función que los grupos reservados para una fecha y hora concreta
   * @param fecha la fecha de la consulta
   * @param hora la hora de la consulta
   * @return un array con los grupos reservados
   * */
  @Override
  public List<Integer> getGruposReservasPorFecha(LocalDate fecha, int hora) {
    List<Integer> grupos = new ArrayList<>();
    List<Reserva> reservas = reservaDAO.findByFechaAndHora(fecha, hora);
    reservas.forEach(r -> grupos.add(r.getGrupo()));
    return grupos;
  }

  /**
   * Función que los lugares reservados para una fecha y hora concreta
   * @param fecha la fecha de la consulta
   * @param hora la hora de la consulta
   * @return un array con los lugares reservados
   * */
  @Override
  public List<Long> getLugaresReservasPorFecha(LocalDate fecha, int hora) {
    List<Long> lugares = new ArrayList<>();
    List<Reserva> reservas = reservaDAO.findByFechaAndHora(fecha, hora);
    reservas.forEach(l -> lugares.add(l.getLugarId()));
    return lugares;
  }

}
