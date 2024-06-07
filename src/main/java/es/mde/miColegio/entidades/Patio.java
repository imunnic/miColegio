package es.mde.miColegio.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

//Entidad que representa un lugar tipo Patio
@Entity
@DiscriminatorValue("PATIO")
public class Patio extends Lugar {
  private List<String> deportes;

  public List<String> getDeportes() {
    return deportes;
  }

  public void setDeportes(List<String> deportes) {
    this.deportes = deportes;
  }

  public Patio() {
    super();
  }
}
