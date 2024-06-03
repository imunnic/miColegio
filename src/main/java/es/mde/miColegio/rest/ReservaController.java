package es.mde.miColegio.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import es.mde.miColegio.entidades.Lugar;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.mde.miColegio.entidades.Reserva;
import es.mde.miColegio.repositorios.ReservaDAO;

@RepositoryRestController
@Configuration
public class ReservaController {

  private ReservaDAO reservaDAO;

  public ReservaController(ReservaDAO reservaDAO) {
    this.reservaDAO = reservaDAO;
  }

  @GetMapping("/reservas/search/lugar-disponible")
  @ResponseBody
  public ResponseEntity<Boolean> isReservaPosible(@RequestParam("lugar") Lugar lugar,
      @RequestParam("fecha") LocalDate fecha, @RequestParam("hora") int hora) {
    Boolean retorno = reservaDAO.isLugarDisponible(lugar, fecha, hora);
    return new ResponseEntity<Boolean>(retorno, HttpStatus.OK);
  }

  @GetMapping("/reservas/search/reservas-profesor-fecha")
  @ResponseBody
  public CollectionModel<PersistentEntityResource> getReservasDeProfesorEntreFechas(
      @RequestParam("profesorId") int profesor, @RequestParam("fechaInicio") LocalDate inicio,
      @RequestParam("fechaFin") LocalDate fin, PersistentEntityResourceAssembler assembler) {
    List<Reserva> reservas = reservaDAO.getReservasDeProfesorEntreFechas(profesor, inicio, fin);
    return assembler.toCollectionModel(reservas);
  }

  @GetMapping("/reservas/search/reservas-grupo-fecha")
  @ResponseBody
  public CollectionModel<PersistentEntityResource> getReservasDeGrupoEntreFechas(
      @RequestParam("grupoId") int grupo, @RequestParam("fechaInicio") LocalDate inicio,
      @RequestParam("fechaFin") LocalDate fin, PersistentEntityResourceAssembler assembler) {
    List<Reserva> reservas = reservaDAO.getReservasDeGrupoEntreFechas(grupo, inicio, fin);
    return assembler.toCollectionModel(reservas);
  }

  @PostMapping("/reservas/search/fechas-imposible-reservar-grupo")
  @ResponseBody
  public ResponseEntity<Map<LocalDate, List<Integer>>> getFechasImposibleReservarGrupo(
      @RequestBody FiltroReservasGrupoImposible filtroReservas) {
    Map<LocalDate, List<Integer>> fechasHorasReservadas =
        reservaDAO.getFechasHorasReservadasPorGrupos(
            filtroReservas.getGrupos(),
            filtroReservas.getFechaInicio(),
            filtroReservas.getFechaFin());

    return new ResponseEntity<>(fechasHorasReservadas, HttpStatus.OK);
  }
}
