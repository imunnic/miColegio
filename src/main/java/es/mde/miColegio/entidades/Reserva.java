package es.mde.miColegio.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "RESERVAS",
uniqueConstraints = {
    @UniqueConstraint(columnNames = {"grupo","fecha","hora"}),
    @UniqueConstraint(columnNames = {"lugar", "fecha", "hora"})
})
public class Reserva {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int profesor;
  private int asignatura;
  private int grupo;
  @ManyToOne
  @JoinColumn(name = "lugar_id", nullable = false)
  private Lugar lugar;
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
  
  public Lugar getLugar() {
    return lugar;
  }

  public Long getLugarId() {
    return getLugar().getId();
  }
  
  public void setLugar(Lugar lugar) {
    this.lugar = lugar;
  }
  
  public void setHora(int hora) {
    this.hora = hora;
  }
  
  public int getHora() {
    return hora;
  }
  public Long getIdentificacion() {
    return getId();
  }
  
  public Reserva() {
  }
  
  public Reserva(int profesor, int grupo, int asignatura, Lugar lugar, LocalDate fecha, int hora) {
    setAsignatura(asignatura);
    setFecha(fecha);
    setGrupo(grupo);
    setLugar(lugar);
    setProfesor(profesor);
    setHora(hora);
  }
  
}
