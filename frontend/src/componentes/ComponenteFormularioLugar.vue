<template>
    <v-snackbar v-model="snackAux" :timeout="-1">
    Introduzca los datos del lugar y pulse guardar
  </v-snackbar>
  <v-card>
    <v-toolbar>
          <v-btn
            icon="mdi-close"
            @click="cerrar()"
          ></v-btn>

          <v-toolbar-title>Crear Nuevo Lugar</v-toolbar-title>

          <v-spacer></v-spacer>
    </v-toolbar>
    <v-card-text>
      <v-form>
        <v-text-field v-model="lugarSeleccionado.nombre" label="Nombre" :rules="[rules.requerido]" required></v-text-field>
        <v-text-field v-model="lugarSeleccionado.capacidad" label="Capacidad" type="number" :rules="[rules.numero, rules.positivo, rules.entero]"
          required></v-text-field>

        <v-select v-model="lugarSeleccionado.tipo" :items="['Aula', 'Patio']" label="Tipo" required></v-select>
        <v-checkbox v-if="lugarSeleccionado.tipo == 'Aula'" v-model="lugarSeleccionado.proyector" label="Proyector"></v-checkbox>
        <v-combobox v-if="lugarSeleccionado.tipo == 'Patio'" v-model="lugarSeleccionado.deportes" :items="deportesOptions"
          label="Deportes" clearable chips multiple>
          <template v-slot:selection="{ item, select }">
            <v-chip @click="select" @click:close="quitarDeporte(item)">
              <strong>{{ item.title }}</strong>&nbsp;
            </v-chip>
          </template>
        </v-combobox>

      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="red darken-1" text @click="cerrar()">Cancelar</v-btn>
      <v-btn color="green darken-2" text @click="guardar()">Guardar</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import {useLugaresStore} from '../store/lugaresStores'
import {mapState} from 'pinia'
export default {
  props:{
    snack2:{
      type:Boolean,
    }
  },
  data() {
    return {
      deportesOptions: ['Futbol', 'Baloncesto'],
      rules: {
        requerido: value => !!value || 'Requerido.',
        numero: value => !isNaN(value) || 'Debe introducir un número.',
        positivo: value => value > 0 || 'Debe introducir un número positivo.',
        entero: value => Number.isInteger(Number(value)) || 'Debe introducir un número entero.'
      },
      snackAux:false
    }
  },
  computed:{
    ...mapState(useLugaresStore,['lugarSeleccionado'])
  },
  methods: {
    cerrar() {
      this.$emit('cerrar');
    },
    guardar() {
      this.$emit('guardar');

    },
    quitarDeporte(item) {
      this.chips.splice(this.chips.indexOf(item), 1)
    }
  },
  watch: {
    tipo(newTipo) {
      if (newTipo == 'AULA') {
        this.$set(this.lugar, 'deportes', undefined);
        this.$set(this.lugar, 'proyector', false);
      } else if (newTipo == 'PATIO') {
        this.$set(this.lugar, 'deportes', []);
        this.$set(this.lugar, 'proyector', undefined);
      }
    }
  },
  created(){
    this.snackAux=this.snack2;
  }
}
</script>
<style scoped></style>