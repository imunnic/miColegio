package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Aula;
import es.mde.miColegio.entidades.Lugar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AulaDAOCustomImpl implements AulaDAOCustom{
  @Autowired
  AulaDAO aulaDAO;
  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Aula> getAulas() {
    return aulaDAO.findAll();
  }
}
