import { defineStore } from "pinia"
import profesores from "../assets/profesores.json"

export const useProfesoresStore = defineStore('profesorado', {
    state:() => ({
        profesorado:profesores.profesores,
        profesorSeleccionado:null
    }),
    actions: {
        seleccionarProfesor(profesor){
            this.profesorSeleccionado = profesor;
        },
        getProfesorPorId(id){
            return this.profesorado.find(profesor => profesor.id == id);
        }
    }
})