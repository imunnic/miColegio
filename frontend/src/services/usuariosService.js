import axios from "axios";

 const host = "https://micolegio-c6e07df12596.herokuapp.com/api";
//const host = "http://localhost:8080/api"
const autenticacion = "/auth";
const loginurl = "/login";
const register = "/register";
const usuarios = "/usuarios/search";
const find = "/findByUsername?username=";
let config = {
  headers: {
    Authorization: "",
  },
};

export default class UsuariosService {
  login(login) {
    return axios.post(host + autenticacion + loginurl, login);
  }

  getIdUsuario(username, token) {
    config.headers.Authorization = "Bearer " + token;
    return axios.get(host + usuarios + find + username, config);
  }
}
