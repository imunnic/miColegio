package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.List;

public interface ReservaDAOCustom {
  boolean isLugarDisponible(int lugar, LocalDate fecha, int hora);
  List<Integer> getLugaresReservas(LocalDate fecha, int hora);
}
