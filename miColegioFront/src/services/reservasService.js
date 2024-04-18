import axios from "axios";

const host = "http://localhost:8080/api";
const reservasEndPoint = "/reservas";
const search = "/search";

export default class reservasService {

  getAll() {
    return axios.get(host + reservasEndPoint);
  }

  getReservasProfesor(href) {
    return axios.get(
      host + reservasEndPoint + search + "/findByProfesor?profesor=" + href
    );
  }

  getReservasGrupo(href) {
    return axios.get(
      host + reservasEndPoint + search + "/findByGrupo?grupo=" + href
    );
  }

  getReservasProfesorEntre(href, fechaInicio, fechaFin) {
    return axios.get(
      host + reservasEndPoint + search + "/reservas-profesor-fecha?profesorId=" + href 
      + "&fechaInicio=" + fechaInicio 
      + "&fechaFin=" + fechaFin
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
      hora
    );
  }

  create(reserva) {
    return axios.post(host + reservasEndPoint, reserva);
  }

  delete(href) {
    return axios.delete(href);
  }

  update(id, lugarData) {
    return axios.put(host + reservasEndPoint + "/" + id, lugarData);
  }
}
