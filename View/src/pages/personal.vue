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
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift" v-if="activeNav === 'profile'">
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
                <div class="flex-shrink-0 w-10 h-10 rounded-full" :class="activity.bgClass">
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

          <!-- 我的投票（独立视图） -->
          <div v-if="activeNav === 'myVotes'" class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <div class="flex justify-between items-center mb-4">
              <h2 class="text-lg font-semibold flex items-center">
                <i class="fa fa-list text-primary mr-2"></i>
                我的投票
              </h2>
              <div class="flex items-center space-x-3">
                <input v-model="voteQuery" type="search" placeholder="搜索投票标题" class="px-3 py-1 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-primary" />
                <span class="text-sm text-gray-500">共 {{ filteredVotes.length }} 项</span>
              </div>
            </div>

            <div v-if="filteredVotes.length === 0" class="text-center py-12 text-gray-500">
              暂无匹配的投票，尝试更改搜索关键词或创建一个新的投票。
              <div class="mt-4">
                <RouterLink to="/newpoll" class="px-4 py-2 bg-primary text-white rounded-lg">快速创建投票</RouterLink>
              </div>
            </div>

            <div class="space-y-4" v-else>
              <div
                class="border border-gray-100 rounded-lg p-4 hover:border-primary/30 transition-colors"
                v-for="vote  in pagedVotes"
                :key="vote.id"
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

              <!-- 简单分页 -->
              <div class="flex justify-end items-center space-x-3 pt-2">
                <button class="px-3 py-1 border rounded" :disabled="votePage === 1" @click="votePage = Math.max(1, votePage - 1)">上一页</button>
                <span class="text-sm text-gray-500">第 {{ votePage }} 页 / 共 {{ totalPages }} 页</span>
                <button class="px-3 py-1 border rounded" :disabled="votePage >= totalPages" @click="votePage = Math.min(totalPages, votePage + 1)">下一页</button>
              </div>
            </div>
          </div>

          <!-- 最近创建的投票 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-lg font-semibold flex items-center">
                <i class="fa fa-bar-chart text-primary mr-2"></i>
                我创建的投票
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
<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { userStore } from '@/stores/user';

// ====== 类型定义 ======
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

// ====== 响应式状态 ======
const userInfo = ref<UserInfo>({
  avatar: '',
  fullname: '',
  username: '',
  email: '',
  phone: '',
  bio: ''
});

const originalUserInfo = ref<UserInfo>({ ...userInfo.value });

const stats = ref({
  createdVotes: 0,
  createdVotesGrowth: 0,
  participatedVotes: 0,
  participatedVotesGrowth: 0,
  totalParticipants: 0,
  totalParticipantsGrowth: 0
});

const recentActivities = ref<Activity[]>([]);
const recentVotes = ref<VoteItem[]>([]);

const voteQuery = ref('');
const votePage = ref(1);
const votePageSize = ref(6);

const activeNav = ref('profile');
const isScrolled = ref(false);
const showSuccessToast = ref(false);

// ====== 计算属性 ======
const filteredVotes = computed(() => {
  const q = voteQuery.value.trim().toLowerCase();
  if (!q) return recentVotes.value;
  return recentVotes.value.filter(v => v.title.toLowerCase().includes(q));
});

const totalPages = computed(() => {
  return Math.max(1, Math.ceil(filteredVotes.value.length / votePageSize.value));
});

const pagedVotes = computed(() => {
  const start = (votePage.value - 1) * votePageSize.value;
  return filteredVotes.value.slice(start, start + votePageSize.value);
});

// ====== 方法 ======
const handleScroll = () => {
  isScrolled.value = window.scrollY > 10;
};

const saveProfile = () => {
  console.log('保存用户信息:', userInfo.value);
  showSuccessToast.value = true;
  setTimeout(() => {
    showSuccessToast.value = false;
  }, 3000);
  originalUserInfo.value = { ...userInfo.value };

};

const resetForm = () => {
  userInfo.value = { ...originalUserInfo.value };
};

const handleAvatarUpload = () => {
  alert('头像上传功能待实现');
};


const handleEditVote = (voteId: number) => {
  console.log('编辑投票:', voteId);
};

const handleViewResults = (voteId: number) => {
  console.log('查看投票结果:', voteId);
};

const handleViewVote = (voteId: number) => {
  console.log('查看投票:', voteId);
};

// ====== 生命周期 ======
onMounted(() => {
  const user = userStore();
  console.log('加载用户数据:', user.userName);
  // 初始化用户信息
  userInfo.value = {
    avatar: user.AvatarUrl || '',
    fullname: user.userName || user.userId || '',
    username: user.userId || '',
    email: user.userEmail || '',
    phone:'',
    bio:''
  };
  originalUserInfo.value = { ...userInfo.value };

  // 监听滚动
  window.addEventListener('scroll', handleScroll);
});

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style scoped lang="postcss">
@import '../assets/index.css';

</style>
