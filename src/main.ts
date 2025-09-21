import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
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


//定义路由
const routes = [
  {path:"/",redirect:"/home"},
  {path:"/home",component:HomePage},
  {path:"/voted",component:Voted},
  {path:"/contractus",component:ContractUs},
  {path:"/login",component:Login},
  {path:"/register",component:register},
  {path:"/vote",component:Vote},
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

app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.use(VueCookies)

app.mount('#app')

