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
    /**
     * Función que permite cargar los lugares en la variable lugaresColegio.
     * Carga todos los lugares en la BBDD
     */
    async cargarLugares() {
      let response = await this.lugaresService.getAll();
      let aulasYPatios = response.data._embedded || {};
      let aulas = [];
      let patios = [];

      if (aulasYPatios.aulas) {
        aulas = aulasYPatios.aulas.map(aula => ({
          ...aula,
          tipo: 'Aula'
        }));
      }

      if (aulasYPatios.patios) {
        patios = aulasYPatios.patios.map(patio => ({
          ...patio,
          tipo: 'Patio'
        }));
      }

      this.lugaresColegio = [...aulas, ...patios];
    },

    /**
     * Función que borra un lugar de la BBDD
     * @param lugar objeto lugar
     */
    async borrarLugar(lugar) {
      await this.lugaresService.delete(lugar._links.self.href);
      await this.cargarLugares();
    },

    /**
     * Función que marca un lugar como seleccionado
     * @param lugar objeto lugar
     */
    seleccionarLugar(lugar) {
      this.lugarSeleccionado = lugar;
    },

    arrancarServicioLugares(token) {
      this.lugaresService = new LugaresService(token)
    },

    /**
     * Función que devuelve un lugar por su id
     * @param lugar id del lugar
     */
    accessLugarPorId(id) {
      let lugar = this.lugaresColegio.find(l => l.identificacion == id);
      return lugar;
    },

    /**
     * Función que devuelve un lugar por su nombre
     * @param lugar nombre del lugar
     */
    getLugarPorNombre(nombre) {
      let lugar = this.lugaresColegio.find(lugar => lugar.name === nombre);
      return lugar._links.self.href;
    },

    /**
     * Función que devuelve el href de un lugar por su id
     * @param lugar id del lugar
     */
    getLugarPorId(id) {
      return this.lugaresService.getHrefById(id);
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
        lugares.push(this.getLugarPorId(id))
      });
      lugares.sort((a, b) => b.capacidad - a.capacidad);
      for (let lugar of lugares) {
        let id = lugar.split('/')[lugar.split('/').length - 1];
        await useReservasStore().reservasService.isLugarDisponible(id,
          periodo)
          .then(response => {
            if (response.data == true) {
              lugarDisponible = lugar
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