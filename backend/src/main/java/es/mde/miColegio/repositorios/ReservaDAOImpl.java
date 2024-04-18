package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import es.mde.miColegio.entidades.Reserva;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional(readOnly = true)
public class ReservaDAOImpl implements ReservaDAOCustom{
  
  @Autowired
  private ReservaDAO reservaDAO;
  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public boolean isLugarDisponible(int lugar, LocalDate fecha, int hora) {
    return (reservaDAO.findByLugarAndFechaAndHora(lugar, fecha, hora).size() == 0);
  }

  @Override
  public List<Reserva> getReservasDeProfesorEntreFechas(int profesor, LocalDate fechaInicio,
      LocalDate fechaFin) {
    List<Reserva> reservas = new ArrayList<Reserva>();
    reservas = reservaDAO.findByProfesor(profesor)
                         .stream()
                         .filter(p -> {
                           return p.getFecha().isAfter(fechaInicio);
                         })
                         .filter(p -> {
                           return p.getFecha().isBefore(fechaFin) ||
                               p.getFecha().isEqual(fechaFin);
                         })
                         .collect(Collectors.toList());
    return reservas;
  }

  @Override
  public List<Reserva> getReservasDeGrupoEntreFechas(int grupo, LocalDate fechaInicio,
      LocalDate fechaFin) {
    List<Reserva> reservas = new ArrayList<Reserva>();
    reservas = reservaDAO.findByGrupo(grupo)
                         .stream()
                         .filter(p -> {
                           return p.getFecha().isAfter(fechaInicio) ;
                         })
                         .filter(p -> {
                           return p.getFecha().isBefore(fechaFin)||
                               p.getFecha().isEqual(fechaInicio);
                         })
                         .collect(Collectors.toList());
    return reservas;
  }

}
