<!-- 
  Vista que permite la visualización de las reservas de un profesor y permite reservar
  franjas horarias para una asignatura, grupo y lugar. 
-->
<template>
  <v-menu v-model="menu" :close-on-content-click="false" :nudge-x="activacion.x" :nudge-y="activacion.y" absolute
    offset-y>
    <v-card class="menu" :style="{ top: activacion.y + 'px', left: activacion.x + 'px' }">
      <v-card-actions class="acciones">
        <h6>Opciones de reserva</h6>
        <v-btn color="primary" icon="mdi-close" @click="menu = false"></v-btn>
      </v-card-actions>
      <ComponenteReservasPosibles class="opcionesReserva" :items="posiblesReservas" @opcion-seleccionada="reservarOpcion"></ComponenteReservasPosibles>
    </v-card>
  </v-menu>
  <ComponentEdicionEvento v-model="editando" @finEdicion="terminarEdicion" @cancelar="cancelarEdicion"
    :asignaturas="profesorSeleccionado.asignaturas">
  </ComponentEdicionEvento>
  <v-snackbar v-model="snack1" :timeout="-1">
    Seleccione la asignatura sobre la que quiere reservar o seleccione una franja horaria
  </v-snackbar>
  <v-snackbar v-model="snack2" :timeout="-1">
    Seleccione el grupo sobre el que quiere reservar
  </v-snackbar>
  <v-snackbar v-model="snack3" :timeout="-1">
    Haga click sobre una franja horaria para reservar
  </v-snackbar>
  <v-snackbar v-model="snack4" :timeout="-1">
    Seleccione una de las opciones para reservar
  </v-snackbar>
  <div class="contenedorColumnas">
    <div class="columnaIzquierda">
      <div class="formularioReserva">
        <v-form ref="form" v-if="profesorSeleccionado != null">
          <!-- si se quisiera mostrar el nombre del profesor -->
          <!-- {{ profesorSeleccionado.nombre }} {{ profesorSeleccionado.apellido }} -->
          <v-spacer></v-spacer>
          <v-select class="select" v-model="asignaturaSeleccionada" label="Asignaturas"
            :items="profesorSeleccionado.asignaturas" @click="if (snack1 == true){snack1 = false; snack2=true}">
            <template v-slot:selection="{ item, index }" required>
              {{ getAsignaturaPorId(item.props.value).nombre }}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" :title="getAsignaturaPorId(item.props.value).nombre">
              </v-list-item>
            </template>
          </v-select>

          <v-select class="select" v-model="grupoSeleccionado" label="Grupo" :items="gruposConNinguno"
            @update:modelValue="cargarGrupo()" @click="if (snack2 == true){snack2 = false; snack3=true}"
            :rules="[v => !!v || 'Seleccione un grupo', v => v !== -1 || 'La opción Ninguno no es válida para reservar']"
            required>
            <template v-slot:selection="{ item, index }">
              {{ getGrupoPorId(item.props.value).nombre }}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" :title="getGrupoPorId(item.props.value).nombre">
              </v-list-item>
            </template>
          </v-select>

        </v-form>
        <div v-else>
          No hay profesor seleccionado
        </div>
      </div>
    </div>
    <div class="columnaDerecha">
      <Qalendar class="calendario" ref="calendarRef" :config="configuracion" :events="eventos"
        @interval-was-clicked="clickEnIntervalo" @updated-period="actualizarCalendarioPorPeriodo"
        @updated-mode="actualizarCalendarioPorModo" @delete-event="borrarEvento" @edit-event="editarEvento">
        <template #weekDayEvent="eventProps">
          <div v-if="eventProps.eventData.title == 'No disponible'"
            :style="{ backgroundColor: '#C5B6F6', color: '#fff', width: '100%', height: '100%', overflow: 'hidden', border: '1px solid gray' }">
            <p class="itemEvento">
              {{ eventProps.eventData.title }}
            </p>

          </div>

          <div v-else
            :style="{ backgroundColor: eventProps.eventData.color || 'cornflowerblue', color: '#fff', width: '200%', height: '100%', overflow: 'hidden', border: '1px solid gray' }">
            <p class="itemEvento">
              <v-icon class="itemEvento" icon="mdi-book-open-variant-outline"></v-icon>{{ eventProps.eventData.topic }}
            </p>
            <p class="itemEvento">
              <v-icon class="itemEvento" icon="mdi-map-marker-outline"></v-icon>{{ eventProps.eventData.location }}
            </p>
            <p class="itemEvento">
              <v-icon class="itemEvento" icon="mdi-account-group"></v-icon>{{ eventProps.eventData.with }}
            </p>
          </div>
        </template>
        <template #dayCell="{ dayData }">
          <div class="celdaDia">
            <div> {{ dayData.dateTimeString.substring(8, 10) }}</div>
            <div> {{ dayData.events.length }} clases</div>
          </div>
        </template>
      </Qalendar>
    </div>
  </div>
</template>

<script>
import { Qalendar } from '../../node_modules/qalendar';
import { mapState, mapActions } from 'pinia';
import { useProfesoresStore } from '../store/profesoresStore';
import { useReservasStore } from '../store/reservasStore';
import { useAsignaturasStore } from '../store/asignaturasStore';
import { useGruposStore } from '../store/gruposStore';
import { useLugaresStore } from '../store/lugaresStores';
import { useUsuariosStore } from '../store/usuarioStore';
import ComponentEdicionEvento from '../componentes/ComponentEdicionEvento.vue';
import ComponenteReservasPosibles from '../componentes/ComponenteReservasPosibles.vue';

export default {
  components: { Qalendar, ComponentEdicionEvento, ComponenteReservasPosibles },

  data() {
    return {
      configuracion: {
        week: {
          startsOn: 'monday',
          nDays: 5
        },
        dayBoundaries: {
          start: 9,
          end: 15
        },
        dayIntervals: {
          displayClickableInterval: true,
          height: 90
        },
        style: {
          fontFamily: 'arial'
        }
      },
      asignaturaSeleccionada: null,
      grupoSeleccionado: null,
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000))
        .toISOString()
        .substr(0, 10),
      menu: false,
      periodoSeleccionado: null,
      fechaSeleccionada: "",
      idEventoEdicion: 0,
      editando: false,
      menu: false,
      posiblesReservas: [[1,1,1]],
      activacion: { x: 0, y: 0 },
      snack1:false,
      snack2:false,
      snack3:false,
      snack4:false,
    }
  },

  computed: {
    ...mapState(useProfesoresStore, ['profesorSeleccionado']),
    ...mapState(useReservasStore, ['reservas', 'eventos', 'reserva']),
    gruposConNinguno() {
      let grupos = [-1, ...this.getAsignaturaPorId(this.asignaturaSeleccionada).grupos];

      return grupos;
    }
  },

  methods: {
    ...mapActions(useReservasStore, ['cargarReservas', 'guardarReserva', 'resetReserva',
      'formatearFechaParaAPI', 'cargarReservasGrupo', 'mapReservaToEventoAjeno',
      'mapReservaToEvento', 'agregarEventos', 'quitarEventosGrupo', 'agregarFranjasImposibles',
      'convertirPeriodToPeriodo', 'arrancarServicio', 'eliminarReserva', 'modificarReserva',
      'quitarReservasImposibles', 'quitarEventosPorId', 'getGruposReservados', 'getLugaresReservados']),
    ...mapActions(useAsignaturasStore, ['getAsignaturaPorId']),
    ...mapActions(useGruposStore, ['getGrupoPorId']),
    ...mapActions(useLugaresStore, ['escogerLugarDisponible', 'cargarLugares', 'arrancarServicioLugares', 'getLugarPorId']),
    ...mapActions(useProfesoresStore,['getReservasPosibles']),

    /**
     * Función para controlar los click en los intervalos del calendario. Coge la franja 
     * horaria y la fecha sobre la que se ha realizado click y la guarda en 
     * this.fechaSeleccionada 
     * @param evento es el evento elevado desde calendar al hacer click. El contenido se 
     * encuentra en la documentación oficial de Qalendar
     */
    async clickEnIntervalo(evento) {
      if(this.snack1 == true || this.snack2 == true){
        this.snack1 = false;
        this.snack2 = false;
        this.snack4 = true;
      }
      document.addEventListener('click', this.handleDocumentClick);
      let fecha = evento.intervalStart.substr(0, 10);
      let partes = fecha.split("-");
      fecha = partes[2] + "-" + partes[1] + "-" + partes[0];
      this.fechaSeleccionada = fecha + " "
        + evento.intervalStart.substr(11, 2) + "-" + evento.intervalEnd.substr(11, 2);
      if (this.grupoSeleccionado != null && this.grupoSeleccionado != -1) {
        await this.reservar();
        document.addEventListener('click', this.handleDocumentClick);

      } else {
        this.menu = true;
        let horaAux = parseInt(this.fechaSeleccionada.split(" ")[1].split("-")[0],10);
        let fechaAux = partes[0] + "-" + partes[1] + "-" + partes[2];
        let periodo = {
          fecha: fechaAux,
          hora: horaAux
        }
        this.posiblesReservas = await this.getReservasPosibles(periodo)
      }
    },

    handleDocumentClick(e) {
      this.activacion = { x: e.clientX, y: e.clientY }; // Obtén las coordenadas del clic
      document.removeEventListener('click', this.handleDocumentClick);
    },
    /**
     * Función que dada una opción de respuesta seleccionada por el usuario realiza la reserva
     * @param item representa la opción de reserva del usuario que es un item con la forma [
     * asignaturaId, grupoId, lugarId] 
     */
    async reservarOpcion(item){
      this.snack4 = false;
      this.reserva.fecha = this.formatearFechaParaAPI(this.fechaSeleccionada.split(" ")[0]);
      this.reserva.hora = parseInt(this.fechaSeleccionada.split(" ")[1].split("-")[0]);
      this.reserva.profesor = this.profesorSeleccionado.id;
      this.reserva.asignatura = item[0];
      this.reserva.grupo = item[1];
      this.reserva.lugar = this.getLugarPorId(item[2]);
      await this.guardarReserva();
      this.fechaSeleccionada = null;
      await this.refrescarCalendario();
      this.menu = false;
    },

    /**
     * Función que realiza comprueba si hay lugares disponibles, si lo hay asigna uno y 
     * después realiza la reserva. Si no lo hay informa al usuario.
     */
    async reservar() {
      this.snack3 = false;
      const { valid } = await this.$refs.form.validate();
      if (valid) {
        this.reserva.fecha = this.formatearFechaParaAPI(this.fechaSeleccionada.split(" ")[0]);
        this.reserva.hora = parseInt(this.fechaSeleccionada.split(" ")[1].split("-")[0]);
        let periodo = {
          "fecha": this.reserva.fecha,
          "hora": this.reserva.hora
        };
        let lugar = await this.escogerLugarDisponible(this.asignaturaSeleccionada, periodo);
        if (lugar != null) {
          this.reserva.profesor = this.profesorSeleccionado.id;
          this.reserva.asignatura = this.asignaturaSeleccionada;
          this.reserva.grupo = this.grupoSeleccionado;
          this.reserva.lugar = lugar;
          await this.guardarReserva();
          this.fechaSeleccionada = null;
          await this.refrescarCalendario();
        } else {
          alert('No hay lugares disponibles para esa franja horaria, elija otra franja');
          this.resetReserva();
        }
      }
    },

    /**
     * Función que añade a los eventos los del grupo seleccionado para que los profesores puedan ver
     * cuando el grupo no está disponible.
     * TODO cambiar el color de los eventos del grupo para hacerlo un poco más amigable
     */
    async cargarGrupo() {
      this.quitarEventosGrupo();
      if (this.grupoSeleccionado == -1) {
        this.quitarEventosGrupo();
        this.agregarFranjasImposibles(this.convertirPeriodToPeriodo(this.periodoSeleccionado));
      } else {
        let eventosGrupo = [];
        try {
          let reservasGrupo = await this.cargarReservasGrupo(this.grupoSeleccionado,
            this.periodoSeleccionado);
          if (reservasGrupo.length == 0) {
            this.refrescarCalendario();
          } else {
            eventosGrupo = reservasGrupo.map((reserva) => { return this.mapReservaToEventoAjeno(reserva) })
            eventosGrupo = eventosGrupo.filter(eventoGrupo => !this.eventos.some(evento => evento.id == eventoGrupo.id));
            this.agregarEventos(eventosGrupo);
          }
        } catch (error) {
          console.log(error);
        }
      }
    },

    /**
     * Funcion que sirve para refrescar la vista del calendario y actualizar sus props.
     * Si se quiere volver al día actual se debe añadir la prop :key y utilizar una variable
     * dummy para que al cambiarla se refresque la vista y devuelva a la fecha actual.
     * Hay que tener en cuenta que la carga de reservas sería la del periodo dado por lo que
     * habría que cargar de nuevo las reservas para el periodo de la fecha actual antes de
     * mostrarlo.
     */
    async refrescarCalendario() {
      let aux = this.convertirPeriodToPeriodo(this.periodoSeleccionado);
      await this.cargarReservas(aux);
      await this.agregarFranjasImposibles(aux);
      if (this.grupoSeleccionado == null || this.grupoSeleccionado == -1){

      } else {
        await this.cargarGrupo();
      }
    },

    async actualizarCalendarioPorModo(modo) {
      await this.actualizarCalendarioPorPeriodo(modo.period)
    },

    async actualizarCalendarioPorPeriodo(periodo) {
      this.periodoSeleccionado = periodo;
      let aux = this.convertirPeriodToPeriodo(this.periodoSeleccionado);
      await this.cargarReservas(aux);
      await this.agregarFranjasImposibles(aux);
      if (this.grupoSeleccionado != null) {
        await this.cargarGrupo();
      }
    },

    editarEvento(evento) {
      this.editando = true;
      this.idEventoEdicion = evento
    },

    cancelarEdicion() {
      this.idEventoEdicion = 0;
      console.log(this.idEventoEdicion)
      this.editando = false;
    },

    async terminarEdicion(idAsignatura) {
      this.editando = false;
      await this.modificarReserva(idAsignatura, this.idEventoEdicion);
      this.refrescarCalendario()
      this.idEventoEdicion = 0;
    },

    async borrarEvento(evento) {
      let idEliminado = await this.eliminarReserva(evento);
      console.log(idEliminado.data.identificacion);
      this.quitarEventosPorId(idEliminado.data.identificacion);
      // await this.refrescarCalendario();//devuelve el id, hay que buscar el evento por id
    }
  },

  async created() {
    //Si no hay asignatura seleccionada dará error, por eso debe ser al crear
    this.asignaturaSeleccionada = this.profesorSeleccionado.asignaturas[0];
    //Si se cargan los lugares en mounted 1 de cada 3 veces da error
    this.arrancarServicioLugares(useUsuariosStore().token);
    await this.cargarLugares();

  },
  /**
   * Al montar a la fecha de fin del periodo seleccionado del calendario hay que sumar 1 porque 
   * si no da un día por detrás. Después de eso hay que almacenar el periodo seleccionado de forma
   * correcta. El problema viene de la librería.
   */
  async mounted() {
    if(useUsuariosStore().primerInicio == true){
      this.snack1 = true;
    }
    let fechaFin = new Date(this.$refs.calendarRef.period.end);
    fechaFin.setDate(fechaFin.getDate() + 1);
    this.periodoSeleccionado = {
      start: this.$refs.calendarRef.period.start,
      end: fechaFin
    }
    if (useReservasStore().reservasService == null) {
      this.arrancarServicio(useUsuariosStore().token);
    }
    await this.cargarReservas(this.convertirPeriodToPeriodo(this.periodoSeleccionado));
    await this.agregarFranjasImposibles(this.convertirPeriodToPeriodo(this.periodoSeleccionado));
  }
}
</script>

<style scoped>
form {
  display: flex;
  justify-content: center;
  align-items: center;
}

form .select {
  padding: 0px 5px;
  min-width: 150px;
}

.formularioReserva {
  display: flex;
  flex-flow: row;
  justify-content: space-around;
}

.contenedorColumnas {
  padding: 16px;
  width: 100%;
  display: flex;
  flex-flow: column;
  justify-content: center;
}

.columnaIzquierda {
  padding: 10px;
  width: 100%;
}

.columnaDerecha {

  padding: 10px;
  width: 100%;
}

.calendario {
  width: 100%;
  max-height: 700px;
  text-align: start;
  overflow-y: scroll;
}

.celdaDia {
  min-height: 60px;
}

.fecha {
  min-width: 200px;
}

.itemEvento {
  margin-bottom: 0px;
  padding: 2px;
}

.opcionesReserva{
  overflow-y: scroll;
}

.menu{
  max-height: 200px;
}
.acciones{
  display: flex;
  justify-content: space-between;
}

/*Vista para dispositivos de menos de 575px*/
@media (max-width: 575px) {
  .contenedorColumnas {
    flex-flow: column;
  }

  .columnaIzquierda {

    width: 100%;
  }

  .columnaDerecha {
    width: 100%;
  }
}
</style>