package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Lugar;
import es.mde.miColegio.entidades.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "patios", itemResourceRel = "patio", collectionResourceRel = "patios")
public interface PatioDAO extends JpaRepository<Patio, Long>, PatioDAOCustom {
  public List<Patio> findByNombre(String txt);
}
