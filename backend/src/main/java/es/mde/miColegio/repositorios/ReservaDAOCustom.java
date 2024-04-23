package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.List;
import es.mde.miColegio.entidades.Reserva;

public interface ReservaDAOCustom {
  /**
   * Función que devuelve si un lugar en una franja horaria está o no dispobible
   * 
   * @param lugar es la id del lugar
   * @param fecha es la fecha (yyyy-MM-dd) de la franja horaria
   * @param hora es la hora a la que empieza
   * */
  boolean isLugarDisponible(int lugar, LocalDate fecha, int hora);
  
  public List<Reserva> getReservasDeProfesorEntreFechas(int profesor, LocalDate fechaInicio, LocalDate fechaFin);
  
  public List<Reserva> getReservasDeGrupoEntreFechas(int grupo, LocalDate fechaInicio, LocalDate fechaFin);
  
}
