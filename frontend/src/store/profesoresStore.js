import { defineStore } from "pinia";
import { useAsignaturasStore } from "./asignaturasStore";
import profesores from "../assets/profesores.json";
import { useLugaresStore } from "./lugaresStores";
import { useReservasStore } from "./reservasStore";

export const useProfesoresStore = defineStore("profesorado", {
  state: () => ({
    profesorado: profesores.profesores,
    profesorSeleccionado: {
      nombre: "",
      apellidos: "",
    },
  }),
  actions: {
    seleccionarProfesor(profesor) {
      this.profesorSeleccionado = profesor;
    },
    getProfesorPorId(id) {
      return this.profesorado.find((profesor) => profesor.id == id);
    },
    getGruposDeProfesor() {
      const asignaturas = useAsignaturasStore().asignaturasColegio;
      let grupos = new Set();
      this.profesorSeleccionado.asignaturas.forEach((asignaturaId) => {
        let asignatura = asignaturas.find((asig) => asig.id == asignaturaId);
        if (asignatura) {
          asignatura.grupos.forEach((grupo) => grupos.add(grupo));
        }
      });
      return Array.from(grupos);
    },

    getLugaresDeProfesor() {
      const asignaturas = useAsignaturasStore().asignaturasColegio;
      let lugares = new Set();
      this.profesorSeleccionado.asignaturas.forEach((asignaturaId) => {
        let asignatura = asignaturas.find((asig) => asig.id == asignaturaId);
        if (asignatura) {
          asignatura.lugares.forEach((lugar) => lugares.add(lugar));
        }
      });
      return Array.from(lugares);
    },
    /**
     * Funcion que permite obtener todas las reservas posibles de un profesor en formato
     * de array [asignaturaId, grupoId, lugarId]
     * @returns devuelve todas las combinaciones posibles de reserva de un profesor
     */
    getCombinacionesReserva() {
      let combinaciones = [];
      this.profesorSeleccionado.asignaturas.forEach((asignaturaId) => {
        let asignatura = useAsignaturasStore().asignaturasColegio.find(
          (a) => a.id == asignaturaId
        );
        if (asignatura) {
          asignatura.grupos.forEach((grupoId) => {
            asignatura.lugares.forEach((lugarId) => {
              combinaciones.push([asignaturaId, grupoId, lugarId]);
            });
          });
        }
      });
      return combinaciones;
    },

    async getReservasPosibles(combinaciones, periodo){
      let gruposReservados = await useReservasStore().getGruposReservados();
      let lugaresReservados = await useReservasStore().getLugaresReservados();
      let combinacionesFiltradas = combinaciones.filter(([asignaturaId, grupoId, lugarId]) => {
        let lugarEstaDisponible = !lugaresReservados.includes(lugarId);
        let grupoEstaDisponible = !gruposReservados.includes(grupoId);
        return lugarEstaDisponible && grupoEstaDisponible;
    });

    return combinacionesFiltradas;
    }
  },
});
