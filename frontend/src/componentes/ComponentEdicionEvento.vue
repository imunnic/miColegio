<!-- Componente para el formulario de edición de la reserva -->
<template>
  <v-dialog title="Edición de reserva">
    <div class="contenedor">
      <v-card class="contenido">
        Modificar Reserva
        
        <v-form ref="formularioEdicion"> 
          <v-select 
            v-model="asignatura" 
            label="Asignatura" 
            :items=asignaturas :rules="[v => !!v || 'Seleccione una asignatura']">
            <template v-slot:selection="{ item, index }">
              {{ getAsignaturaPorId(item.props.value).nombre }}
            </template>
            <template v-slot:item="{ props, item }">
              <v-list-item 
                v-bind="props" 
                :title="getAsignaturaPorId(item.props.value).nombre"></v-list-item>
            </template>
          </v-select>
          
          <div class="botonesFormulario">
            <v-btn 
              :prepend-icon="'mdi-check-circle'" 
              @click="emitirEdicion()">
              <template v-slot:prepend>
                <v-icon color="success"></v-icon>
              </template>
              Modificar
            </v-btn>
            
            <v-btn 
              :prepend-icon="'mdi-close-circle'" 
              @click="this.$emit('cancelar')">
              <template v-slot:prepend>
                <v-icon color="error"></v-icon>
              </template>
              Cancelar
            </v-btn>
          </div>
        </v-form>
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
      asignatura: null
    }
  },
  props: ['asignaturas'],
  methods: {
    ...mapActions(useAsignaturasStore, ['getAsignaturaPorId']),
    async emitirEdicion() {
      const { valid } = await this.$refs.formularioEdicion.validate();
      if (valid) {
        this.$emit('finEdicion', this.asignatura);
      }
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

.botonesFormulario {
  display: flex;
  justify-content: end;
}

.v-btn {
  margin: 5px;
}
</style>