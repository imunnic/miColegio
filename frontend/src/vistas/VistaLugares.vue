<template>
  <div class="tabla">
    <v-card flat>

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

</style>