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
                <button type="submit" class="submit-btn" @click="onShow">
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
import { ref } from 'vue'
import Vcode from "vue3-puzzle-vcode";

const username = ref('')
const email = ref('')
const verificationCode = ref('')
const password = ref('')
const confirmPassword = ref('')

const handleRegister = () => {
    console.log({
        username: username.value,
        email: email.value,
        verificationCode: verificationCode.value,
        password: password.value,
        confirmPassword: confirmPassword.value
    })
}

const sendVerificationCode = () => {
    console.log('Sending verification code to:', email.value)
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
  };
</script>

