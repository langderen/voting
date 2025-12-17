
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
                <div v-if="userInfo.isFinited.value" class="user-info" >
                    <el-dropdown >
                      <span class="el-dropdown-link" @click.stop="personal" style="display:flex; align-items:center; gap:8px;">
                        <el-avatar style="background-color: #4a6bff"> {{userInfo.username.value}} </el-avatar>
                      </span>
                      <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click.stop="personal">个人中心</el-dropdown-item>
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

  <RouterView />
</div>
</template>


<script setup lang="ts">
import { userStore } from '@/stores/user';
import { ElDropdown, ElDropdownMenu, ElDropdownItem, ElAvatar } from 'element-plus';
import { storeToRefs } from 'pinia';
import { getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router'

const instance = getCurrentInstance();
type CookieProxy = {
  $cookies: {
    isKey: (k: string) => boolean;
    get: (k: string) => string | null;
    remove: (k: string) => void;
  };
};
const proxy = (instance?.proxy as unknown) as CookieProxy | undefined;
const user=userStore();
//console.log(user.username);
const userInfo=storeToRefs (user);
if (proxy?.$cookies.isKey('username')) {
  userInfo.isFinited.value = true;
  userInfo.username.value = proxy.$cookies.get('username') ?? '';
} else {
  userInfo.isFinited.value = false;
  userInfo.username.value = '';
}

function logout(){
  userInfo.isFinited.value=false;
  userInfo.username.value="";
  proxy?.$cookies.remove('username');
  location.href="/home?pageNum=1";
}

const router = useRouter();

function personal() {
  location.href = '/personal';
}


</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
  text-decoration: none;
}
</style>
