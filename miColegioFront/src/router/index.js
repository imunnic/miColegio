
import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path:'/',
        name:'home',
        component: () => import('../vistas/VistaCalendario.vue')
    },
    {
        path:'/grupos',
        name:'grupos',
        component: () => import('../vistas/VistaCalendarioGrupo.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router;

