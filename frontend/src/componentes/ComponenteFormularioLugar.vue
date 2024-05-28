<template>
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
        <v-text-field v-model="lugarNuevo.nombre" label="Nombre" :rules="[rules.requerido]" required></v-text-field>
        <v-text-field v-model="lugarNuevo.capacidad" label="Capacidad" type="number" :rules="[rules.numero, rules.positivo, rules.entero]"
          required></v-text-field>

        <v-select v-model="lugarNuevo.tipo" :items="['AULA', 'PATIO']" label="Tipo" required></v-select>
        <v-checkbox v-if="lugarNuevo.tipo == 'AULA'" v-model="lugarNuevo.proyector" label="Proyector"></v-checkbox>
        <v-combobox v-if="lugarNuevo.tipo == 'PATIO'" v-model="lugarNuevo.deportes" :items="deportesOptions"
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
      <v-btn color="blue darken-1" text @click="cerrar()">Cancelar</v-btn>
      <v-btn color="blue darken-1" text @click="guardar()">Guardar</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      lugarNuevo: {
        nombre: '',
        capacidad: '',
        tipo: 'AULA',
        proyector: false,
        deportes: []
      },
      deportesOptions: ['Futbol', 'Baloncesto'],
      rules: {
        requerido: value => !!value || 'Requerido.',
        numero: value => !isNaN(value) || 'Debe introducir un número.',
        positivo: value => value > 0 || 'Debe introducir un número positivo.',
        entero: value => Number.isInteger(Number(value)) || 'Debe introducir un número entero.'
      }
    }
  },
  methods: {
    cerrar() {
      this.$emit('cerrar');
    },
    guardar() {
      this.$emit('guardar', this.lugar);
      this.cerrar();

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
  }
}
</script>
<style scoped></style>