import App from './App.vue'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { createVuetify } from 'vuetify'
import router from './router/index'
import * as directives from 'vuetify/directives'
import * as components from 'vuetify/components'
import * as bootstrap from 'bootstrap'
import 'vuetify/styles'
import {es} from 'vuetify/locale' //importar idioma español
import '@mdi/font/scss/materialdesignicons.scss'
import './scss/styles.scss'
import './style.css'

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi',
  },
  locale:{ //poner el local en español para traducir los textos por defecto
    locale:'es',
    fallback:'es',
    messages: {es}
  }
})

const pinia = createPinia()

createApp(App)
.component('font-awesome-icon', FontAwesomeIcon)
.use(router)
.use(pinia)
.use(vuetify)
.mount('#app')

