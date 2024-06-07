<!-- Accesible en  micolegio.netlify.app-->
<script>
import ComponenteHeader from './componentes/ComponenteHeader.vue'
import ComponenteNavegador from './componentes/ComponenteNavegador.vue'
import ComponenteFooter from './componentes/ComponenteFooter.vue'
export default {
  components: { ComponenteHeader, ComponenteNavegador, ComponenteFooter }
}
</script>

<template>
  <v-app>
    <v-main>
      <ComponenteHeader class="cabecera" color="#15054c"></ComponenteHeader>
      <ComponenteNavegador class="contenido"></ComponenteNavegador>
      <ComponenteFooter id="pie" color="#15054c"></ComponenteFooter>
    </v-main>
  </v-app>
</template>

<style scoped>
.cabecera {
  height: 75px;
}

.contenido {
  min-height: 88vh;
  max-width: none;
}

#pie {
  z-index: 1000;
  height: 100px;
  width: 100vw;
}
</style>
