package es.mde.miColegio.entidades;

import jakarta.persistence.*;

import java.util.Set;

//Entidad que representa un lugar
@Entity
@Table(name="LUGARES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO",
    discriminatorType = DiscriminatorType.STRING)
public class Lugar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private int capacidad;
  @OneToMany(mappedBy = "lugar")
  private Set<Reserva> reservas;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getCapacidad() {
    return capacidad;
  }

  public void setCapacidad(int capacidad) {
    this.capacidad = capacidad;
  }

  public Long getIdentificacion() {
    return getId();
  }

  public Lugar() {
  }

  @Override
  public String toString() {
    return getNombre();
  }
}
