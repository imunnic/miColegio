import axios from "axios";

// const host = "https://micolegio-c6e07df12596.herokuapp.com/api";
const host = "http://localhost:8080/api"
const reservasEndPoint = "/reservas";
const search = "/search";
const reservas = host + reservasEndPoint;
const reservasSearch = reservas + search;
let config = {
  headers: {
    Authorization: "Bearer ",
  },
};

export default class reservasService {
  constructor(token) {
    this.actualizarCabecera(token);
  }

  actualizarCabecera(token) {
    config.headers.Authorization = config.headers.Authorization + token;
  }
  /**
   * Función que devuelve todas las reservas
   */
  getAll() {
    return axios.get(host + reservasEndPoint, config);
  }

  /**
   * Función que devuelve todas las reservas de un profesor
   */
  getReservasProfesor(href) {
    return axios.get(
      reservasSearch + "/findByProfesor?profesor=" + href,
      config
    );
  }

  /**
   * Función que devuelve todas las reservas de un grupo
   */
  getReservasGrupo(href) {
    return axios.get(
      reservasSearch + "/findByGrupo?grupo=" + href,
      config
    );
  }

  /**
   * Función que devuelve todas las reservas de un profesor entre dos fechas
   */
  getReservasProfesorEntre(href, periodo) {
    return axios.get(
      reservasSearch +
        "/reservas-profesor-fecha?profesorId=" +
        href +
        "&fechaInicio=" +
        periodo.start +
        "&fechaFin=" +
        periodo.end,
      config
    );
  }

  /**
   * Función que devuelve todas las reservas de un grupo entre dos fechas
   */
  getReservasGrupoEntre(href, periodo) {
    return axios.get(
      reservasSearch +
        "/reservas-grupo-fecha?grupoId=" +
        href +
        "&fechaInicio=" +
        periodo.start +
        "&fechaFin=" +
        periodo.end,
      config
    );
  }

  /**
   * Función que dice si un lugar está disponible o no
   */
  isLugarDisponible(href, franjaHoraria) {
    return axios.get(
      reservasSearch +
        "/lugar-disponible?lugar=" +
        href +
        "&fecha=" +
        franjaHoraria.fecha +
        "&hora=" +
        franjaHoraria.hora,
      config
    );
  }

  /**
   * Función que devuelve un mapa de fechas y horas a las que no se puede reservar
   */
  getReservasImposibleGrupo(filtro){
    return axios.post(
      reservasSearch + '/fechas-imposible-reservar-grupo',
      filtro, config
    );
  }

  /**
   * Función que devuelve un mapa de fechas y horas a las que no se puede reservar
   */
  getReservasImposibleLugar(filtro){
    return axios.post(
      reservasSearch + '/fechas-imposible-reservar-lugar',
      filtro, config
    );
  }

  create(reserva) {
    return axios.post(reservas, reserva, config);
  }

  delete(href) {
    return axios.delete(reservas + "/" + href, config);
  }

  update(id, data) {
    return axios.patch(reservas + "/" + id, data, config);
  }
}
