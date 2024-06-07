<template>
    <v-card class="pa-4 contenedor" flat>
      <v-card-title class="headline">Leyenda de Reservas</v-card-title>
      <v-card-text class="texto">
        <v-row>
          <v-col class="columna" cols="12" md="4" v-for="(color, tipo) in reservationColors" 
          :key="tipo">
            <v-sheet :color="color" class="pa-2" elevation="2" width="50" height="50"></v-sheet>
            <span class="ml-3">{{ tipo }}</span>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </template>
  
  <script>
  export default {
    name: 'ReservationLegend',
    data() {
      return {
        reservationColors: {
          'Propias': '#15054C',
          'No disponible': '#C5B6F6',
          'Grupo': '#054D09'
        }
      };
    }
  }
  </script>
  
  <style scoped>

  .columna {
    display: flex;
    align-items: center;
    align-content: center;
  }

  .texto {
    display: ruby;
  }
  </style>
  