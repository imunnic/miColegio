<template>
  <div class="contenedorColumnas">
    <div class="columnaIzquierda">
      <div class="formularioReserva">
        <v-select v-model="grupoSeleccionado" label="Grupo" :items="gruposAlumnos"
          @update:modelValue="cargarGrupo()">
          <template v-slot:selection="{ item, index }">
            {{ item.props.value.nombre }}
          </template>
          <template v-slot:item="{ props, item }">
            <v-list-item v-bind="props" :title="item.props.value.nombre"></v-list-item>
          </template>
        </v-select>
      </div>
    </div>
    <div class="columnaDerecha">
      <Qalendar class="calendario" 
        ref="calendarRef" 
        :config="configuracion" 
        :events="eventos"
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
import { useReservasStore } from '../store/reservasStore';
import { useGruposStore } from '../store/gruposStore';
export default {
  components: {Qalendar},
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
      eventos:[],
      grupoSeleccionado:null,
      periodoSeleccionado:null,
    }
  },
  computed:{
    ...mapState(useGruposStore,['gruposAlumnos'])
  },
  methods:{
    ...mapActions(useReservasStore,['cargarReservasGrupo','mapReservaToEvento',
  'convertirPeriodToPeriodo']),

    async actualizarCalendarioPorPeriodo(periodo){
      if(this.grupoSeleccionado != null){
        this.periodoSeleccionado = periodo;
        await this.cargarGrupo()
      }
    },

    async cargarGrupo(){
      await this.cargarReservasGrupo(this.grupoSeleccionado.id, this.periodoSeleccionado)
      .then(response => {
        this.eventos = response.map((reserva) => {
          return this.mapReservaToEvento(reserva);
        });
      });
    }
  },
  
  mounted(){
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