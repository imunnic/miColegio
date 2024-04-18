
import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path:'/',
        name:'home',
        component: () => import('../vistas/VistaCalendario.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router;

