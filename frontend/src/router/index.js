import { createRouter, createWebHashHistory } from "vue-router";
import { useUsuariosStore } from "../store/usuarioStore";

const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/home",
    name: "home",
    component: () => import("../vistas/VistaCalendario.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/grupos",
    name: "grupos",
    component: () => import("../vistas/VistaCalendarioGrupo.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../vistas/VistaLogin.vue"),
  },
  {
    path: "/dashboard",
    name: "panel_control",
    component: () => import("../vistas/VistaPanelControl.vue"),
    meta: {requiresAuth:true, requiresPerfil:'GESTOR'}
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const usuariosStore = useUsuariosStore();
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const requiresPerfil = to.matched.some(record => record.meta.requiresPerfil);

  if (requiresAuth && !usuariosStore.isLogged) {
    next("/login");
  } else if (requiresPerfil && usuariosStore.perfil !== to.meta.requiresPerfil) {
    next("/home");
  } else {
    next();
  }
});

export default router;
