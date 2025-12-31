import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createRouter, createWebHistory } from 'vue-router'
import VueCookies from 'vue3-cookies'

const routes = [
  { path: "/", redirect: "/home" },
  { path: "/home", component: () => import('./pages/HomePage.vue') },
  { path: "/voted", component: () => import('./pages/voted.vue') },
  { path: "/contractus", component: () => import('./pages/ContractUs.vue') },
  { path: "/login", component: () => import('./pages/login-page.vue') },
  { path: "/register", component: () => import('./pages/register.vue') },
  { path: "/vote", component: () => import('./pages/vote.vue') },
  { path: "/personal", component: () => import('./pages/personal.vue') },
  { path: "/newpoll", component: () => import('./pages/NewPoll.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate);

app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.use(VueCookies)

app.mount('#app')
