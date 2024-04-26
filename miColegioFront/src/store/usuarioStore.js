import { defineStore } from "pinia";
import UsuariosService from "../services/usuariosService";
import { useProfesoresStore } from "./profesoresStore";

export const useUsuariosStore = defineStore("usuarios", {
  state: () => ({
    username: "",
    token: "",
    isLogged: false,
    profesorId: null,
    usuarioService: new UsuariosService(),
  }),

  actions: {
    async peticionLogin(login) {
      await this.usuarioService
        .login(login)
        .then((response) => {
          if (response.status == 200) {
            this.token = response.data.token;
            this.username = response.data.username;
            this.isLogged = true;
          }
        })
        .then(() => this.obtenerIdProfesor())
        .catch((error) => {
          console.log(error);
        });
    },
    async obtenerIdProfesor() {
      await this.usuarioService
        .getIdUsuario(this.username, this.token)
        .then((response) => {
          this.profesorId = response.data.profesor;
          let profesoresStore = useProfesoresStore();
          let profesor = profesoresStore.getProfesorPorId(this.profesorId);
          profesoresStore.seleccionarProfesor(profesor);
          console.log(profesoresStore.profesorSeleccionado);
        })
        .catch((error) => console.log(error));
    },
  },
});
