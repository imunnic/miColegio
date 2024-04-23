<!-- 
  Componente que construye la vista principal de la página
 -->
<template>
  <v-layout>
    <v-navigation-drawer 
      v-model="drawer" 
      :rail="rail"
      permanent
      class="barraNavegacion"
      @click="rail = false">
      <v-list>
        <v-list-item
          v-for="profesor in profesorado" 
          class="profesor"  
          :key="profesor.nombre" 
          :title="profesor.nombre + ' ' + profesor.apellido"
          prepend-icon="mdi-account" 
        @click.stop="cambiarProfesor(profesor)">
        </v-list-item>
        <v-list-item
          prepend-icon="mdi-account-group"
          :title="'Grupo'"
          @click.stop="navegarAGrupo"
        >
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <router-view class="contenido"></router-view>
    </v-main>
  </v-layout>
</template>
<script>
import { mapActions, mapState } from 'pinia';
import {useProfesoresStore} from '../store/profesoresStore'

export default {
  data() {
    return {
      drawer: true,
      rail: true,
    }
  },
  computed: {
    ...mapState(useProfesoresStore,['profesorado', 'profesorSeleccionado'])
  },
  methods: {
    ...mapActions(useProfesoresStore,['seleccionarProfesor']),
    cambiarProfesor(profesor) {
      this.rail = true;
      this.seleccionarProfesor(profesor);
    },
    navegarAGrupo(){
      this.$router.push('/grupos')
    }
  }
}
</script>
<style scoped>
.profesor:hover {
  cursor: pointer;
}

.barraNavegacion {
  height: 86vh;
}
</style>