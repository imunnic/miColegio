<!-- Vista que permite el login del usuario -->
<template>
  <v-container class="contenedor">
    <v-card elevation="3" class="card">
      Registro de entrada
      <v-form class="formulario" @keyup.enter="intentarLogin()">
        <v-text-field 
          v-model="login.username" 
          variant="outlined" 
          :rules="[rules.required]" 
          label="Nombre de usuario">
        </v-text-field>

        <v-text-field 
          v-model="login.password" 
          variant="outlined" 
          :rules="[rules.required]"
          :append-inner-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
          label="Contraseña" :type="show1 ? 'text' : 'password'"
          @click:append-inner="show1 = !show1">
          <template v-if="intentos > 0" #details>
            <v-spacer />
            <p class="intentoFallido">Contraseña o usuario incorrectos</p>
          </template>
        </v-text-field>

        <v-btn 
          class="mt-2" 
          @click="intentarLogin()">
          Entrar
        </v-btn>
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
      rules: {
        required: value => !!value || 'Campo requerido'
      },
      show1: false,
      login: {
        username: "",
        password: ""
      },
      intentos: 0
    }
  },
  computed: {
    ...mapState(useUsuariosStore, ['isLogged', 'perfil'])
  },
  methods: {
    ...mapActions(useUsuariosStore, ['peticionLogin']),
    async intentarLogin() {
      await this.peticionLogin(this.login);
      if (this.isLogged && this.perfil!= 'GESTOR') {
        this.$router.push('/home');
      } else if(this.isLogged && this.perfil == 'GESTOR') {
        this.$router.push('/lugares');
      } else {
        this.intentos++;
      }
    }
  }
}
</script>

<style scoped>
.intentoFallido {
  color: red;
}

.formulario {
  display: flex;
  flex-flow: column;
  align-items: center;
  min-width: 300px;
  width: 75%;
  padding: 10px;
}

.card {
  display: flex;
  align-items: center;
  width: 33%;
}

.v-text-field {
  width: 75%;
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