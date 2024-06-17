package es.mde.miColegio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.mde.miColegio.entidades.Asignatura;
import es.mde.miColegio.entidades.Grupo;
import es.mde.miColegio.entidades.Profesor;
import es.mde.miColegio.repositorios.ServicioDatosIniciales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendApplication {
  private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);
  private static List<Asignatura> asignaturas = new ArrayList<>();
  private static List<Grupo> grupos = new ArrayList<>();
  private static List<Profesor> profesores = new ArrayList<>();
  private static ServicioDatosIniciales servicioInicial = new ServicioDatosIniciales();


  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);

    asignaturas = servicioInicial.getDatosIniciales("asignaturas.json", new TypeReference<List<Asignatura>>() {});
    profesores = servicioInicial.getDatosIniciales("profesores.json", new TypeReference<List<Profesor>>() {});
    grupos = servicioInicial.getDatosIniciales("grupos.json", new TypeReference<List<Grupo>>() {});
  }

  public static List<Asignatura> getAsignaturas() {
    return asignaturas;
  }

  public static List<Grupo> getGrupos() {
    return grupos;
  }

  public static List<Profesor> getProfesores() {
    return profesores;
  }

}
