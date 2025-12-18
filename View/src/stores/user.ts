// src/stores/user.ts
import { defineStore } from 'pinia'

// 1. 定义状态接口
export interface UserState {
  userId: string
  userName: string
  userEmail: string
  AvatarUrl: string
  isFinited: boolean
}

// 2. 使用对象式 state（而非函数），并显式标注类型
export const userStore = defineStore('user', {
  // ✅ 启用持久化（v3 写法）
  persist: true, // 最简：整个 state 持久化到 localStorage


  // ✅ 使用对象字面量 + 断言，确保类型推导
  state: (): UserState => ({
    userId: '',
    userName: '',
    userEmail: '',
    AvatarUrl: '',
    isFinited: false
  }),

  getters: {
    getUserName(state): string {
      return state.userName || state.userId.toUpperCase()
    }
  },

  actions: {
    setUserId(value: string) {
      if (value?.trim()) {
        this.userId = value.trim()
      }
    },
    setUser(data: Partial<UserState>) {
      Object.assign(this, data)
      this.isFinited = true
    },
    clearUser() {
      this.$reset() // 👈 Pinia 内置方法，重置为初始 state
    }
  }
})
