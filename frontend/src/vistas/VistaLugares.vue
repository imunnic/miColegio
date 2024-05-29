<template>
  <div class="tabla">
    <v-card flat>
      <v-card-title class="d-flex align-center pe-2">
        <v-icon icon="mdi-map-marker"></v-icon> &nbsp;
        Lugares
        <v-btn class="crear" prepend-icon="mdi-plus" @click="crear()" >Nuevo Lugar</v-btn>
        <v-spacer></v-spacer>
        <v-text-field v-model="search" density="compact" label="Buscar" prepend-inner-icon="mdi-magnify"
          variant="solo-filled" flat hide-details single-line></v-text-field>
      </v-card-title>

      <v-dialog v-model="edicion" max-width="600">
        <ComponenteFormularioLugar @cerrar="cambiarModo()" @guardar="crearLugar()"></ComponenteFormularioLugar>
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
          No hay lugares
        </template>
      </v-data-table>
    </v-card>
  </div>
</template>
<script>
import ComponenteFormularioLugar from '../componentes/ComponenteFormularioLugar.vue'
import { useUsuariosStore } from '../store/usuarioStore'
import { useLugaresStore } from '../store/lugaresStores'
import { mapState, mapActions } from 'pinia'
export default {
  components: { ComponenteFormularioLugar },
  data() {
    return {
      edicion: false,
      search: '',
      headers: [
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
    ...mapState(useLugaresStore, ['lugaresColegio', 'lugarSeleccionado'])
  },
  methods: {
    ...mapActions(useLugaresStore,['arrancarServicio','crearNuevoLugar']),
    crear() {
      this.lugarSeleccionado.nombre = '';
      this.lugarSeleccionado.capacidad = null;
      this.lugarSeleccionado.tipo = 'Aula';
      this.lugarSeleccionado.proyector = false;
      this.lugarSeleccionado.deportes = null;
      this.cambiarModo();
    },
    cambiarModo() {
      this.edicion = !this.edicion;
    },
    editarLugar(item) {
      this.lugarSeleccionado.nombre = item.nombre;
      this.lugarSeleccionado.capacidad = item.capacidad;
      this.lugarSeleccionado.tipo = item.tipo;
      this.lugarSeleccionado.proyector = item.proyector;
      this.lugarSeleccionado.deportes = item.deportes;
      this.cambiarModo();
    },
    crearLugar() {
      this.crearNuevoLugar();
      this.cambiarModo();
    }
  },
  mounted() {
    this.arrancarServicio(useUsuariosStore().token);
  }
}
</script>
<style scoped>
.tabla {
  margin-left: 2vw;
}
.crear{
  margin-left: 10px;
}
</style>