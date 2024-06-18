package es.mde.miColegio.modelos;

import es.mde.miColegio.entidades.Reserva;

import java.time.LocalDate;

public class ReservaDisponibleResponse {
  private static final String URL= "https://micolegio-c6e07df12596.herokuapp.com/api/lugares/";
  private Long id;
  private int profesor;
  private int asignatura;
  private int grupo;
  private Long lugar;
  private LocalDate fecha;
  private int hora;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getProfesor() {
    return profesor;
  }

  public void setProfesor(int profesor) {
    this.profesor = profesor;
  }

  public int getAsignatura() {
    return asignatura;
  }

  public void setAsignatura(int asignatura) {
    this.asignatura = asignatura;
  }

  public int getGrupo() {
    return grupo;
  }

  public void setGrupo(int grupo) {
    this.grupo = grupo;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getLugar() {
    return URL+lugar;
  }

  public void setLugar(Long lugar) {
    this.lugar = lugar;
  }

  public void setHora(int hora) {
    this.hora = hora;
  }

  public int getHora() {
    return hora;
  }

  public ReservaDisponibleResponse(Reserva reserva){
    setId(reserva.getId());
    setAsignatura(reserva.getAsignatura());
    setGrupo(reserva.getGrupo());
    setProfesor(reserva.getProfesor());
    setLugar(reserva.getLugarId());
    setFecha(reserva.getFecha());
    setHora(reserva.getHora());
  }
}
