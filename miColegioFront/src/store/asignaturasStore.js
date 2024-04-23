import { defineStore } from "pinia"
import asignaturas from "../assets/asignaturas.json"

export const useAsignaturasStore = defineStore('asignaturas', {
  state: () => ({
    asignaturasColegio: asignaturas.asignaturas,
    asignaturaSeleccionada: null
  }),
  actions: {
    seleccionarAsignatura(asignatura) {
      this.asignaturaSeleccionada = asignatura;
    },
    getAsignaturaPorId(id) {
      return this.asignaturasColegio.find(asignatura => asignatura.id == id);
    }
  }
})