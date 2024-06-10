<template>
  <v-snackbar v-model="snack1" :timeout="-1" :location="snackPosition" :color="'#C5B6F6'">
    Pulse sobre "Nuevo lugar" para crear un lugar
  </v-snackbar>
  <v-snackbar v-model="snack3" :timeout="3000" :location="snackPosition" :color="'#C5B6F6'">
    Para editar o eliminar un lugar, pulse sobre los iconos correspondientes
  </v-snackbar>
  <div class="tabla">
    <v-card flat>
      <v-card-title class="d-flex align-center pe-2">
        <v-icon icon="mdi-map-marker"></v-icon> &nbsp;
        Lugares
        <v-btn class="crear" prepend-icon="mdi-plus" @click="crear()" >Nuevo Lugar</v-btn>
        <v-spacer></v-spacer>
        <v-text-field v-model="search" density="compact" label="Buscar" 
        prepend-inner-icon="mdi-magnify"
          variant="solo-filled" flat hide-details single-line></v-text-field>
      </v-card-title>

      <v-dialog v-model="mostrarModal" max-width="600">
        <ComponenteFormularioLugar :editar="editar" :snack2="snack2" @cerrar="cerrar()" 
        @guardar="modificarLugares()"></ComponenteFormularioLugar>
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
    <ComponenteConfirmacionBorrado v-model="confirmar" @cancelar-borrado="cancelarBorrado" 
    @borrar-item="borrarLugarConfirmado"></ComponenteConfirmacionBorrado>
    </v-card>
  </div>
</template>
<script>
import ComponenteFormularioLugar from '../componentes/ComponenteFormularioLugar.vue'
import ComponenteConfirmacionBorrado from '../componentes/ComponenteConfirmacionBorrado.vue'
import { useUsuariosStore } from '../store/usuarioStore'
import { useLugaresStore } from '../store/lugaresStores'
import { mapState, mapActions } from 'pinia'
export default {
  components: { ComponenteFormularioLugar, ComponenteConfirmacionBorrado },
  data() {
    return {
      mostrarModal: false,
      editar:false,
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
      snack1:false,
      snack2:false,
      snack3:false,
      snackPosition:'top',
    }
  },
  computed: {
    ...mapState(useLugaresStore, ['lugaresColegio', 'lugarSeleccionado'])
  },
  methods: {
    ...mapActions(useLugaresStore,['arrancarServicioLugares','crearNuevoLugar','cargarLugares',
    'borrarLugar','modificarLugar']),
    crear() {
      if(this.snack1){
        this.snack1=false;
        this.snack2=true;
      }
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

    cerrar() {
      if(this.snack2) {
        this.snack2=false;
        this.snack3=true;
      }
      this.cambiarModo();
      this.editar = false;
    },
    
    async borrarLugarConfirmado() {
      if (this.lugarAEliminar) {
        await this.borrarLugar(this.lugarAEliminar);
        await this.cargarLugares();
        this.cancelarBorrado();
      }
    },

    cambiarModo() {
      this.mostrarModal = !this.mostrarModal;
    },

    editarLugar(item) {
      this.lugarSeleccionado.nombre = item.nombre;
      this.lugarSeleccionado.capacidad = item.capacidad;
      this.lugarSeleccionado.tipo = item.tipo;
      this.lugarSeleccionado.proyector = item.proyector;
      this.lugarSeleccionado.deportes = item.deportes;
      this.lugarSeleccionado.href = item._links.self.href;
      this.editar = true;
      this.cambiarModo();
    },

    async modificarLugares() {
      if(this.snack2) {
        this.snack2=false;
        this.snack3=true;
      }
      if (this.editar == false) {
        await this.crearNuevoLugar();
        this.cambiarModo();
        await this.cargarLugares();
      } else {
        await this.modificarLugar();
        this.cambiarModo();
        await this.cargarLugares();
        this.editar = false;
      }
    }
  },

  mounted() {
    this.arrancarServicioLugares(useUsuariosStore().token);
    this.cargarLugares();
    if (useUsuariosStore().primerInicio) {
      this.snack1 = true;
    }
  }
}
</script>
<style scoped>
.tabla {
  margin: 2vw;
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