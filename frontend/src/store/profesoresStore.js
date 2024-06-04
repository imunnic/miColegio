import { defineStore } from "pinia"
import { useAsignaturasStore } from "./asignaturasStore";
import profesores from "../assets/profesores.json"

export const useProfesoresStore = defineStore('profesorado', {
  state: () => ({
    profesorado: profesores.profesores,
    profesorSeleccionado: {
      nombre:"",
      apellidos:""
    }
  }),
  actions: {
    seleccionarProfesor(profesor) {
      this.profesorSeleccionado = profesor;
    },
    getProfesorPorId(id) {
      return this.profesorado.find(profesor => profesor.id == id);
    },
    getGruposDeProfesor() {
      const asignaturas = useAsignaturasStore().asignaturasColegio;
      let grupos = new Set();
      this.profesorSeleccionado.asignaturas.forEach(asignaturaId => {
          let asignatura = asignaturas.find(asig => asig.id == asignaturaId);
          if (asignatura) {
              asignatura.grupos.forEach(grupo => grupos.add(grupo));
          }
      });
      return Array.from(grupos);
  },
  getLugaresDeProfesor() {
    const asignaturas = useAsignaturasStore().asignaturasColegio;
    let lugares = new Set();
    this.profesorSeleccionado.asignaturas.forEach(asignaturaId => {
        let asignatura = asignaturas.find(asig => asig.id == asignaturaId);
        if (asignatura) {
            asignatura.lugares.forEach(lugar => lugares.add(lugar));
        }
    });
    return Array.from(lugares);
}
  }
})