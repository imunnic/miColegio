import { defineStore } from "pinia";
import ReservasService from "../services/reservasService";
import { useAsignaturasStore } from "./asignaturasStore";
import { useGruposStore } from "./gruposStore";
import { useLugaresStore } from "./lugaresStores";
import { useProfesoresStore } from "./profesoresStore";

export const useReservasStore = defineStore("reservas", {
  state: () => ({
    reservas: [],
    reservasService: null,
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
    async cargarReservas(periodo) {
      let profesores = useProfesoresStore();
      if (profesores.profesorSeleccionado != null) {
        try {
          let response = await this.reservasService.getReservasProfesorEntre(
            profesores.profesorSeleccionado.id,
            periodo
          );
          // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/keys
          if (Object.keys(response.data).length == 0) {
            this.eventos = [];
            let evento = {};
            return evento;
          } else {
            this.reservas = response.data._embedded.reservas;
            this.eventos = this.reservas.map((reserva) => {
              return this.mapReservaToEvento(reserva);
            });
          }
        } catch (error) {
          console.log(error);
        }
      } else {
        this.reservas = [];
        this.eventos = [];
      }
    },

    /**
     * Función que permite modificar la asignatura de una reserva y además el lugar automáticamente
     * @param idAsignaturaNueva es el id de la asignatura que se va a poner
     * @param idEvento es el id del evento de qalendar que se corresponde con el id de la reserva
     */
    async modificarReserva(idAsignaturaNueva, idEvento) {
      let eventoEdicion = this.eventos.filter((e) => (e.id = idEvento))[0];
      let periodo = {
        fecha: eventoEdicion.time.start.split(" ")[0],
        hora: eventoEdicion.time.start.split(" ")[1].split(":")[0],
      };
      try {
        let lugar = await useLugaresStore().escogerLugarDisponible(
          idAsignaturaNueva,
          periodo
        );
        if (lugar == null) {
        } else {
          let modificacion = {
            asignatura: idAsignaturaNueva,
            lugar: lugar,
          };
          await this.reservasService.update(idEvento, modificacion);
        }
      } catch (error) {
        console.log(error);
      }
    },

    /**
     * Función que devuelve las reservas de un grupo entre las fechas dadas
     * @param grupoId id del grupo del que se ven todas las reservas
     * @returns todas las reservas de un grupo concreto
     */
    async cargarReservasGrupo(grupoId, period) {
      let periodo = this.convertirPeriodToPeriodo(period);
      let reservasGrupo = [];
      try {
        let response = await this.reservasService.getReservasGrupoEntre(
          grupoId,
          periodo
        );
        if (Object.keys(response.data).length != 0) {
          reservasGrupo = response.data._embedded.reservas;
        }
      } catch (error) {
        console.log(error);
      }
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
        location: lugares.accessLugarPorId(reserva.lugarId).nombre,
        with: grupos.getGrupoPorId(reserva.grupo).nombre,
        isEditable: true,
        disableDnD: ["month", "week", "day"],
        disableResize: ["month", "week", "day"],
        color: 'blue',
        isCustom: true,
      };

      return evento;
    },

    /**
     * Función que mapea una reserva en un evento de la librería "Qalendar" para que no se
     * pueda editar
     * @param reserva la reserva como se recibe de la API
     * @returns el evento en el formato de la librería "Qalendar" no editable
     */
    mapReservaToEventoAjeno(reserva) {
      let evento = this.mapReservaToEvento(reserva);
      evento.isEditable = false;
      evento.color = "green";
      return evento;
    },

    mapReservaImposibleToEvento(mapaFechasHoraImposible) {
      let eventos = [];
      for (let fecha in mapaFechasHoraImposible) {
        if (mapaFechasHoraImposible.hasOwnProperty(fecha)) {
          let horas = mapaFechasHoraImposible[fecha];
          for (let i = 0; i < horas.length; i++) {
            let hora = horas[i];
            let nuevoEvento = {
              id: null,
              title: 'No disponible',
              time: {
                start: null,
                end: null,
              },
              topic: null,
              description: null,
              location: null,
              with: null,
              isEditable: false,
              disableDnD: ["month", "week", "day"],
              disableResize: ["month", "week", "day"],
              isCustom: true,
              color: 'red'
            };

            let horaFin = hora + 1;

            nuevoEvento.id = new Date().getTime() + hora;
            nuevoEvento.time.start = `${fecha} ${hora}:00`;
            nuevoEvento.time.end = `${fecha} ${horaFin}:00`;
            if (this.eventos.filter(e => e.time.start == nuevoEvento.time.start).length > 0){

            } else {
              eventos.push(nuevoEvento);
            }
          }
        }
      }

      return eventos;
    },

    mapLugaresNoPosiblesToEvento(){
      let eventos = [];

    },

    async agregarFranjasImposibles(periodo) {
      let fechasImposiblesGrupos =
        await this.reservasService.getReservasImposibleGrupo(
          this.getFiltroGrupo(periodo)
        );
      let fechasImposiblesLugares =
        await this.reservasService.getReservasImposibleLugar(
          this.getFiltroLugar(periodo)
        )
      // console.log(fechasImposiblesGrupos.data);
      let fechasImposibles = {};
      for (const fecha in fechasImposiblesGrupos.data) {
        fechasImposibles[fecha] = [...new Set([...(fechasImposiblesGrupos.data[fecha] || []), ...(fechasImposiblesLugares.data[fecha] || [])])];
      }
      let reservasImposibles = this.mapReservaImposibleToEvento(fechasImposibles);
      this.agregarEventos(reservasImposibles);
      this.ordenarEventos();
    },

    ordenarEventos() {
      const colorOrder = { 'blue': 1, 'red': 2, 'green': 3 };

      this.eventos.sort((a, b) => colorOrder[a.color] - colorOrder[b.color]);
    },


    async guardarReserva() {
      try {
        await this.reservasService.create(this.reserva);
      } catch (error) {
        if (error.response.status == 409) {
          alert(
            "El grupo ya tiene asignada esa franja horaria. Puede elejir otra."
          );
        }
      }
    },

    resetReserva() {
      for (let propiedad in this.reserva) {
        this.reserva[propiedad] = null;
      }
    },

    async eliminarReserva(href) {
      try {
        return await this.reservasService.delete(href);
      } catch (error) {
        console.log(error);
      }
    },

    agregarEventos(eventos) {
      this.eventos = [...this.eventos, ...eventos];
    },

    /**
     * Función que permite eliminar de la vista los eventos de grupo añadidos al calendario
     * @param numEventos es el número de eventos que se había añadido previamente
     */
    quitarEventosGrupo() {
      this.eventos = this.eventos.filter(e => e.color != 'green');
    },

    quitarReservasImposibles() {
      this.eventos = this.eventos.filter(e => e.title !== 'No disponible');
    },

    quitarEventosPorId(idEvento) {
      this.eventos = this.eventos.filter(e => e.id !== idEvento);
    },

    getFiltroGrupo(periodo) {
      let filtro = {
        fechaInicio: periodo.start,
        fechaFin: periodo.end,
        grupos: useProfesoresStore().getGruposDeProfesor(),
      };
      return filtro;
    },

    getFiltroLugar(periodo) {
      let filtro = {
        fechaInicio: periodo.start,
        fechaFin: periodo.end,
        grupos: useProfesoresStore().getLugaresDeProfesor(),
      };
      return filtro;
    },

    /**
     * Función que permite dar la vuelta a una fecha para guardarla en la API
     * @param fecha la fecha en formato dd-MM-yyyy
     * @returns la fecha en formato yyyy-MM-dd
     */
    formatearFechaParaAPI(fecha) {
      let fechaPartida = fecha.split("-");
      let fechaNueva =
        fechaPartida[2] + "-" + fechaPartida[1] + "-" + fechaPartida[0];
      return fechaNueva;
    },

    /**
     * Función que convierte el periodo que devuelve calendar en un periodo manejable por la API
     * @param {*} period periodo de Qalendar
     * @returns el periodo en el formato que puede manejar la API
     */
    convertirPeriodToPeriodo(period) {
      let periodo = {
        start: period.start.toISOString().split("T")[0],
        end: period.end.toISOString().split("T")[0],
      };
      return periodo;
    },

    arrancarServicio(token) {
      this.reservasService = new ReservasService(token);
    },
  },
});
