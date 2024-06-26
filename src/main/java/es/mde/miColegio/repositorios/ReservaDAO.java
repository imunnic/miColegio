package es.mde.miColegio.repositorios;

import java.util.List;

import es.mde.miColegio.entidades.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.miColegio.entidades.Reserva;

import java.time.LocalDate;


@RepositoryRestResource(path = "reservas", itemResourceRel = "reserva",
    collectionResourceRel = "reservas")
public interface ReservaDAO extends JpaRepository<Reserva, Long>, ReservaDAOCustom {

  List<Reserva> findByProfesor(int profesor);

  List<Reserva> findByGrupo(int grupo);

  List<Reserva> findByLugar(Lugar lugar);

  List<Reserva> findByFechaAndHora(LocalDate fecha, int hora);

  List<Reserva> findByGrupoAndFechaAndHora(int grupo, LocalDate fecha, int hora);

  List<Reserva> findByLugarAndFechaAndHora(Lugar lugar, LocalDate fecha, int hora);

}
