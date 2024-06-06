<template>
  <div>
    <v-container>
      <v-card v-for="item in items" class="mb-4" @click="$emit('opcion-seleccionada', item)">
        <v-card-subtitle>{{ getAsignaturaPorId(item[0]).nombre }}</v-card-subtitle>
        <v-card-text class="texto"> {{ getGrupoPorId(item[1]).nombre }}</v-card-text>
        <v-card-text class="texto">{{ accessLugarPorId(item[2]).nombre }}</v-card-text>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import { useAsignaturasStore } from '../store/asignaturasStore';
import { useGruposStore } from '../store/gruposStore';
import { useLugaresStore } from '../store/lugaresStores';
import { mapActions } from 'pinia';
export default {
  name: 'opcionesReserva',
  props: {
    items: {
      type: Array,
      required: true
    }
  },
  methods:{
    ...mapActions(useAsignaturasStore, ['getAsignaturaPorId']),
    ...mapActions(useGruposStore, ['getGrupoPorId']),
    ...mapActions(useLugaresStore, ['accessLugarPorId'])
  }
}
</script>

<style scoped>
.texto{
  padding: 5px;
}
</style>