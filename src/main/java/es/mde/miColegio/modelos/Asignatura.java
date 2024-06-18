package es.mde.miColegio.modelos;

import java.util.List;

public class Asignatura {
  private int id;
  private String nombre;
  private List<Long> lugares;
  private List<Integer> grupos;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Long> getLugares() {
    return lugares;
  }

  public void setLugares(List<Long> lugares) {
    this.lugares = lugares;
  }

  public List<Integer> getGrupos() {
    return grupos;
  }

  public void setGrupos(List<Integer> grupos) {
    this.grupos = grupos;
  }

  @Override
  public String toString() {
    return nombre;
  }
}
