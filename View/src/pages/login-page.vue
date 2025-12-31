<template>
    <div class="container">
        <section class="floating">
        <div class="section-title">
                <h2>用户登录</h2>
                <p>登录以管理您的投票</p>
            </div>
              <form @submit.prevent="handleLogin" class="floating-form">
                <div class="input-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" v-model="username" required maxlength="20" class="input-control" placeholder="请填写用户名"/>
                    <span class="highlight"></span>
                </div>
                <div class="input-group">
                      <label for="password">密码</label>
                    <input type="password" id="password" v-model="password" required minlength="6" maxlength="20" class="input-control" placeholder="请填写您的密码"/>
                    <span class="highlight"></span>
                </div>

                <button type="submit" class="submit-btn" >
                    <span>登录</span>
                    <Vcode :show="isShow" @success="onSuccess" @close="onClose" />
                    <i class="arrow-icon"></i>
                </button>
                <div class="form-footer">
                    <span>还没有账号？</span>
                    <RouterLink to="/register">立即注册</RouterLink>
                </div>
            </form>
        </section>

    </div>
 <!-- 提示框 -->
      <div v-if="toast.show" :class="['toast', toast.type === 'success' ? 'toast-success' : 'toast-error']">{{ toast.message }}</div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue'
import { useRouter } from 'vue-router'
// 验证码组件，爆红也没事
import Vcode from "vue3-puzzle-vcode";

import {getCurrentInstance} from 'vue'
import { userStore } from '@/stores/user';

const {proxy} = getCurrentInstance()
const router = useRouter()
const username = ref('')
const password = ref('')
const user=userStore();
const toast = ref({ show: false, message: '', type: 'success' });

const showToast = (message: string, type = 'success') => {
  toast.value.message = message;
  toast.value.type = type;
  toast.value.show = true;
  setTimeout(() => (toast.value.show = false), 5000);
}

// 登录处理
const handleLogin = async () => {
    // 防止XSS攻击
    const xssPattern = /(~|\{|\}|"|'|<|>|\?)/g
if (!username.value && !password.value) {
      showToast('请填写用户名和密码', 'error');
      return;
    } else if (xssPattern.test(username.value) || xssPattern.test(password.value)) {
        showToast('警告:输入内容包含非法字符', 'error');
        return
    }else {
        // 触发验证码弹窗，实际登录在 onSuccess 中执行
        onShow();
    }

}

 const isShow = ref(false);

  const onShow = () => {
    isShow.value = true;

  };

  const onClose = () => {
    isShow.value = false;
  };

  const onSuccess = () => {
    onClose(); // 验证成功，需要手动关闭模态框
    // 执行注册并反馈
        // 对输入进行转义处理
        const safeUsername = encodeURIComponent(username.value)
        const safePassword = encodeURIComponent(password.value)

        try {
            login(safeUsername,safePassword).then(async (res) => {
              console.log(res);
              if (res.code == "200" ) {
                  // 设置cookie
                  proxy.$cookies.set('Authorization', res.data.tokenValue);
                  // 更新用户状态
                  user.userId=res.data.loginId;
                  user.isFinited = true;
                      //获取完整用户信息（可选，如果你 store 里有更多字段）
                  const userRes = await axios({
                    url: `https://frp-six.com:11086/api/user?id=${user.userId}`,
                    method: 'GET',
                  });
                  const userData = userRes.data.data;
                  if (userData) {
                    user.userName = userData.username;
                    user.AvatarUrl = userData.avatarUrl;
                    user.userEmail = userData.email;
                  }
                  // 跳转到主页
                  alert('登录成功，欢迎用户：'+res.data.loginId);
                  router.push('/');
              } else{
                  showToast('登录失败，用户名或密码错误', 'error');
              }
        });
        } catch {
            showToast('用户不存在，登录失败', 'error');
            return;
        }
  };

// LOGIN API 调用
async function login(userName :string,pwd:string) {
  const res=await axios({
    url: 'https://frp-six.com:11086/api/login/doLogin?name='+userName+"&pwd="+pwd,
    method: 'GET',
  })
  return res.data;
}
// api无法调用时也没有反应
</script>
