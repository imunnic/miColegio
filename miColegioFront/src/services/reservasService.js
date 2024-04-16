import axios from "axios";

const host = "http://localhost:8080/api";
const reservasEndPoint = "/reservas";
const search = "/search";

export default class reservasService {
  //TODO método que devuelva solo las reservas de un profesor

  getAll() {
    return axios.get(host + reservasEndPoint);
  }

  getReservasProfesor(href) {
    return axios.get(
      host + reservasEndPoint + search + "/findByProfesor?profesor=" + href
    );
  }

  isLugarDisponible(lugarId, fecha, hora) {
    return axios.get(
      host +
      reservasEndPoint +
      search +
      "/lugar-disponible?lugar=" +
      lugarId +
      "&fecha=" +
      fecha +
      "&hora=" +
      hora
    );
  }

  getLugaresNoDisponibles(fecha, hora) {
    return axios.get(
      host +
        reservasEndPoint +
        search +
        "/lugares-no-disponibles?fecha=" +
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
