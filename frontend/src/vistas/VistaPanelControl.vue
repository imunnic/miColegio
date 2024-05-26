<!-- Vista que permite al gestor controlar asignaturas, profesores, lugares y grupos -->
<template>
  <div class="panel_control">
    <v-card class="mx-auto my-8 lugares" elevation="16" max-width="344" @click="navegarALugares()">
      <v-card-item>
        <v-card-title>
          Lugares
        </v-card-title>

        <v-card-subtitle>
          Gestione los lugares de su centro educativo
        </v-card-subtitle>
      </v-card-item>

      <v-card-text>
        Pulse aquí para gestionar los lugares de su centro educativo.
      </v-card-text>
    </v-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      
    }
  },
  methods: {
    navegarALugares() {
      this.$router.push('/lugares');
    }
  }
}
</script>
<style scoped>
.panel_control {
  padding: 16px;
}

.lugares:hover {
  cursor: pointer;
}
</style>