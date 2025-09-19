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
                    <input id="username" v-model.trim="loginForm.username" type="text" autocomplete="off" @input="validateInput" required class="input-control" placeholder="请填写手机号、邮箱或用户名"/>
                    <span class="highlight"></span>
                </div>
                <div class="input-group">
                    <label for="password">密码</label>
                    <input id="password" v-model.trim="loginForm.password" type="password" autocomplete="off" @input="validateInput" required class="input-control" placeholder="请填写密码" />
                    <span class="highlight"></span>
                </div>
                <div class="error-message" v-if="errorMsg">{{ errorMsg }}</div>
                <button type="submit" class="submit-btn"  :disabled="!isFormValid">
                    <span>登录</span>
                    <i class="arrow-icon"></i>
                </button>
                <div class="form-footer">
                    <span>还没有账号？</span>
                    <RouterLink to="/register">立即注册</RouterLink>
                </div>
            </form>
        </section>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 表单数据
const loginForm = reactive({
    username: '',
    password: ''
})

const errorMsg = ref('')
const isFormValid = ref(false)

// 输入验证
const validateInput = () => {
    // 基本验证
    if (loginForm.username && loginForm.password) {
        isFormValid.value = true
        errorMsg.value = ''
    } else {
        isFormValid.value = false
    }
}

// 登录处理
const handleLogin = async () => {
    // 防止XSS攻击
    const xssPattern = /(~|\{|\}|"|'|<|>|\?)/g
    if (xssPattern.test(loginForm.username) || xssPattern.test(loginForm.password)) {
        errorMessage('警告:输入内容包含非法字符');
        return
    }

    try {
        // 对输入进行转义处理
        const safeUsername = encodeURIComponent(loginForm.username)
        const safePassword = encodeURIComponent(loginForm.password)

        // 实际的登录API调用
        console.log('登录请求:', { username: safeUsername, password: safePassword })

        // 模拟登录成功并设置cookie，设置过期时间为1小时
        const expires = new Date(Date.now() + 3600 * 1000).toUTCString()
        document.cookie = `authToken=yourAuthToken; path=/; expires=${expires}`

        // 跳转到主页
        router.push('/home')
    } catch (error) {
        errorMessage('登录失败，请稍后重试');
    }
}

// 错误提示
const errorMessage = (text) => {
    errorMsg.value = text
    setTimeout(() => {
        errorMsg.value = ''
    }, 3000)
}

onMounted(() => {
    validateInput()
})

</script>
