package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import es.mde.miColegio.entidades.Lugar;
import es.mde.miColegio.entidades.Reserva;

public interface ReservaDAOCustom {
  /**
   * Función que devuelve si un lugar en una franja horaria está o no dispobible
   *
   * @param lugar es la id del lugar
   * @param fecha es la fecha (yyyy-MM-dd) de la franja horaria
   * @param hora  es la hora a la que empieza
   */
  boolean isLugarDisponible(Lugar lugar, LocalDate fecha, int hora);

  boolean isGrupoDisponible(int grupoId, LocalDate fecha, int hora);

  List<Reserva> getReservasDeProfesorEntreFechas(int profesor, LocalDate fechaInicio,
      LocalDate fechaFin);

  List<Reserva> getReservasDeGrupoEntreFechas(int grupo, LocalDate fechaInicio,
      LocalDate fechaFin);

  Map<LocalDate, List<Integer>> getFechasHorasReservadasPorGrupos(List<Integer> grupos,
      LocalDate fechaInicio, LocalDate fechaFin);

  Map<LocalDate, List<Integer>> getFechasHorasReservadasPorLugares(List<Integer> lugares,
      LocalDate fechaInicio, LocalDate fechaFin);

  List<Reserva> consultarDisponible(int profesorID, LocalDate fechaInicio, LocalDate fechafin);

  List<Integer> getGruposReservasPorFecha(LocalDate fecha, int hora);
  List<Long> getLugaresReservasPorFecha(LocalDate fecha, int hora);
}
