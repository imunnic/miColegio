import { defineStore } from "pinia"
import grupos from "../assets/grupos.json"

export const useGruposStore = defineStore('grupos',{
    state:() => ({
        gruposAlumnos:grupos.grupos,
        gruposSeleccionado:null
    }),
    actions:{
        seleccionarGrupo(grupo){
            this.gruposSeleccionado = grupo;
        },
        getGrupoPorId(id){
            return this.gruposAlumnos.find(grupo => grupo.id == id);
        }
    }
})