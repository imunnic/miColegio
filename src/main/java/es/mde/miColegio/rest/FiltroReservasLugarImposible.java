package es.mde.miColegio.rest;

import java.time.LocalDate;
import java.util.List;

public class FiltroReservasLugarImposible {
  private LocalDate fechaInicio;
  private java.time.LocalDate fechaFin;
  private List<Integer> lugaresId;

  public LocalDate getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDate fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public LocalDate getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
  }

  public List<Integer> getLugaresId() {
    return lugaresId;
  }

  public void setGrupos(List<Integer> lugaresId) {
    this.lugaresId = lugaresId;
  }

  public FiltroReservasLugarImposible() {
  }
}
