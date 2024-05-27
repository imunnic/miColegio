package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Aula;
import es.mde.miColegio.entidades.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "aulas", itemResourceRel = "aula", collectionResourceRel = "aulas")
public interface AulaDAO extends JpaRepository<Aula, Long>, AulaDAOCustom {
  public List<Aula> findByNombre(String txt);
}
