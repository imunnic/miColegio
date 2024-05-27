package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Lugar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LugarDAOCustomImpl implements LugarDAOCustom{

  @Autowired
  LugarDAO lugarDAO;
  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Lugar> getLugares() {
    return lugarDAO.findAll();
  }
}
