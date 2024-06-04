package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "lugares", itemResourceRel = "lugar",
    collectionResourceRel = "lugares")
public interface LugarDAO extends JpaRepository<Lugar, Long>, LugarDAOCustom {
  public List<Lugar> findByNombre(String txt);
  public Lugar getById(Long id);
  public List<Lugar> findAll();
}
