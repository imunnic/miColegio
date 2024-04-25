package es.mde.miColegio.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MixIns {

  public interface Usuarios{
    @JsonIgnore
    String getPassword();
  }

}
