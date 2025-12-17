<template>
  <div class="font-inter bg-neutral min-h-screen text-dark">

    <!-- 主内容区 -->
    <main class="container mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 页面标题 -->
      <div class="mb-8">
        <h1 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-dark">个人中心</h1>
        <p class="text-gray-500 mt-2">管理您的账户信息和投票活动</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
        <!-- 左侧：侧边导航 -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl card-shadow p-4 sticky top-20">
            <!-- 个人资料缩略 -->
            <div class="flex flex-col items-center py-4 border-b border-gray-100 mb-3">
              <div class="relative">
                <img :src="userInfo.avatar" alt="用户头像" class="w-24 h-24 rounded-full object-cover border-4 border-white shadow-sm">
                <button class="absolute bottom-0 right-0 w-8 h-8 bg-primary rounded-full flex items-center justify-center text-white text-xs hover:bg-primary/90 transition-colors" @click="handleAvatarUpload">
                  <i class="fa fa-camera"></i>
                </button>
              </div>
              <h3 class="mt-3 font-semibold">{{ userInfo.fullname }}</h3>
              <p class="text-sm text-gray-500 mt-1">{{ userInfo.email }}</p>
            </div>

            <!-- 导航菜单 -->
            <nav class="space-y-1">
              <a
                href="#"
                class="nav-item"
                :class="{ active: activeNav === 'profile' }"
                @click.prevent="activeNav = 'profile'"
              >
                <i class="fa fa-user-circle w-5 text-center mr-3"></i>
                <span>个人资料</span>
              </a>
              <a
                href="#"
                class="nav-item"
                :class="{ active: activeNav === 'myVotes' }"
                @click.prevent="activeNav = 'myVotes'"
              >
                <i class="fa fa-bar-chart w-5 text-center mr-3"></i>
                <span>我的投票</span>
                <span class="ml-auto bg-primary/10 text-primary text-xs px-2 py-0.5 rounded-full">{{ stats.createdVotes }}</span>
              </a>
              <a
                href="#"
                class="nav-item"
                :class="{ active: activeNav === 'favorites' }"
                @click.prevent="activeNav = 'favorites'"
              >
                <i class="fa fa-star-o w-5 text-center mr-3"></i>
                <span>收藏的投票</span>
                <span class="ml-auto bg-primary/10 text-primary text-xs px-2 py-0.5 rounded-full">3</span>
              </a>
              <a
                href="#"
                class="nav-item"
                :class="{ active: activeNav === 'participations' }"
                @click.prevent="activeNav = 'participations'"
              >
                <i class="fa fa-history w-5 text-center mr-3"></i>
                <span>参与记录</span>
              </a>
              <a
                href="#"
                class="nav-item"
                :class="{ active: activeNav === 'settings' }"
                @click.prevent="activeNav = 'settings'"
              >
                <i class="fa fa-cog w-5 text-center mr-3"></i>
                <span>账户设置</span>
              </a>
              <a href="#" class="nav-item text-danger hover:bg-danger/5 hover:text-danger" @click.prevent="handleLogout">
                <i class="fa fa-sign-out w-5 text-center mr-3"></i>
                <span>退出登录</span>
              </a>
            </nav>
          </div>
        </div>

        <!-- 右侧：主内容区域 -->
        <div class="lg:col-span-3 space-y-6">
          <!-- 统计卡片 -->
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            <div class="stat-card">
              <div class="flex items-start justify-between">
                <div>
                  <p class="text-gray-500 text-sm">创建的投票</p>
                  <h3 class="text-2xl font-bold mt-1">{{ stats.createdVotes }}</h3>
                  <p class="text-success text-xs mt-2 flex items-center">
                    <i class="fa fa-arrow-up mr-1"></i> 较上月增长 {{ stats.createdVotesGrowth }} 个
                  </p>
                </div>
                <div class="w-10 h-10 rounded-lg bg-primary/10 flex items-center justify-center text-primary">
                  <i class="fa fa-pencil"></i>
                </div>
              </div>
            </div>

            <div class="stat-card">
              <div class="flex items-start justify-between">
                <div>
                  <p class="text-gray-500 text-sm">参与的投票</p>
                  <h3 class="text-2xl font-bold mt-1">{{ stats.participatedVotes }}</h3>
                  <p class="text-success text-xs mt-2 flex items-center">
                    <i class="fa fa-arrow-up mr-1"></i> 较上月增长 {{ stats.participatedVotesGrowth }} 个
                  </p>
                </div>
                <div class="w-10 h-10 rounded-lg bg-secondary/10 flex items-center justify-center text-secondary">
                  <i class="fa fa-check-circle"></i>
                </div>
              </div>
            </div>

            <div class="stat-card">
              <div class="flex items-start justify-between">
                <div>
                  <p class="text-gray-500 text-sm">总参与人数</p>
                  <h3 class="text-2xl font-bold mt-1">{{ stats.totalParticipants }}</h3>
                  <p class="text-success text-xs mt-2 flex items-center">
                    <i class="fa fa-arrow-up mr-1"></i> 较上月增长 {{ stats.totalParticipantsGrowth }} 人
                  </p>
                </div>
                <div class="w-10 h-10 rounded-lg bg-warning/10 flex items-center justify-center text-warning">
                  <i class="fa fa-users"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- 快速创建投票 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <h2 class="text-lg font-semibold mb-2 flex items-center">
              <i class="fa fa-plus-circle text-primary mr-2"></i>
              快速创建投票
            </h2>
            <p class="text-sm text-gray-500">在个人中心快速开始一个新的投票，或打开完整的创建页面进行高级设置。</p>
            <div class="mt-4 flex items-center">
              <button @click="goToNewPoll" class="py-2 px-4 bg-primary text-white rounded-lg shadow-md hover:bg-primary/90 transition-colors">
                <i class="fa fa-pencil mr-2"></i> 创建投票
              </button>
              <RouterLink to="/newpoll" class="ml-4 text-primary underline">打开完整创建页</RouterLink>
            </div>
          </div>

          <!-- 个人资料编辑 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift" v-if="activeNav === 'profile'">
            <h2 class="text-lg font-semibold mb-6 flex items-center">
              <i class="fa fa-user-circle text-primary mr-2"></i>
              个人资料
            </h2>

            <form class="space-y-6">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label for="fullname" class="block text-sm font-medium text-gray-700 mb-1">姓名</label>
                  <input
                    type="text"
                    id="fullname"
                    v-model="userInfo.fullname"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  >
                </div>

                <div>
                  <label for="username" class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
                  <input
                    type="text"
                    id="username"
                    v-model="userInfo.username"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  >
                  <p class="text-xs text-gray-500 mt-1">用户名用于显示和他人识别</p>
                </div>

                <div>
                  <label for="email" class="block text-sm font-medium text-gray-700 mb-1">电子邮箱</label>
                  <input
                    type="email"
                    id="email"
                    v-model="userInfo.email"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none bg-gray-50"
                    disabled
                  >
                  <p class="text-xs text-gray-500 mt-1">邮箱用于登录和接收通知</p>
                </div>

                <div>
                  <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">手机号码（可选）</label>
                  <input
                    type="tel"
                    id="phone"
                    v-model="userInfo.phone"
                    placeholder="请输入手机号码"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  >
                </div>
              </div>

              <div>
                <label for="bio" class="block text-sm font-medium text-gray-700 mb-1">个人简介（可选）</label>
                <textarea
                  id="bio"
                  rows="3"
                  v-model="userInfo.bio"
                  placeholder="简单介绍一下自己..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none resize-none"
                ></textarea>
                <p class="text-xs text-gray-500 mt-1">最多可输入200个字符</p>
              </div>

              <div class="flex justify-end space-x-3 pt-4 border-t border-gray-100">
                <button
                  type="button"
                  class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors"
                  @click="resetForm"
                >
                  取消
                </button>
                <button
                  type="button"
                  class="px-4 py-2 bg-primary text-white rounded-lg font-medium hover:bg-primary/90 transition-colors"
                  @click="saveProfile"
                >
                  保存修改
                </button>
              </div>
            </form>
          </div>

          <!-- 最近活动 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-lg font-semibold flex items-center">
                <i class="fa fa-history text-primary mr-2"></i>
                最近活动
              </h2>
              <a href="#" class="text-primary hover:text-primary/80 text-sm font-medium transition-colors">
                查看全部
              </a>
            </div>

            <div class="space-y-5">
              <div class="flex" v-for="(activity, index) in recentActivities" :key="index">
                <div class="shrink-0 w-10 h-10 rounded-full" :class="activity.bgClass">
                  <div class="w-full h-full flex items-center justify-center" :class="activity.textClass">
                    <i :class="activity.icon"></i>
                  </div>
                </div>
                <div class="ml-4 flex-1">
                  <p class="text-sm">
                    <span class="font-medium">{{ activity.action }}</span>
                    <span class="text-gray-600 ml-1">「{{ activity.target }}」</span>
                  </p>
                  <p class="text-xs text-gray-500 mt-1">{{ activity.time }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 最近创建的投票 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-lg font-semibold flex items-center">
                <i class="fa fa-bar-chart text-primary mr-2"></i>
                最近创建的投票
              </h2>
              <a href="#" class="text-primary hover:text-primary/80 text-sm font-medium transition-colors">
                查看全部
              </a>
            </div>

            <div class="space-y-4">
              <div
                class="border border-gray-100 rounded-lg p-4 hover:border-primary/30 transition-colors"
                v-for="(vote, index) in recentVotes"
                :key="index"
              >
                <div class="flex justify-between items-start">
                  <div>
                    <h3 class="font-medium">{{ vote.title }}</h3>
                    <p class="text-sm text-gray-500 mt-1">创建于 {{ vote.createdAt }}</p>
                  </div>
                  <span class="px-2 py-1" :class="vote.statusClass">{{ vote.status }}</span>
                </div>

                <div class="mt-3 flex items-center justify-between">
                  <div class="flex items-center space-x-4">
                    <span class="text-sm flex items-center">
                      <i class="fa fa-user-o mr-1 text-gray-400"></i>
                      <span>{{ vote.participants }}人参与</span>
                    </span>
                    <span class="text-sm flex items-center">
                      <i class="fa fa-list-ul mr-1 text-gray-400"></i>
                      <span>{{ vote.options }}个选项</span>
                    </span>
                  </div>

                  <div class="flex space-x-2">
                    <button
                      class="text-sm text-gray-600 hover:text-primary transition-colors"
                      v-if="vote.status === '进行中'"
                      @click="handleEditVote(vote.id)"
                    >
                      <i class="fa fa-pencil mr-1"></i> 编辑
                    </button>
                    <button
                      class="text-sm text-gray-600 hover:text-primary transition-colors"
                      v-if="vote.status === '已结束'"
                      @click="handleViewResults(vote.id)"
                    >
                      <i class="fa fa-bar-chart mr-1"></i> 结果
                    </button>
                    <button
                      class="text-sm text-gray-600 hover:text-primary transition-colors"
                      @click="handleViewVote(vote.id)"
                    >
                      <i class="fa fa-eye mr-1"></i> 查看
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>



    <!-- 保存成功提示 -->
    <div
      id="success-toast"
      class="fixed bottom-6 right-6 bg-success text-white px-4 py-3 rounded-lg shadow-lg transform translate-y-20 opacity-0 transition-all duration-300 flex items-center z-50"
      :class="{ 'translate-y-0 opacity-100': showSuccessToast }"
    >
      <i class="fa fa-check-circle mr-2"></i>
      <span>资料保存成功</span>
    </div>
  </div>
</template>

<script lang="ts">
interface UserInfo {
  avatar: string;
  fullname: string;
  username: string;
  email: string;
  phone: string;
  bio: string;
}
interface Activity {
  action: string;
  target?: string;
  time?: string;
  icon?: string;
  bgClass?: string;
  textClass?: string;
}
interface VoteItem {
  id: number;
  title: string;
  createdAt: string;
  status: string;
  statusClass?: string;
  participants?: number;
  options?: number;
}

import { userStore } from '@/stores/user';
export default {
  name: 'PersonalPage',
  data() {
    return {
      // 用户信息（已清空）
      userInfo: {
        avatar: '',
        fullname: '',
        username: '',
        email: '',
        phone: '',
        bio: ''
      },
  // 原始用户信息，用于重置表单
  originalUserInfo: {} as unknown as UserInfo,

      // 统计数据（已清零）
      stats: {
        createdVotes: 0,
        createdVotesGrowth: 0,
        participatedVotes: 0,
        participatedVotesGrowth: 0,
        totalParticipants: 0,
        totalParticipantsGrowth: 0
      },

  // 最近活动（已清空）
  recentActivities: [] as Activity[],

  // 最近创建的投票（已清空）
  recentVotes: [] as VoteItem[],

      // 导航状态
      activeNav: 'profile',

      // 滚动状态
      isScrolled: false,

      // 成功提示显示状态
      showSuccessToast: false
    };
  },
  mounted() {
    // 保存原始用户信息
    this.originalUserInfo = { ...this.userInfo };

    // 从 Pinia userStore 同步用户信息（若有）
    try {
      const store = userStore();
      if (store && store.username) {
        // 将 store 中的 username 用作 fullname 和 username 的回填
        this.userInfo.fullname = store.username;
        this.userInfo.username = store.username;
      }
    } catch {
      // ignore if Pinia not initialized
    }

    // 监听滚动事件
    window.addEventListener('scroll', this.handleScroll);
  },

  beforeUnmount() {
    // 移除滚动监听
    window.removeEventListener('scroll', this.handleScroll);
  },

  methods: {
    // 处理滚动事件
    handleScroll() {
      this.isScrolled = window.scrollY > 10;
    },

    // 保存个人资料
    saveProfile() {
      // 模拟API请求
      console.log('保存用户信息:', this.userInfo);

      // 显示成功提示
      this.showSuccessToast = true;

      // 3秒后隐藏提示
      setTimeout(() => {
        this.showSuccessToast = false;
      }, 3000);

      // 更新原始数据
      this.originalUserInfo = { ...this.userInfo };
    },

    // 重置表单
    resetForm() {
      this.userInfo = { ...this.originalUserInfo };
    },

    // 处理头像上传
    handleAvatarUpload() {
      // 实际项目中这里会触发文件选择对话框
      alert('头像上传功能待实现');
    },

    // 处理退出登录
    handleLogout() {
      if (confirm('确定要退出登录吗？')) {
        // 实际项目中这里会调用退出登录API
        console.log('用户退出登录');
      }
    },

    // 处理编辑投票
  handleEditVote(voteId: number) {
      console.log('编辑投票:', voteId);
      // 实际项目中会跳转到编辑页面
    },

    // 处理查看投票结果
  handleViewResults(voteId: number) {
      console.log('查看投票结果:', voteId);
      // 实际项目中会跳转到结果页面
    },

    // 处理查看投票
  handleViewVote(voteId: number) {
      console.log('查看投票:', voteId);
      // 实际项目中会跳转到详情页面
    }
    ,
    // 跳转到创建投票页面
    goToNewPoll() {
      // 使用 router 的命名路由跳转
      // 尝试使用 Vue Router 的实例导航（Options API 中通过 this.$router 访问）
      type RouterLike = { push: (arg: { name?: string; path?: string; query?: Record<string,string> }) => Promise<unknown> };
      const router = (this as unknown as { $router?: RouterLike }).$router;
      if (router && typeof router.push === 'function') {
        router.push({ name: 'newpoll' }).catch(() => {});
        return;
      }
      // 回退：直接改变 location
      window.location.href = '/newpoll';
    }
  }
};
</script>

<style scoped lang="postcss">
@layer utilities {
  .content-auto {
    content-visibility: auto;
  }
  .card-shadow {
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  }
  .hover-lift {
    transition: transform 0.2s ease, box-shadow 0.2s ease;
  }
  .hover-lift:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 24px rgba(0, 0, 0, 0.12);
  }
  .stat-card {
    @apply bg-white rounded-xl p-5 card-shadow hover-lift transition-all;
  }
  .nav-item {
    @apply flex items-center px-4 py-3 text-gray-600 hover:bg-primary/5 hover:text-primary rounded-lg transition-colors;
  }
  .nav-item.active {
    @apply bg-primary/10 text-primary font-medium;
  }
}

/* 引入外部资源的样式不会被scoped影响，保持原样 */
</style>
