import axios from "axios";

// const host = "https://micolegio-c6e07df12596.herokuapp.com/api";
const host = "http://localhost:8080/api"
const lugaresEndPoint = host + "/lugares";
const aulasEndPoint = host + "/aulas";
const patiosEndPoint = host + "/patios";
const search = "/search";
const lugaresSearch = lugaresEndPoint + search;
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
   * Función que devuelve todos los lugares
   */
  getAll() {
    return axios.get(lugaresSearch + '/getLugares', config);
  }
  
  create(lugar) {
    if (lugar.tipo == 'Aula'){
      return axios.post(aulasEndPoint, lugar, config);
    } else {
      return axios.post(patiosEndPoint, lugar, config);
    }
  }

  delete(href) {
    return axios.delete(href, config);
  }

  update(href, data) {
    return axios.put(href, data, config);
  }
}
