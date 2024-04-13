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
        @click.stop="cambiarProfesor(profesor)"></v-list-item>
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
      profesores: [
        {
          avatar: "https://randomuser.me/api/portraits/women/83.jpg",
          nombre: "Mary Birmingham"
        },
        {
          avatar: "https://randomuser.me/api/portraits/men/3.jpg",
          nombre: "Patrick Birmingham"
        }
      ]
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