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
      try {
        let response = await this.usuarioService.login(login);
        if (response.status == 200) {
          this.token = response.data.token;
          this.username = response.data.username;
          this.isLogged = true;
        }
        this.obtenerIdProfesor()
      } catch(error){
        console.log(error);
      }
    },

    async obtenerIdProfesor() {
      try {
        let response = await this.usuarioService.getIdUsuario(this.username, this.token);
        this.profesorId = response.data.profesor;
          let profesoresStore = useProfesoresStore();
          let profesor = profesoresStore.getProfesorPorId(this.profesorId);
          profesoresStore.seleccionarProfesor(profesor);
      } catch(error){
        console.log(error);
      }
    },
  },
});
