package es.mde.miColegio.repositorios;

import java.time.LocalDate;
import java.util.List;

public interface ReservaDAOCustom {
  boolean isReservaPosible(int lugar, int grupo, LocalDate fecha, int hora);
  List<Integer> getLugaresReservas(LocalDate fecha, int hora);
}
