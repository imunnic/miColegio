<template>
  <div class="tabla">
    <v-card flat>
      <v-card-title class="d-flex align-center pe-2">
        <v-icon icon="mdi-map-marker"></v-icon> &nbsp;
        Lugares
        <v-spacer></v-spacer>
        <v-text-field v-model="search" density="compact" label="Buscar" prepend-inner-icon="mdi-magnify"
          variant="solo-filled" flat hide-details single-line></v-text-field>
      </v-card-title>
      <v-btn @click="cambiarModo()">Crear</v-btn>
      <v-dialog v-model="edicion" max-width="600">
        <ComponenteFormularioLugar @cerrar="cambiarModo()"></ComponenteFormularioLugar>
      </v-dialog>
      <v-divider></v-divider>
      <v-data-table :headers="headers" :items="lugaresColegio" :search="search">
        <template v-slot:item.actions="{ item }">
          <v-icon class="me-2" size="small" @click="editarLugar(item)">
            mdi-pencil
          </v-icon>
          <v-icon size="small" @click="borrarLugar(item)">
            mdi-delete
          </v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="primary" @click="initialize()">
            Cargar datos
          </v-btn>
        </template>
      </v-data-table>
    </v-card>
  </div>
</template>
<script>
import ComponenteFormularioLugar from '../componentes/ComponenteFormularioLugar.vue'
import { useLugaresStore } from '../store/lugaresStores'
import { mapState } from 'pinia'
export default {
  components: { ComponenteFormularioLugar },
  data() {
    return {
      edicion: false,
      search: '',
      headers: [
        {
          title: 'Id',
          align: 'center',
          sortable: true,
          key: 'id'
        },
        {
          title: 'Tipo',
          align: 'center',
          sortable: true,
          key: 'tipo'
        },
        {
          title: 'Nombre',
          key: 'nombre',
          align: 'center',
          sortable: true
        },
        {
          title: 'Capacidad',
          key: 'capacidad',
          align: 'center',
          sortable: true
        },
        {
          title: "Acciones",
          key: 'actions',
          align: 'center'
        }
      ],
    }
  },
  computed: {
    ...mapState(useLugaresStore, ['lugaresColegio','lugarSeleccionado'])
  },
  methods: {
    crear() {
      this.cambiarModo();
    },
    cambiarModo() {
      this.edicion = !this.edicion;
    },
    editarLugar() {
      this.cambiarModo();
    }

  }
}
</script>
<style scoped>
.tabla {
  margin-left: 2vw;
}
</style>