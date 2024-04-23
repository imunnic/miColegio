<!-- 
  Vista que permite la visualización de las reservas de un profesor y permite reservar
  franjas horarias para una asignatura, grupo y lugar. 
-->
<!-- TODO error seleccion grupo y salto de periodo al actual. Se debe la refresh del key
posible solución cambiar el periodo del calendario-->
<!-- TODO error de  actualización de reservas. Al actualizar las reservas no se muestran-->
<!-- TODO error de carga de grupos, al saltar de periodo no se cargan los grupos para el
periodo nuevo. -->
<template>
  <div class="contenedorColumnas">
    <div class="columnaIzquierda">
      <div class="formularioReserva">
        <div v-if="profesorSeleccionado != null">
          {{ profesorSeleccionado.nombre }} {{ profesorSeleccionado.apellido }}

          <v-select v-model="asignaturaSeleccionada" 
            label="Asignaturas" 
            :items="profesorSeleccionado.asignaturas">
            <template v-slot:selection="{ item, index }">
              {{ getAsignaturaPorId(item.props.value).nombre }}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" 
              :title="getAsignaturaPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>

          <v-select v-model="grupoSeleccionado" 
            label="Grupo" 
            :items="getAsignaturaPorId(asignaturaSeleccionada).grupos"
            @update:modelValue="cargarGrupo()">
            <template v-slot:selection="{ item, index }">
              {{ getGrupoPorId(item.props.value).nombre }}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" 
                :title="getGrupoPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>

          <v-text-field class="fecha" 
            prepend-icon="mdi-calendar" 
            v-model="fechaSeleccionada" 
            :disabled="true">
          </v-text-field>

          <v-btn @click="reservar">Reservar</v-btn>

        </div>
        <div v-else>
          No hay profesor seleccionado
        </div>
      </div>
    </div>
    <div class="columnaDerecha">
      <Qalendar class="calendario"
        ref="calendarRef"
        :config="configuracion" 
        :events="eventos"
        @interval-was-clicked="clickEnIntervalo"
        @updated-period="actualizarCalendarioPorPeriodo">
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

export default {
  components: { Qalendar },

  data() {
    return {
      configuracion: {
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
      periodoSeleccionado:null,
      fechaSeleccionada: "",
      ultimosIdGrupoCargados: 0, //TODO controlar que se quede a 0 al cambiar profesor
    }
  },

  computed: {
    ...mapState(useProfesoresStore, ['profesorSeleccionado']),
    ...mapState(useReservasStore, ['reservas', 'eventos', 'reserva'])
  },

  methods: {
    ...mapActions(useReservasStore, ['cargarReservas', 'guardarReserva', 'resetReserva',
      'formatearFechaParaAPI', 'cargarReservasGrupo',
      'mapReservaToEvento', 'agregarEventos', 'quitarUltimosEventosAdded',
      'convertirPeriodToPeriodo']),
    ...mapActions(useAsignaturasStore, ['getAsignaturaPorId']),
    ...mapActions(useGruposStore, ['getGrupoPorId']),
    ...mapActions(useLugaresStore, ['escogerLugarDisponible']),

    /**
     * Función para controlar los click en los intervalos del calendario. Coge la franja 
     * horaria y la fecha sobre la que se ha realizado click y la guarda en 
     * this.fechaSeleccionada 
     * @param evento es el evento elevado desde calendar al hacer click. El contenido se 
     * encuentra en la documentación oficial de Qalendar
     */
    clickEnIntervalo(evento) {
      if (this.profesorSeleccionado !== null) {
        if (this.grupoSeleccionado != null){
          let fecha = evento.intervalStart.substr(0, 10);
          let partes = fecha.split("-");
          fecha = partes[2] + "-" + partes[1] + "-" + partes[0];
          this.fechaSeleccionada = fecha + " "
            + evento.intervalStart.substr(11, 2) + "-" + evento.intervalEnd.substr(11, 2);
        } else {
          alert('Por favor, seleccione un grupo antes de elegir franja horaria');
        }
      }
    },

    /**
     * Función que realiza comprueba si hay lugares disponibles, si lo hay asigna uno y 
     * después realiza la reserva. Si no lo hay informa al usuario.
     */
    async reservar() {
      this.reserva.fecha = this.formatearFechaParaAPI(this.fechaSeleccionada.split(" ")[0]);
      this.reserva.hora = parseInt(this.fechaSeleccionada.split(" ")[1].split("-")[0]);
      let lugar = await this.escogerLugarDisponible(this.asignaturaSeleccionada);
      if (lugar != null) {
        this.reserva.profesor = this.profesorSeleccionado.id;
        this.reserva.asignatura = this.asignaturaSeleccionada;
        this.reserva.grupo = this.grupoSeleccionado;
        this.reserva.lugar = lugar;
        await this.guardarReserva();
        this.fechaSeleccionada = null;
        this.grupoSeleccionado = null;
        await this.refrescarCalendario();
      } else {
        alert('No hay lugares disponibles para esa franja horaria, elija otra franja');
        this.resetReserva();
      }
    },
      
    /**
     * Función que añade a los eventos los del grupo seleccionado para que los profesores puedan ver
     * cuando el grupo no está disponible.
     * TODO cambiar el color de los eventos del grupo para hacerlo un poco más amigable
     */
    async cargarGrupo() {
      this.quitarUltimosEventosAdded(this.ultimosIdGrupoCargados);
      let eventosGrupo = [];
      await this.cargarReservasGrupo(this.grupoSeleccionado, this.periodoSeleccionado)
      .then(reservasGrupo => {
        console.log(reservasGrupo)
        eventosGrupo = reservasGrupo.map((reserva) => {return this.mapReservaToEvento(reserva)})
        eventosGrupo = eventosGrupo.filter(eventoGrupo => !this.eventos.some(evento => evento.id === eventoGrupo.id));
        // console.log(eventosGrupo);
        this.ultimosIdGrupoCargados = eventosGrupo.length;
        this.agregarEventos(eventosGrupo);
      })
      .catch(error => console.log(error));
    },

    /**
     * Funcion que sirve para refrescar la vista del calendario y actualizar sus props.
     * Si se quiere volver al día actual se debe añadir la prop :key y utilizar una variable
     * dummy para que al cambiarla se refresque la vista y devuelva a la fecha actual.
     * Hay que tener en cuenta que la carga de reservas sería la del periodo dado por lo que
     * habría que cargar de nuevo las reservas para el periodo de la fecha actual antes de
     * mostrarlo.
     */
    async refrescarCalendario(){
      await this.cargarReservas(this.convertirPeriodToPeriodo(this.periodoSeleccionado));
    },
    
    async actualizarCalendarioPorPeriodo(periodo){
      this.periodoSeleccionado = periodo;
      let aux = this.convertirPeriodToPeriodo(this.periodoSeleccionado);
      await this.cargarReservas(aux);
      if(this.grupoSeleccionado != null){
        await this.cargarGrupo();
      }
    },
  },

  watch: {
    /**
     * Observador que permite controlar el cambio de un profesor a otro y cambiar los parámetros
     * relacionados con cada uno, que en este caso afectan al formulario (asignaturas y grupos)
     * y al calendario, con las reservas (eventos de Qalendar) de cada uno.
     */
    profesorSeleccionado: {
      handler(nuevoProfesor) {
        if (nuevoProfesor !== null) {
          this.asignaturaSeleccionada = nuevoProfesor.asignaturas[0];
          this.quitarUltimosEventosAdded();
          this.ultimosIdGrupoCargados = 0;
          this.grupoSeleccionado = null;
          this.fechaSeleccionada = null;
          this.cargarReservas(this.convertirPeriodToPeriodo(this.periodoSeleccionado));
        } else {
          this.asignaturaSeleccionada = null;
        }
      },
      immediate: true
    }
  },

  mounted() {
    let fechaFin = new Date(this.$refs.calendarRef.period.end);
    fechaFin.setDate(fechaFin.getDate() + 1);
    this.periodoSeleccionado ={
      start: this.$refs.calendarRef.period.start,
      end: fechaFin
    }
  }
}
</script>

<style scoped>
.formularioReserva {
  display: flex;
  flex-flow: column;
  align-items: center;
}

.contenedorColumnas {
  padding: 16px;
  width: 100%;
  display: flex;
  flex-flow: row;
  justify-content: center;
}

.columnaIzquierda {
  padding: 10px;
  width: 33%;
}

.columnaDerecha {

  padding: 10px;
  width: 67%;
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