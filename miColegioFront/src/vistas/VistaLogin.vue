<template>
  <v-container class="contenedor">
    <v-card elevation="3" class="card">
      Registro de entrada
      <v-form class="formulario">
        <v-text-field v-model="login.username" label="Nombre de usuario">
        </v-text-field>

        <v-text-field v-model="login.password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" label="Contraseña"
          :type="show1 ? 'text' : 'password'" @click:append="show1 = !show1">
        </v-text-field>

        <v-btn class="mt-2" @click="intentarLogin()">Entrar</v-btn>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import { useUsuariosStore } from '../store/usuarioStore';
import { mapState, mapActions } from 'pinia';
export default {
  data() {
    return {
      show1: false,
      login: {
        username: "alejandro",
        password: "alejandro"
      }
    }
  },
  computed: {
    ...mapState(useUsuariosStore, ['isLogged'])
  },
  methods: {
    ...mapActions(useUsuariosStore, ['peticionLogin']),
    async intentarLogin() {
      await this.peticionLogin(this.login);
      if (this.isLogged) {
        this.$router.push('/home');
      }
    }
  }
}
</script>

<style scoped>
.formulario {
  padding: 10px;
}

.card {
  display: flex;
  justify-content: center;
  width: 45%;
}

.contenedor {
  display: flex;
  justify-content: center;
}

@media (max-width: 800px) {
  .card {
    width: 55%;
  }
}

@media (max-width: 550px) {
  .card {
    width: 90%;
  }
}
</style>