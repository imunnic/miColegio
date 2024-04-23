import { defineStore } from "pinia"
import lugares from "../assets/lugares.json"
import { useAsignaturasStore } from "./asignaturasStore";
import { useReservasStore } from "./reservasStore";

export const useLugaresStore = defineStore('lugares', {
  state: () => ({
    lugaresColegio: lugares.lugares,
    lugarSeleccionado: null
  }),
  actions: {
    seleccionarLugar(lugar) {
      this.lugarSeleccionado = lugar;
    },

    getLugarPorId(id) {
      return this.lugaresColegio.find(lugar => lugar.id == id);
    },
    
    /**
    * Función que devuelve un lugar seleccionado automáticamente entre los lugares disponibles 
    * para una asignatura, en funcion de su capacidad.
    * @param asignaturaId parametro de id de la asignatura sobre la que seleccionar el lugar.
    * @returns el lugar seleccionado para la clase
    */
    async escogerLugarDisponible(asignaturaId) {
      let asignatura = useAsignaturasStore().getAsignaturaPorId(asignaturaId);
      let lugaresId = asignatura.lugares;
      let lugares = [];
      let lugarSeleccionado = null;
      let disponible = false;
      lugaresId.forEach(id => {
        lugares.push(useLugaresStore().getLugarPorId(id))
      });
      lugares.sort((a, b) => b.capacidad - a.capacidad);
      for (let lugar of lugares) {
        await useReservasStore().reservasService.isLugarDisponible(lugar.id, 
          useReservasStore().reserva.fecha, useReservasStore().reserva.hora)
          .then(response => {
            if (response.data == true) {
              lugarSeleccionado = lugar.id
              disponible = true;
            }
          }).catch(error => console.log(error.code));
        if (disponible) {
          break;
        }
      }
      return lugarSeleccionado;
    }
  }
})