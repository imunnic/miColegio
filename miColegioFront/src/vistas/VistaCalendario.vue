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
          </v-select>
          <v-select v-model="grupoSeleccionado" label="Grupo" :items="['A', 'B']">
          </v-select>
          <v-text-field
            class = "fecha"
            prepend-icon = "mdi-calendar"
            v-model = "fechaSeleccionada">

          </v-text-field>
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
import { mapState } from 'pinia';
import { useProfesoresStore } from '../store/profesoresStore';
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
          displayClickableInterval:true,
          height: 90
        },
        style: {
          fontFamily: 'arial'
        }
      },
      asignaturaSeleccionada: null,
      grupoSeleccionado:null,
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
      fechaSeleccionada:""
    }
  },
  computed: {
    ...mapState(useProfesoresStore, ['profesorSeleccionado'])
  },
  methods: {
    //Maneja el click en un intervalo
    clickEnIntervalo(evento){
      this.fechaSeleccionada = evento.intervalStart.substr(0,10) + " " 
      + evento.intervalStart.substr(11,2) + "-" + evento.intervalEnd.substr(11,2);
    }
  },
  watch: {
    profesorSeleccionado: {
      handler(nuevoProfesor) {
        if (nuevoProfesor !== null) {
          this.asignaturaSeleccionada = nuevoProfesor.asignaturas[0];
        } else {
          this.asignaturaSeleccionada = null;
        }
      },
      immediate: true
    }
  }
}
</script>
<style scoped>
.formularioReserva{
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

.fecha{
  min-width: 200px;
}
@media (max-width: 500px) {
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
<!-- TODO dar la vuelta al formato de la fecha -->