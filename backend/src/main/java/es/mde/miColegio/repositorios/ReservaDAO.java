package es.mde.miColegio.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.miColegio.entidades.Reserva;

@RepositoryRestResource(path="reservas", itemResourceRel = "reserva", collectionResourceRel = "reservas")
public interface ReservaDAO extends JpaRepository<Reserva, Long>{
  List<Reserva> findByProfesor(int profesor);
}
