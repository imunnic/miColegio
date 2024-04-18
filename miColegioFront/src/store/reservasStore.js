import { defineStore } from "pinia";
import ReservasService from "../services/reservasService";
import { useAsignaturasStore } from "./asignaturasStore";
import { useGruposStore } from "./gruposStore";
import { useLugaresStore } from "./lugaresStores";
import { useProfesoresStore } from "./profesoresStore";

export const useReservasStore = defineStore("reservas", {
  state: () => ({
    reservas: [],
    reservasService: new ReservasService(),
    eventos: [],
    reserva: {
      profesor: null,
      lugar: null,
      asignatura: null,
      grupo: null,
      fecha: null,
      hora: null,
    },
  }),
  actions: {
    /**
     * Función que carga las reservas desde la api y los eventos en el formato
     * de la librería "Qalendar" siempre que se seleccione un profesor. Modifica
     * this.reservas y this.eventos.
     */
    cargarReservas() {
      let profesores = useProfesoresStore();
      if (profesores.profesorSeleccionado != null) {
        this.reservasService
          .getReservasProfesor(profesores.profesorSeleccionado.id)
          .then((response) => {
            this.reservas = response.data._embedded.reservas;
            this.eventos = this.reservas.map((reserva) => {
              return this.mapReservaToEvento(reserva);
            });
          })
          .catch((error) => {
            console.log(error.code);
          });
      } else {
        this.reservas = [];
        this.eventos = [];
      }
    },
    agregarEventos(eventos){
      eventos.forEach(evento => {
        this.eventos.push(evento);
      });
    },
    quitarUltimosEventosAdded(numEventos){
      for (let index = 0; index < numEventos; index++) {
        this.eventos.pop();
      }
    },
    /**
     * 
     * @param grupoId id del grupo del que se ven todas las reservas
     */
    async cargarReservasGrupo(grupoId){
      let reservasGrupo = [];
      await this.reservasService.getReservasGrupo(grupoId)
      .then(response => reservasGrupo = response.data._embedded.reservas)
      .catch(error => console.log(error));
      return reservasGrupo;
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
        disableResize: ["month", "week", "day"],
      };

      return evento;
    },
    /**
     * Función que permite la selección automática de lugar entre los lugares disponibles para una
     * asignatura y se la asigna a la reserva.
     * @param asignaturaId parametro de id de la asignatura sobre la que seleccionar el lugar.
     */
    async escogerLugarDisponible(asignaturaId) {
      let asignatura = useAsignaturasStore().getAsignaturaPorId(asignaturaId);
      let lugaresId = asignatura.lugares;
      let lugares = [];
      let disponible = false;
      lugaresId.forEach(id => {
        lugares.push(useLugaresStore().getLugarPorId(id))
      });
      lugares.sort((a,b) => b.capacidad - a.capacidad);
      for (let lugar of lugares) {
        await this.reservasService.isLugarDisponible(lugar.id,this.reserva.fecha, this.reserva.hora)
        .then(response => {
          if (response.data == true){
            this.reserva.lugar = lugar.id
            disponible = true;
          }
        }).catch(error => console.log(error.code));
        if (disponible){
          break;
        }
      }
      return disponible;
    },

    guardarReserva() {
      this.reservasService
        .create(this.reserva)
        .then(() => {
          this.cargarReservas();
        })
        .catch((error) => {
          if (error.response.status == 409){
            alert('El grupo ya tiene asignada esa franja horaria. Puede elejir otra.')
          }
        });
    },

    resetReserva() {
      for (let propiedad in this.reserva) {
        this.reserva[propiedad] = null;
      }
    },

    /**
     * Función que permite dar la vuelta a una fecha para guardarla en la API
     * @param fecha la fecha en formato dd-MM-yyyy
     * @returns la fecha en formato yyyy-MM-dd
     */
    formatarFechaParaAPI(fecha) {
      let fechaPartida = fecha.split("-");
      let fechaNueva =
        fechaPartida[2] + "-" + fechaPartida[1] + "-" + fechaPartida[0];
      return fechaNueva;
    },
  },
});
