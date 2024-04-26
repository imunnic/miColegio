<template>
    <v-dialog title="Edición de reserva">
      <div class="contenedor">
        <v-card class="contenido">
          Modificar Reserva
          <v-select v-model="asignatura" label="Asignatura" :items=asignaturas>
            <template v-slot:selection="{ item, index }">
              {{ getAsignaturaPorId(item.props.value).nombre }}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props" :title="getAsignaturaPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>
          <v-btn @click="emitirEdicion()">Cerrar</v-btn>
        </v-card>
      </div>
    </v-dialog>
  </template>
<script>
import { mapActions } from 'pinia';
import { useAsignaturasStore } from '../store/asignaturasStore';
export default {
  data() {
    return {
      asignatura:null
    }
  },
  props: ['asignaturas'],
  methods: {
    ...mapActions(useAsignaturasStore,['getAsignaturaPorId']),
    emitirEdicion(){
      this.$emit('finEdicion', this.asignatura);
    }
  }

}
</script>

<style scoped>
.contenedor {
  display: flex;
  justify-content: center;
}

.contenido {
  width: 33%;
  padding: 15px;
}
</style>