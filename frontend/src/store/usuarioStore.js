import { defineStore } from "pinia";
import UsuariosService from "../services/usuariosService";
import { useProfesoresStore } from "./profesoresStore";

export const useUsuariosStore = defineStore("usuarios", {
  state: () => ({
    username: "",
    token: "",
    perfil:"",
    isLogged: false,
    profesorId: null,
    primerInicio:null,
    usuarioService: new UsuariosService(),
  }),

  actions: {
    async peticionLogin(login) {
      try {
        let response = await this.usuarioService.login(login);
        if (response.status == 200) {
          this.token = response.data.token;
          this.username = response.data.username;
          this.perfil = response.data.rol;
          this.primerInicio = response.data.primerInicio;
          this.isLogged = true;
        }
        await this.obtenerIdProfesor()
      } catch(error){
        console.log(error);
      }
    },

    /**
     * Función que obtiene la id del profesor que se ha logeado y guarda el profesor
     * correspondiente en la store de profesor
     */
    async obtenerIdProfesor() {
      try {
        let response = await this.usuarioService.getIdUsuario(this.username, this.token);
        this.profesorId = response.data.profesor;
          let profesoresStore = useProfesoresStore();
          let profesor = profesoresStore.getProfesorPorId(this.profesorId);
          await profesoresStore.seleccionarProfesor(profesor);
      } catch(error){
        console.log(error);
      }
    },
  },
});
