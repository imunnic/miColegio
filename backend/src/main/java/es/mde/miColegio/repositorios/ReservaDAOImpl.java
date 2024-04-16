package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
  public List<Integer> getLugaresReservas(LocalDate fecha, int hora) {
    List<Reserva> reservas = reservaDAO.findByFechaAndHora(fecha, hora);
    List<Integer> lugaresReservados = new ArrayList<Integer>();
    reservas.forEach(r -> lugaresReservados.add(r.getLugar()));
    return lugaresReservados;
  }

}
