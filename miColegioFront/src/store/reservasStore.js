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
  }),
  actions: {
    //funcion que carga las reservas siempre que haya un profesor seleccionado
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
    
    //función que al coger una reserva la mapea en un evento de qalendar para que se pueda mostrar
    //dando también el formato a la fecha adecuado
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
  },
});
