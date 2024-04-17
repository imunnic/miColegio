package es.mde.miColegio.rest;

import java.time.LocalDate;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
  public ResponseEntity<Boolean> isReservaPosible(@RequestParam("lugar") int lugar,
      @RequestParam("fecha") LocalDate fecha, @RequestParam("hora") int hora) {
    Boolean retorno = reservaDAO.isLugarDisponible(lugar, fecha, hora);
    return new ResponseEntity<Boolean>(retorno, HttpStatus.OK);
  }

}
