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
      <v-divider></v-divider>
      <v-data-table :headers="headers" :items="lugaresColegio" :search="search"></v-data-table>
    </v-card>
  </div>
</template>
<script>
import { useLugaresStore } from '../store/lugaresStores'
import { mapState } from 'pinia'
export default {
  data() {
    return {
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
        }
      ],
    }
  },
  computed: {
    ...mapState(useLugaresStore, ['lugaresColegio'])
  }
}
</script>
<style scoped>
.tabla{
  margin-left: 2vw;
}
</style>