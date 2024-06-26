package es.mde.miColegio.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

//Entidad que representa un lugar tipo Aula
@Entity
@DiscriminatorValue("AULA")
public class Aula extends Lugar {
  private boolean proyector;

  public boolean isProyector() {
    return proyector;
  }

  public void setProyector(boolean proyector) {
    this.proyector = proyector;
  }

  public Aula() {
    super();
  }
}
