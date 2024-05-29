import { defineStore } from "pinia"
import LugaresService from '../services/lugaresService'
import lugares from "../assets/lugares.json"
import { useAsignaturasStore } from "./asignaturasStore";
import { useReservasStore } from "./reservasStore";

export const useLugaresStore = defineStore('lugares', {
  state: () => ({
    lugaresColegio: lugares.lugares,
    lugaresService: null,
    lugarSeleccionado: {
      nombre: '',
      capacidad: '',
      tipo: 'AULA',
      proyector: false,
      deportes: []
    }
  }),
  actions: {
    seleccionarLugar(lugar) {
      this.lugarSeleccionado = lugar;
    },

    arrancarServicio(token){
      this.lugaresService = new LugaresService(token)
      console.log(this.lugaresService);
    },

    getLugarPorId(id) {
      return this.lugaresColegio.find(lugar => lugar.id == id);
    },

    crearNuevoLugar() {
      this.lugaresService.create(this.lugarSeleccionado);
    },
    
    /**
    * Función que devuelve un lugar seleccionado automáticamente entre los lugares disponibles 
    * para una asignatura, en funcion de su capacidad.
    * @param asignaturaId parametro de id de la asignatura sobre la que seleccionar el lugar.
    * @returns el lugar seleccionado para la clase
    */
    async escogerLugarDisponible(asignaturaId, periodo) {
      let asignatura = useAsignaturasStore().getAsignaturaPorId(asignaturaId);
      let lugaresId = asignatura.lugares;
      let lugares = [];
      let lugarDisponible = null;
      let disponible = false;
      lugaresId.forEach(id => {
        lugares.push(useLugaresStore().getLugarPorId(id))
      });
      lugares.sort((a, b) => b.capacidad - a.capacidad);
      for (let lugar of lugares) {
        await useReservasStore().reservasService.isLugarDisponible(lugar.id, 
          periodo)
          .then(response => {
            if (response.data == true) {
              lugarDisponible = lugar.id
              disponible = true;
            }
          }).catch(error => console.log(error.code));
        if (disponible) {
          break;
        }
      }
      return lugarDisponible;
    }
  }
})