<template>
    <v-dialog max-width="600">
        <v-card>
          <v-card-title class="headline">Confirmar Borrado</v-card-title>
          <v-card-text>
            ¿Estás seguro de que deseas borrar este elemento?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red darken-1" text @click="$emit('cancelar-borrado')">Cancelar</v-btn>
            <v-btn color="green darken-1" text @click="$emit('borrar-item')">Borrar</v-btn>
          </v-card-actions>
        </v-card>
    </v-dialog>
</template>
<script>
export default{

}
</script>