
import { createRouter, createWebHistory, type RouteLocationNormalized } from 'vue-router'

const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', name: 'home', component: () => import('../pages/HomePage.vue') },
  { path: '/vote', name: 'vote', component: () => import('../pages/vote.vue') },
  { path: '/voted', name: 'voted', component: () => import('../pages/voted.vue') },
  { path: '/newpoll', name: 'newpoll', component: () => import('../pages/NewPoll.vue') },
  { path: '/contractus', name: 'contractus', component: () => import('../pages/ContractUs.vue') },
  { path: '/login', name: 'login', component: () => import('../pages/login-page.vue') },
  { path: '/register', name: 'register', component: () => import('../pages/register.vue') },
  // personal center
  { path: '/personal', name: 'personal', component: () => import('../pages/personal.vue'), meta: { requiresAuth: true } },
  // fallback
  { path: '/:pathMatch(.*)*', redirect: '/home' },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// 全局路由守卫：处理需要登录的路由（meta.requiresAuth）
router.beforeEach((to: RouteLocationNormalized, from: RouteLocationNormalized, next) => {
  const requiresAuth = !!(to.meta && (to.meta as Record<string, unknown>).requiresAuth);
  if (requiresAuth) {
    // 简单检查 cookie 中是否存在 username（项目中使用 $cookies 存储用户名）
    const isLogged = typeof document !== 'undefined' && document.cookie.includes('username=');
    if (!isLogged) {
      // 未登录，跳转到登录页并携带重定向目标
      return next({ name: 'login', query: { redirect: to.fullPath } });
    }
  }
  next();
});

export default router

