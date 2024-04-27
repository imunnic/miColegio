package es.mde.miColegio.seguridad.usuarios;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path="usuarios", collectionResourceRel = "usuarios", itemResourceRel = "usuario")
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByUsername(String username);

  @RestResource(exported = false)
  <S extends Usuario> List<S> findAll(Example<S> example);

  @RestResource(exported = false)
  <S extends Usuario> S save(S entity);

  @RestResource(exported = false)
  void delete(Usuario entity);
}
