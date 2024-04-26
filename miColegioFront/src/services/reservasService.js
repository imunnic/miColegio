import axios from "axios";
import {useUsuariosStore} from '../store/usuarioStore';

const host = "http://localhost:8080/api";
const reservasEndPoint = "/reservas";
const search = "/search";
let config = {
  headers: {
      Authorization:'Bearer '
  }
}

export default class reservasService {

  constructor(token){
    this.actualizarCabecera(token);
  }

  actualizarCabecera(token){
    config.headers.Authorization = config.headers.Authorization + token;
  }

  getAll() {
    return axios.get(host + reservasEndPoint, config);
  }

  getReservasProfesor(href) {
    return axios.get(
      host + reservasEndPoint + search + "/findByProfesor?profesor=" + href, config
    );
  }

  getReservasGrupo(href) {
    return axios.get(
      host + reservasEndPoint + search + "/findByGrupo?grupo=" + href, config
    );
  }

  async getReservasProfesorEntre(href, fechaInicio, fechaFin) {
    return await axios.get(
      host + reservasEndPoint + search + "/reservas-profesor-fecha?profesorId=" + href 
      + "&fechaInicio=" + fechaInicio 
      + "&fechaFin=" + fechaFin
    );
  }

  getReservasGrupoEntre(href, fechaInicio, fechaFin) {
    return axios.get(
      host + reservasEndPoint + search + "/reservas-grupo-fecha?grupoId=" + href 
      + "&fechaInicio=" + fechaInicio 
      + "&fechaFin=" + fechaFin,
      config
    );
  }

  isLugarDisponible(lugarId, fecha, hora) {
    return axios.get(
      host +
      reservasEndPoint +
      search +
      "/lugar-disponible?lugarId=" +
      lugarId +
      "&fecha=" +
      fecha +
      "&hora=" +
      hora,
      config
    );
  }

  create(reserva) {
    return axios.post(host + reservasEndPoint, reserva, config);
  }

  delete(href) {
    return axios.delete(host + reservasEndPoint + '/' + href, config);
  }

  update(id, lugarData) {
    return axios.put(host + reservasEndPoint + "/" + id, lugarData, config);
  }
}
