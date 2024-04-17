
import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path:'/',
        name:'home',
        component: () => import('../vistas/VistaCalendario.vue')
    },
    {
        path:'/qalendar',
        name:'qalendar',
        component: () => import('../vistas/VistaQalendar.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router;

