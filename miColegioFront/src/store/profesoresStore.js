import { defineStore } from "pinia"
import profesores from "../assets/profesores.json"

export const useProfesoresStore = defineStore('profesorado', {
    state:() => ({
        profesorado:[
                {
                    "nombre": "Alejandro",
                    "apellido": "Martínez",
                    "avatar": "https://randomuser.me/api/portraits/men/5.jpg",
                    "asignaturas": ["Matemáticas", "Física"]
                  },
                  {
                    "nombre": "Sofía",
                    "apellido": "García",
                    "avatar": "https://randomuser.me/api/portraits/women/5.jpg",
                    "asignaturas": ["Lengua Española", "Literatura"]
                  },
                  {
                    "nombre": "Javier",
                    "apellido": "López",
                    "avatar": "https://randomuser.me/api/portraits/men/10.jpg",
                    "asignaturas": ["Historia", "Geografía"]
                  },
                  {
                    "nombre": "María",
                    "apellido": "Rodríguez",
                    "avatar": "https://randomuser.me/api/portraits/women/10.jpg",
                    "asignaturas": ["Ciencias Naturales", "Química"]
                  },
                  {
                    "nombre": "Carlos",
                    "apellido": "Fernández",
                    "avatar": "https://randomuser.me/api/portraits/men/15.jpg",
                    "asignaturas": ["Educación Física", "Deportes"]
                  }
        ],
        profesorSeleccionado:null
    }),
    actions: {
        seleccionarProfesor(profesor){
            this.profesorSeleccionado = profesor;
        }
    }
})