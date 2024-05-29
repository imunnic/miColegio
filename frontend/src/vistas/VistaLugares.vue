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
      <v-btn @click="editar()">Crear</v-btn>
      <v-dialog v-model="edicion" max-width="600">
        <ComponenteFormularioLugar @cerrar="editar()"></ComponenteFormularioLugar>
      </v-dialog>
      <v-divider></v-divider>
      <v-data-table :headers="headers" :items="lugaresColegio" :search="search"></v-data-table>
    </v-card>
  </div>
</template>
<script>
import ComponenteFormularioLugar from '../componentes/ComponenteFormularioLugar.vue'
import { useLugaresStore } from '../store/lugaresStores'
import { mapState } from 'pinia'
export default {
  components:{ComponenteFormularioLugar},
  data() {
    return {
      edicion:false,
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
          title:"Acciones",
          key:'actions',
          align:'center'
        }
      ],
    }
  },
  computed: {
    ...mapState(useLugaresStore, ['lugaresColegio'])
  },
  methods:{
    editar(){
      this.edicion = !this.edicion;
    }
  }
}
</script>
<style scoped>
.tabla{
  margin-left: 2vw;
}
</style>