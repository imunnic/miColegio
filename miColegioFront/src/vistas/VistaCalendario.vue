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
<<<<<<< Updated upstream
=======
          <v-select v-model="asignaturaSeleccionada" label="Asignaturas" :items="profesorSeleccionado.asignaturas">
            <template v-slot:selection="{ item, index }">
              {{getAsignaturaPorId(item.props.value).nombre}}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" :title="getAsignaturaPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>
          <v-select v-model="grupoSeleccionado" label="Grupo" :items="asignaturaSeleccionada.grupos">
          </v-select>
          <v-text-field class="fecha" prepend-icon="mdi-calendar" v-model="fechaSeleccionada">

          </v-text-field>
>>>>>>> Stashed changes
        </div>
        <div v-else>
          No hay profesor seleccionado
        </div>
      </div>
    </div>
    <div class="columnaDerecha">
<<<<<<< Updated upstream
      <Qalendar class="calendario" :config="configuracion">
=======
      <Qalendar class="calendario" :events="eventos" :config="configuracion" @interval-was-clicked="clickEnIntervalo">
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
          displayClickableInterval: true,
>>>>>>> Stashed changes
          height: 90
        },
        style: {
          fontFamily: 'arial'
        }
<<<<<<< Updated upstream
      }
    }
  },
  computed:{
    ...mapState(useProfesoresStore,['profesorSeleccionado'])
  },
  methods:{
=======
      },
      asignaturaSeleccionada: null,
      grupoSeleccionado: null,
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
      fechaSeleccionada: ""
    }
  },
  computed: {
    ...mapState(useProfesoresStore, ['profesorSeleccionado']),
    ...mapState(useReservasStore, ['reservas', 'eventos'])
  },
  methods: {
    ...mapActions(useReservasStore, ['cargarReservas']),
    ...mapActions(useAsignaturasStore,['getAsignaturaPorId']),
    //Maneja el click en un intervalo
    clickEnIntervalo(evento) {
      if (this.profesorSeleccionado !== null) {
        let fecha = evento.intervalStart.substr(0, 10);
        let partes = fecha.split("-");
        fecha = partes[2] + "-" + partes[1] + "-" + partes[0];
        this.fechaSeleccionada = fecha + " "
          + evento.intervalStart.substr(11, 2) + "-" + evento.intervalEnd.substr(11, 2);
      }
    }
  },
  watch: {
    //Controlar el cambio de profesor para la carga de eventos y los valores a mostrar
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
    console.log(this.reservas);
>>>>>>> Stashed changes
  }
}
</script>
<style scoped>
<<<<<<< Updated upstream
.contenedorColumnas{
=======
.formularioReserva {
  display: flex;
  flex-flow: column;
  align-items: center;
}

.contenedorColumnas {
>>>>>>> Stashed changes
  padding: 16px;
  width: 100%;
  display: flex;
  flex-flow: row;
  justify-content: center;
}
.columnaIzquierda{
  width: 33%;
}
.columnaDerecha{
  width: 67%;
}
.calendario {
  width: 100%;
  max-height: 700px;
  text-align: start;
  overflow-y: scroll;
}
.celdaDia{
  min-height: 60px;
}
<<<<<<< Updated upstream
=======

.fecha {
  min-width: 200px;
}

>>>>>>> Stashed changes
@media (max-width: 500px) {
.contenedorColumnas{
flex-flow: column;
}
.columnaIzquierda{
  width: 100%;
}
.columnaDerecha{
  width: 100%;
}
}
</style>