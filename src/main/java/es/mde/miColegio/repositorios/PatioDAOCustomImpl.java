package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Aula;
import es.mde.miColegio.entidades.Patio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PatioDAOCustomImpl implements PatioDAOCustom{

  @Autowired
  PatioDAO patioDAO;
  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Patio> getPatios() {
    return patioDAO.findAll();
  }
}
