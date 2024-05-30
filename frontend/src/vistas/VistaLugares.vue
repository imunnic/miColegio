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
          <v-icon class="me-2 iconoEditar" size="small" @click="editarLugar(item)">
            mdi-pencil
          </v-icon>
          <v-icon class="iconoBorrar" size="small" @click="confirmarBorrar(item)">
            mdi-delete
          </v-icon>
        </template>
        <template v-slot:no-data>
          No hay lugares
        </template>
      </v-data-table>

      <v-dialog v-model="confirmar" max-width="600">
      <v-card>
        <v-card-title class="headline">Confirmar Borrado</v-card-title>
        <v-card-text>
          ¿Estás seguro de que deseas borrar este lugar?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red darken-1" text @click="cancelarBorrado">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="borrarLugarConfirmado">Borrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

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
      //TODO controlar si se está editando o no para hacer un put o un post dependiendo
      //si se crea o si se edita
      edicion: false,
      confirmar:false,
      lugarAEliminar:null,
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
    ...mapActions(useLugaresStore,['arrancarServicio','crearNuevoLugar','cargarLugares','borrarLugar']),
    crear() {
      this.lugarSeleccionado.nombre = '';
      this.lugarSeleccionado.capacidad = null;
      this.lugarSeleccionado.tipo = 'Aula';
      this.lugarSeleccionado.proyector = false;
      this.lugarSeleccionado.deportes = null;
      this.cambiarModo();
    },
    confirmacion() {
      this.confirmar = !this.confirmar;
    },
    confirmarBorrar(item) {
      this.confirmacion();
      this.lugarAEliminar = item;
    },
    cancelarBorrado() {
      this.confirmacion();
      this.lugarAEliminar = null;
    },
    async borrarLugarConfirmado() {
      if (this.lugarAEliminar) {
        await this.borrarLugar(this.lugarAEliminar);
        await this.cargarLugares();
        this.cancelarBorrado();
      }
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
    async crearLugar() {
      await this.crearNuevoLugar();
      this.cambiarModo();
      await this.cargarLugares();
    }
  },
  mounted() {
    this.arrancarServicio(useUsuariosStore().token);
    this.cargarLugares();
  }
}
</script>
<style scoped>
.tabla {
  margin-left: 2vw;
}
.crear {
  margin-left: 10px;
}
.iconoBorrar:hover {
  color: rgb(168, 17, 17);
}
.iconoEditar:hover {
  color: rgb(27, 124, 27);
}
</style>