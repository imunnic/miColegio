import { defineStore } from "pinia";
import ReservasService from "../services/reservasService";
import { useAsignaturasStore } from "./asignaturasStore";
import {useGruposStore} from "./gruposStore"
import { useLugaresStore } from "./lugaresStores";
import {useProfesoresStore} from "./profesoresStore";

export const useReservasStore = defineStore("reservas", {
  state: () => ({
    reservas: [],
    reservasService: new ReservasService(),
    eventos: [],
    reserva:{
      profesor:null,
      lugar:null,
      asignatura:null,
      grupo:null,
      fecha:null,
      hora:null
    }
  }),
  actions: {
    /**
    * Función que carga las reservas desde la api y los eventos en el formato
    * de la librería "Qalendar" siempre que se seleccione un profesor. Modifica 
    * this.reservas y this.eventos.
    */
    cargarReservas() {
      let profesores = useProfesoresStore();
      if(profesores.profesorSeleccionado != null){
        this.reservasService
          .getAll()
          .then((response) => {
            this.reservas = response.data._embedded.reservas.filter(reserva => {
              return reserva.profesor == profesores.profesorSeleccionado.id;
            });
            this.eventos = this.reservas.map((reserva) =>{
              return this.mapReservaToEvento(reserva);
            });
          })
          .catch((error) => {
            console.log(error);
          });
      } else{
        this.reservas = [];
        this.eventos = [];
      }
    },
    /**
     * Función que mapea una reserva en un evento de la librería "Qalendar"
     * @param reserva la reserva como se recibe de la API
     * @returns el evento en el formato de la librería "Qalendar"
     */
    mapReservaToEvento(reserva) {
      let asignatura = useAsignaturasStore();
      let grupos = useGruposStore();
      let lugares = useLugaresStore();
      let duracion = 1;
      let fechaCompleta = new Date(`${reserva.fecha} ${reserva.hora}:00`);
      let horaInicio = fechaCompleta.toLocaleString("es-ES", {
        hour: "2-digit",
        minute: "2-digit",
      });
      fechaCompleta.setHours(fechaCompleta.getHours() + duracion);
      let horaFin = fechaCompleta.toLocaleString("es-ES", {
        hour: "2-digit",
        minute: "2-digit",
      });
      let evento = {
        id: reserva.identificacion,
        title: "Clase",
        time: {
          start: `${reserva.fecha} ${horaInicio}`,
          end: `${reserva.fecha} ${horaFin}`,
        },
        topic: asignatura.getAsignaturaPorId(reserva.asignatura).nombre,
        description: null,
        location: lugares.getLugarPorId(reserva.lugar).nombre,
        with: grupos.getGrupoPorId(reserva.grupo).nombre,
        isEditable: true,
        disableDnD: ["month", "week", "day"],
        disableResize: ["month", "week", "day"]
      };

      return evento;
    },

    guardarReserva(profesor, asignatura, grupo, grupoFechaHora){
      this.reserva.profesor = profesor.id;
      this.reserva.asignatura = asignatura;
      this.reserva.grupo = grupo;
      this.reserva.lugar = 3;
      this.reserva.fecha = grupoFechaHora.split(" ")[0];
      this.reserva.hora = parseInt(grupoFechaHora.split(" ")[1].split("-")[0]);
      this.reservasService.create(this.reserva);
      this.resetReserva();
    },
    
    resetReserva(){
      this.reserva.profesor = null;
      this.reserva.asignatura = null;
      this.reserva.grupo = null;
      this.reserva.lugar = null;
      this.reserva.fecha = null;
      this.reserva.hora = null;
      // for (propiedad in this.reserva) {
      //   this.reserva[propiedad] = null;
      // }
    }
  },
});
