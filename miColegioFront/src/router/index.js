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
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!useUsuariosStore().isLogged) {
      next("/login");
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
