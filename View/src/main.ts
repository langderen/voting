import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue'
<<<<<<< HEAD
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

=======
import  ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createRouter, createWebHistory } from 'vue-router'

import HomePage from './pages/HomePage.vue'
import Voted from './pages/voted.vue'
import ContractUs from './pages/ContractUs.vue'
import Login from './pages/login-page.vue'
import register from './pages/register.vue'
import Vote from './pages/vote.vue'
import VueCookies from 'vue3-cookies'
import Personal from './pages/personal.vue'
import Newpolls from './pages/NewPoll.vue'


//定义路由
const routes = [
  {path:"/",redirect:"/home"},
  {path:"/home",component:HomePage},
  {path:"/voted",component:Voted},
  {path:"/contractus",component:ContractUs},
  {path:"/login",component:Login},
  {path:"/register",component:register},
  {path:"/vote",component:Vote},
  {path:"/personal",component:Personal},
  {path:"/newpoll",component:Newpolls},
]

//创建路由
const router = createRouter({
  history:createWebHistory(),
  routes
})

//加载路由
const app = createApp(App)

//Pinia配置

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate);
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.use(VueCookies)

app.mount('#app')
<<<<<<< HEAD
=======

>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
