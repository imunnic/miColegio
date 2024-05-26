<!-- 
  Componente que construye la vista principal de la página
 -->
<template>
  <v-layout>
    <v-navigation-drawer v-if="isLogged"
      v-model="drawer" 
      :rail="rail"
      permanent
      class="barraNavegacion"
      @click="rail = false">
      <v-list>
        <v-list-item
          class="profesor"  
          :key="profesorSeleccionado.nombre" 
          :title="profesorSeleccionado.nombre + ' ' + profesorSeleccionado.apellido"
          prepend-icon="mdi-account" 
        @click.stop="navegarAHome(profesor)">
        </v-list-item>
        <!-- <v-list-item
          prepend-icon="mdi-account-group"
          :title="'Grupo'"
          @click.stop="navegarAGrupo"
        >
        </v-list-item> -->
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <div class="vista">
        <router-view></router-view>
      </div>
    </v-main>
  </v-layout>
</template>
<script>
import { mapActions, mapState } from 'pinia';
import {useProfesoresStore} from '../store/profesoresStore'
import { useUsuariosStore } from '../store/usuarioStore';

export default {
  data() {
    return {
      drawer: true,
      rail: true,
    }
  },
  computed: {
    ...mapState(useProfesoresStore,['profesorado', 'profesorSeleccionado']),
    ...mapState(useUsuariosStore,['isLogged'])
  },
  methods: {
    ...mapActions(useProfesoresStore,['seleccionarProfesor']),
    cambiarProfesor(profesor) {
      this.rail = true;
      this.seleccionarProfesor(profesor);
    },
    navegarAHome(){
      this.$router.push('/home')
    },
    navegarAGrupo(){
      this.$router.push('/grupos')
    }
  }
}
</script>
<style scoped>
.vista {
  margin: 2vw;
  margin-left: 0vw;
}
.profesor:hover {
  cursor: pointer;
}

.barraNavegacion {
  height: 86vh;
}
</style>