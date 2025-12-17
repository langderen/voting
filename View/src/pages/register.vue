<template>
    <div class="container">
        <section class="floating">
        <div class="section-title">
                <h2>创建账号</h2>
                <p>开始成为投票管理者</p>
            </div>
            <form @submit.prevent="handleRegister" class="floating-form">
                <div class="input-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" v-model="username" required maxlength="20" class="input-control" placeholder="请填写用户名"/>
                    <span class="highlight"></span>
                </div>
                <div class="input-group">
                  <label for="email">邮箱地址</label>
                    <input type="email" id="email" v-model="email" required class="input-control" placeholder="请填写邮箱"/>
                    <span class="highlight"></span>
                </div>
                <div class="input-group">
                      <label for="password">密码</label>
                    <input type="password" id="password" v-model="password" required minlength="6" maxlength="20" class="input-control" placeholder="请填写您的密码"/>
                    <span class="highlight"></span>
                </div>
                <div class="input-group">
                    <label for="confirmPassword">确认密码</label>
                    <input type="password" id="confirmPassword" v-model="confirmPassword" required minlength="6" maxlength="20" class="input-control" placeholder="请确认您的密码"/>
                    <span class="highlight"></span>
                </div>
                <!-- 提示框 -->
      <div v-if="toast.show" :class="['toast', toast.type === 'success' ? 'toast-success' : 'toast-error']">{{ toast.message }}</div>
                <button type="submit" class="submit-btn" >
                    <span>立即注册</span>
                    <Vcode :show="isShow" @success="onSuccess" @close="onClose" />
                    <i class="arrow-icon"></i>
                </button>

                <div class="form-footer">
                    <span>已有账号？</span>
                    <RouterLink to="/login">立即登录</RouterLink>
                </div>
            </form>
        </section>
    </div>

</template>

<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue'
import { useRouter } from 'vue-router'
// 验证码组件，爆红也没事
import Vcode from "vue3-puzzle-vcode";

const username = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')

const router = useRouter();

const toast = ref({ show: false, message: '', type: 'success' });

const showToast = (message: string, type = 'success') => {
  toast.value.message = message;
  toast.value.type = type;
  toast.value.show = true;
  setTimeout(() => (toast.value.show = false), 5000);
}

const handleRegister = () => {
    // 本地简单校验
    const xssPattern = /(~|\{|\}|"|'|<|>|\?)/g
    if (!username.value || !email.value || !password.value || !confirmPassword.value) {
      showToast('请填写完整的注册信息', 'error');
      return;
    }else if (xssPattern.test(username.value) || xssPattern.test(email.value) || xssPattern.test(password.value) || xssPattern.test(confirmPassword.value)) {
        showToast('警告:输入内容包含非法字符', 'error');
        return
    }else if (password.value !== confirmPassword.value) {
      showToast('两次输入的密码不一致', 'error');
      return;
    }else{
      // 触发验证码弹窗，实际注册在 onSuccess 中执行
      onShow();
    }

}


// 验证码脚本


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
    (async () => {
      try {
        const data = await addUser();
        if (data && (data.code === 0 || data.success || data.id || data.userId)) {
          showToast('注册成功，正在跳转到登录页', 'success');
          setTimeout(() => router.push('/login'), 1200);
        } else {
          // 允许后端返回消息字段
          const msg = data && (data.message || data.msg) ? (data.message || data.msg) : '注册失败，请重试';
          showToast(msg, 'error');
        }
      } catch (err: any) {
        const msg = err && err.response && err.response.data && err.response.data.message ? err.response.data.message : '注册失败,用户名或邮箱可能已被使用';
        showToast(msg, 'error');
      }
    })();
  };
  async function addUser() {
      const res=await axios({
      url: 'https://frp-six.com:11086/api/user',
      method: 'POST',
      data: {
        username: username.value,
        email: email.value,
        passwordHash: password.value,
        createAt: new Date().toISOString(),
      },
  })
  return res.data;
}
</script>

