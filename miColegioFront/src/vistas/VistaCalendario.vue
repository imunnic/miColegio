<!-- 
  Vista que permite la visualización de las reservas de un profesor y permite reservar
  franjas horarias para una asignatura, grupo y lugar. 
-->
<template>
  <div class="contenedorColumnas">
    <div class="columnaIzquierda">
      <div class="formularioReserva">
        <div v-if="profesorSeleccionado != null">
          {{ profesorSeleccionado.nombre }} {{ profesorSeleccionado.apellido }}
          
          <v-select v-model="asignaturaSeleccionada" label="Asignaturas" :items="profesorSeleccionado.asignaturas">
            <template v-slot:selection="{ item, index }">
              {{getAsignaturaPorId(item.props.value).nombre}}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" :title="getAsignaturaPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>
          
          <v-select v-model="grupoSeleccionado" label="Grupo" :items="getAsignaturaPorId(asignaturaSeleccionada).grupos">
            <template v-slot:selection="{ item, index }">
              {{getGrupoPorId(item.props.value).nombre}}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" :title="getGrupoPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>
          
          <v-text-field class="fecha" prepend-icon="mdi-calendar" v-model="fechaSeleccionada" :disabled="true">
          </v-text-field>

          <v-btn @click="reservar">Reservar</v-btn>
    
        </div>
        <div v-else>
          No hay profesor seleccionado
        </div>
      </div>
    </div>
    <div class="columnaDerecha">
      <Qalendar 
        class="calendario" 
        :config="configuracion"
        @interval-was-clicked="clickEnIntervalo"
        :events="eventos"
        >
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
import {useAsignaturasStore} from '../store/asignaturasStore';
import { useGruposStore } from '../store/gruposStore';

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
      fechaSeleccionada: ""
    }
  },
  computed: {
    ...mapState(useProfesoresStore, ['profesorSeleccionado']),
    ...mapState(useReservasStore, ['reservas', 'eventos', 'reserva'])
  },
  methods: {
    ...mapActions(useReservasStore, ['cargarReservas', 'guardarReserva', 'resetReserva', 'formatarFechaParaAPI', 'escogerLugarDisponible']),
    ...mapActions(useAsignaturasStore,['getAsignaturaPorId']),
    ...mapActions(useGruposStore,['getGrupoPorId']),
    /**
     * Función para controlar los click en los intervalos del calendario. Coge la franja 
     * horaria y la fecha sobre la que se ha realizado click y la guarda en 
     * this.fechaSeleccionada 
     * @param evento es el evento elevado desde calendar al hacer click. El contenido se 
     * encuentra en la documentación oficial de Qalendar
     */
    clickEnIntervalo(evento) {
      if (this.profesorSeleccionado !== null) {
        let fecha = evento.intervalStart.substr(0, 10);
        let partes = fecha.split("-");
        fecha = partes[2] + "-" + partes[1] + "-" + partes[0];
        this.fechaSeleccionada = fecha + " "
          + evento.intervalStart.substr(11, 2) + "-" + evento.intervalEnd.substr(11, 2);
      }
    },
    reservar(){
      this.reserva.profesor = this.profesorSeleccionado.id;
      this.reserva.asignatura = this.asignaturaSeleccionada;
      this.reserva.grupo = this.grupoSeleccionado;
      this.reserva.lugar = 3;
      this.reserva.fecha = this.formatarFechaParaAPI(this.fechaSeleccionada.split(" ")[0]);
      this.reserva.hora = parseInt(this.fechaSeleccionada.split(" ")[1].split("-")[0]);
      // this.guardarReserva();
      this.escogerLugarDisponible(this.asignaturaSeleccionada);
      this.resetReserva();
      // this.cargarReservas();
    }
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
          this.cargarReservas();
        } else {
          this.asignaturaSeleccionada = null;
        }
      },
      immediate: true
    }
  },
  mounted() {
    this.cargarReservas();
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