import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
//import router from './router'
import { createRouter, createWebHistory } from 'vue-router'


import HomePage from './pages/HomePage.vue'
import Voted from './pages/voted.vue'
import ContractUs from './pages/ContractUs.vue'
import Login from './pages/login.vue'
import register from './pages/register.vue'
//定义路由
const routes = [
  {path:"/",redirect:"/home"},
  {path:"/home",component:HomePage},
  {path:"/voted",component:Voted},
  {path:"/contractus",component:ContractUs},
  {path:"/login",component:Login},
  {path:"/register",component:register}
]

//创建路由
const router = createRouter({
  history:createWebHistory(),
  routes
})

//加载路由
const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
