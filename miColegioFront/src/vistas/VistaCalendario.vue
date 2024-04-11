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
          <v-select 
            v-model="asignaturaSeleccionada"
            label="Asignaturas"
            :items="profesorSeleccionado.asignaturas">
          </v-select>
          <v-btn @click="console.log(asignaturaSeleccionada)">
            Ver Asignatura
          </v-btn>
        </div>
        <div v-else>
          No hay profesor seleccionado
        </div>
      </div>
    </div>
    <div class="columnaDerecha">
      <Qalendar class="calendario" :config="configuracion">
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
          height: 90
        },
        style: {
          fontFamily: 'arial'
        }
      },
      asignaturaSeleccionada:null
    }
  },
  computed: {
    ...mapState(useProfesoresStore, ['profesorSeleccionado'])
  },
  methods: {
  }
}
</script>
<style scoped>
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
<!-- TODO Controlar que se anule la asignatura al cambiar de profesor -->