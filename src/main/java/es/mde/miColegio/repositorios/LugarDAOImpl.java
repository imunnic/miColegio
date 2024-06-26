package es.mde.miColegio.repositorios;

import es.mde.miColegio.entidades.Lugar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class LugarDAOImpl implements LugarDAOCustom{

  @Autowired
  LugarDAO lugarDAO;
  @PersistenceContext
  EntityManager entityManager;

  /**
   * Función que devuelve el listado de lugares sin paginar
   * @return listado de lugares sin paginar
   */
  @Override
  public List<Lugar> getLugares() {
    return lugarDAO.findAll();
  }
}
