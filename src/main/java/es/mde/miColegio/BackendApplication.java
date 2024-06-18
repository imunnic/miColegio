package es.mde.miColegio;

import com.fasterxml.jackson.core.type.TypeReference;
import es.mde.miColegio.modelos.Asignatura;
import es.mde.miColegio.modelos.Grupo;
import es.mde.miColegio.modelos.Profesor;
import es.mde.miColegio.servicios.ServicioDatosIniciales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
