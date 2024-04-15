import { defineStore } from "pinia"
import lugares from "../assets/lugares.json"

export const useLugaresStore = defineStore('lugares',{
    state:() => ({
        lugaresColegio:lugares.lugares,
        lugarSeleccionado:null
    }),
    actions:{
        seleccionarLugar(lugar){
            this.lugarSeleccionado = lugar;
        },
        getLugarPorId(id){
            return this.lugaresColegio.find(lugar => lugar.id == id);
        }
    }
})