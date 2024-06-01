package es.mde.miColegio.rest;

import java.time.LocalDate;
import java.util.List;

public class FiltroReservasGrupoImposible {
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private List<Integer> grupos;

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

  public List<Integer> getGrupos() {
    return grupos;
  }

  public void setGrupos(List<Integer> grupos) {
    this.grupos = grupos;
  }

  public FiltroReservasGrupoImposible() {
  }
}
