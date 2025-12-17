
<template>
  <div id="app">
  <header>
           <div class="container">
            <nav>
                <a href="http://wy667.cloudns.biz" class="logo">Voting</a>
                <ul class="nav-links">
                    <li><RouterLink to="/home?pageNum=1">进行中的投票</RouterLink></li>
                    <li><RouterLink to="/voted">已结束的投票</RouterLink></li>
                    <li><RouterLink to="/contractus">联系我们</RouterLink></li>
                </ul>
              <!-- 登录或用户按钮 -->
                <div v-if="user.isFinited" class="user-info" >
                    <el-dropdown >
                      <span class="el-dropdown-link" @click.stop="goPersonalDirect" style="display:flex; align-items:center; gap:8px;">
                        <el-avatar style="background-color: #4a6bff"> {{showUserId}} </el-avatar>

                      </span>
                      <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click.stop="goPersonalDirect">个人中心</el-dropdown-item>
                            <el-dropdown-item @click.stop="logout">登出</el-dropdown-item>
                          </el-dropdown-menu>
                      </template>
                    </el-dropdown>

                </div>
                <div v-else class="user-info">
                  <RouterLink to="login" class="btn">登录</RouterLink>
                </div>
            </nav>
        </div>
  </header>
</div>
<div>
  <RouterView />
</div>


    <!-- 页脚 -->
    <footer>
        <div class="container">
            <div class="footer-content">
                <div class="footer-column">
                    <h3>关于我们</h3>
                    <p>我们是一家专注于在线投票的网站，致力于为客户提供高质量的数字化解决方案。</p>
                </div>

                <div class="footer-column">
                    <h3>快速链接</h3>
                    <ul class="footer-links">
                        <li><RouterLink to="/home?pageNum=1">进行中的投票</RouterLink></li>
                        <li><RouterLink to="/voted">已结束的投票</RouterLink></li>
                        <li><RouterLink to="/contractus">联系我们</RouterLink></li>
                        <li><a href="https://blog.csdn.net/wyhelloworld?spm=1000.2115.3001.5343">博客</a></li>
                        <li><a href="https://github.com/langderen/voting">github项目地址</a></li>
                    </ul>
                </div>


                <div class="footer-column">
                    <h3>联系方式</h3>
                    <ul class="footer-links">
                        <li>温州理工学院滨海校区宿舍区5-302</li>
                        <li>电话: 19588091990</li>
                        <li>邮箱: 3295586320@qq.com</li>
                    </ul>
                </div>
            </div>

            <div class="copyright">
                <p>&copy; 2025 302voting保留所有权利.</p>
            </div>
        </div>
    </footer>
</template>


<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { userStore } from '@/stores/user';
import axios from 'axios';
import { ElDropdown, ElDropdownMenu, ElDropdownItem, ElAvatar } from 'element-plus';
import { getCurrentInstance } from 'vue';

const instance = getCurrentInstance();
type CookieProxy = {
  $cookies: {
    isKey: (k: string) => boolean;
    get: (k: string) => string | null;
    remove: (k: string) => void;
  };
};
const proxy = (instance?.proxy as unknown) as CookieProxy | undefined;
const user = userStore();
const showUserId = computed(() => {
  if (user.userId.length <= 4) return user.userId;
  return user.userId.slice(0, 2) + '**' + user.userId.slice(-1);
});
// 封装初始化用户逻辑
async function initUser() {
  const token = proxy?.$cookies.get('Authorization');
  if (!token) {
    user.isFinited = false;
    user.userId = '';
    return;
  }

  try {
    // 获取 token 信息
    const tokenRes = await axios({
      url: 'https://frp-six.com:11086/api/login/tokenInfo',
      method: 'GET',
      headers: { Authorization: token },
    });

    const loginId = tokenRes.data.data?.loginId;
    if (!loginId) throw new Error('Invalid token');

    // 先更新 userId（触发响应式）
    user.userId = loginId;
    user.isFinited = true;

    //获取完整用户信息（可选，如果你 store 里有更多字段）
    const userRes = await axios({
      url: `https://frp-six.com:11086/api/user?id=${loginId}`,
      method: 'GET',
    });

    const userData = userRes.data.data;
    if (userData) {
      user.userName = userData.username;
      user.AvatarUrl = userData.avatarUrl;
      user.userEmail = userData.email;
    }
  } catch (error) {
    console.error('Failed to initialize user:', error);
    // 清除无效 token
    proxy?.$cookies.remove('Authorization');
    user.isFinited = false;
    user.userId = '';
  }
}

// 在组件挂载时初始化
onMounted(() => {
  initUser();
});


async function Logout() {
  const res=await axios({
    url: 'https://frp-six.com:11086/api/login/logout',
    method: 'GET',
    headers: {
      Authorization: proxy?.$cookies.get('Authorization') || '',
    },
  })
  return res.data;
}
function logout(){
  user.isFinited = false;
  user.userId = "";
  location.href="/home?pageNum=1";
  proxy?.$cookies.remove('Authorization');
  Logout();
}


function goPersonalDirect() {
  location.href="/personal";
}


</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
