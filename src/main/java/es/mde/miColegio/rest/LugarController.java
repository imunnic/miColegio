package es.mde.miColegio.rest;

import es.mde.miColegio.entidades.Lugar;
import es.mde.miColegio.repositorios.LugarDAO;
import es.mde.miColegio.repositorios.ReservaDAO;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestController
@Configuration
public class LugarController {

  private LugarDAO lugarDAO;

  public LugarController(LugarDAO lugarDAO) {
    this.lugarDAO = lugarDAO;
  }

  @GetMapping("/lugares/search/getLugares")
  @ResponseBody
  public CollectionModel<PersistentEntityResource> getLugares(
      PersistentEntityResourceAssembler assembler) {
    List<Lugar> lugares = lugarDAO.getLugares();
    return assembler.toCollectionModel(lugares);
  }

}
