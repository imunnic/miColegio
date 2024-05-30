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
      deportes: [],
      href: null
    }
  }),
  actions: {
    async cargarLugares() {
      let response = await this.lugaresService.getAll();
      let aulasYPatios = response.data._embedded;
      let aulas = aulasYPatios.aulas.map(aula => ({
        ...aula,
        tipo:'Aula'
      }));
      let patios = aulasYPatios.patios.map(patio => ({
        ...patio,
        tipo:'Patio'
      }));
      this.lugaresColegio = [...aulas, ...patios];
    },
    async borrarLugar(lugar) {
      await this.lugaresService.delete(lugar._links.self.href);
      await this.cargarLugares();
    },
    seleccionarLugar(lugar) {
      this.lugarSeleccionado = lugar;
    },

    arrancarServicio(token){
      this.lugaresService = new LugaresService(token)
    },

    getLugarPorId(id) {
      return this.lugaresColegio.find(lugar => lugar.id == id);
    },

    async crearNuevoLugar() {
      await this.lugaresService.create(this.lugarSeleccionado);
    },

    async modificarLugar() {
      await this.lugaresService.update(this.lugarSeleccionado.href, 
        this.lugarSeleccionado);
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