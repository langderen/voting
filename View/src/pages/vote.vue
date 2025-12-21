<template>
  <el-container>
    <el-main>
      <section class="assignments">
        <div class="max-w-4xl mx-auto px-4 py-8">
          <div class="bg-white rounded-2xl shadow-xl p-8 fade-in">

            <!-- 页面标题区域 -->
            <div class="text-center mb-8">
              <div class="section-title">
                <h1 class="text-4xl font-bold mb-4 gradient-title">{{ title }}</h1>
                <h2 class="text-xl text-gray-600 mb-2">{{ description }}</h2>
                <div>
                  <span class="text-blue-700">项目编号：{{ queryId }}</span>
                </div>
              </div>

              <!-- 投票状态指示器 -->
              <div
                id="voteStatus"
                class="bg-green-50 border border-green-200 rounded-lg p-4 mb-6 flex items-center justify-between"
                :class="voteStatusClass"
              >
                <div class="flex items-center">
                  <i :class="voteStatusIcon" class="text-xl mr-3"></i>
                  <span class="text-green-700 font-medium">{{ voteStatusText }}</span>
                </div>
                <div class="text-sm text-gray-500">投票截止：{{ voteEndTime }}</div>
              </div>
            </div>

            <!-- 候选人选择区域 -->
            <div class="mb-8">
              <h2 class="text-2xl font-semibold text-gray-800 mb-6 text-center">请选择候选人</h2>
              <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 md:gap-10">
                <!-- 候选人卡片 - 使用v-for循环渲染 -->
                <div
                  v-for="candidate in candidates"
                  :key=" candidate.optionId"
                  class="vote-card bg-white border-2 rounded-xl p-6 text-center cursor-pointer transition-all duration-300 hover:shadow-lg relative overflow-hidden"
                  :class="{'border-blue-500 bg-blue-50 transform scale-105': selectedCandidate === (candidate.id || candidate.optionId), 'border-gray-200': selectedCandidate !== (candidate.id || candidate.optionId)}"
                  @click="selectCandidate(candidate)"
                >
                  <!-- 已选徽章 -->
                  <div v-if="selectedCandidate === (candidate.id || candidate.optionId)" class="absolute top-3 right-3 bg-blue-600 text-white text-xs px-2 py-1 rounded-full shadow">已选</div>

                  <!-- 图片头像，带渐变边框环（使用 CSS 实现） -->
                  <div class="avatar-size mx-auto mb-2 rounded-full flex items-center justify-center avatar-hover" @mousemove="handleAvatarMove" @mouseleave="handleAvatarLeave">
                    <div class="avatar-ring w-full h-full flex items-center justify-center">
                      <img :src="candidate.imageUrl || '/user.png'"  class="avatar-img w-full h-full object-cover" />
                    </div>
                  </div>

                  <h3 class="text-xl font-bold text-gray-800 mb-2">{{ candidate.optionText }}</h3>
                  <p class="text-gray-600 mb-3 text-sm">{{ candidate.desc }}</p>
                  <div class="flex justify-center items-center space-x-3 text-sm text-gray-500">
                    <span class="text-sm text-gray-600">{{ candidate.count || candidate.votes || 0 }} 票</span>
                  </div>
                  <!-- hover overlay -->
                  <div class="card-hover absolute inset-0 flex items-center justify-center opacity-0 transition-opacity duration-300 pointer-events-none">
                    <div class="hover-inner text-center pointer-events-auto">
                      <button class="px-3 py-1 bg-white bg-opacity-20 text-white rounded">查看详情</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 投票按钮 -->
            <div class="text-center mb-8">
              <button
                @click="submitVote"
                class="photo-button w-64 h-16 rounded-xl font-semibold text-lg transition-all duration-300 transform hover:scale-105 disabled:opacity-50 disabled:cursor-not-allowed bg-cover bg-center relative overflow-hidden"
                :disabled="!selectedCandidate || hasVoted || isSubmitting"
              >
                <div class="button-content flex items-center justify-center text-white">
                  <i class="fas fa-paper-plane mr-3"></i>
                  <span>{{ submitButtonText }}</span>
                </div>
                <div v-if="isSubmitting" class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-25">
                  <span class="spinner" aria-hidden="true"></span>
                </div>
              </button>
            </div>

            <!-- 二维码+饼图区域 -->
            <div class=" grid grid-cols-1 sm:grid-cols-2 gap-4 sm:gap-6 md:gap-8">
              <!-- 二维码区域 -->
              <div class="qr-section">
                <h3 class="stats-title">网页直达二维码</h3>
                <div class="flex justify-center sm:justify-start">
                  <img
                    :src="pageUrl"
                    alt="网页直达二维码"
                    class="qr-image w-24 h-24 sm:w-32 sm:h-32 md:w-40 md:h-40 object-contain"
                  />
                </div>
              </div>

              <!-- 饼状图区域（修复：手机高度更小） -->
              <div class="chart-section">
                <h3 class="stats-title">投票统计饼状图</h3>
                 <div
                    ref="mychartRef"
                    class="echart"
                    :style="{ height: '200px', width: '100%' }"
                  ></div>
              </div>
            </div>

            <!-- 投票统计区域 -->
            <div class="mt-8 bg-gray-50 rounded-xl p-6">
              <h3 class="text-xl font-bold text-gray-800 mb-4 text-center">投票统计</h3>
              <div class="space-y-4">
                <div v-for="candidate in candidates" :key="`stat-${candidate.optionId}`" class="flex justify-between items-center">
                  <!-- 关键修复：固定宽度 + 防止换行 -->
                  <span class="text-gray-700 w-40 overflow-hidden text-ellipsis whitespace-nowrap" style="min-width: 4rem">{{ getPrefix(candidate.optionText) }}</span>

                  <div class="w-40 bg-gray-200 rounded-full h-2 overflow-hidden mx-4">
                    <div
                      :class="['h-2 rounded-full transition-all duration-1000', 'bg-blue-500']"
                      :style="{ width: percentWidth(candidate), transition: 'width 1s ease' }"
                    ></div>
                  </div>

                  <div class="flex items-center space-x-2 w-13" style="min-width: 1rem">
                    <span class="text-gray-700 text-sm">{{ percentDisplay(candidate) }}</span>
                    <span class="text-gray-500 text-sm">({{ candidate.count }})</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 成功模态 -->
            <transition name="fade">
              <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
                <div class="bg-white rounded-xl p-6 w-96 text-center shadow-2xl">
                  <div class="text-4xl text-green-500 mb-3"><i class="fas fa-check-circle"></i></div>
                  <h4 class="text-xl font-bold mb-2">投票成功</h4>
                  <p class="text-gray-600 mb-4">感谢您的参与，您的选择已记录。</p>
                  <div class="flex justify-center">
                    <button @click="closeModal" class="px-4 py-2 bg-blue-600 text-white rounded-lg">知道了</button>
                  </div>
                </div>
              </div>
            </transition>

            <!-- 评论区 -->
            <div class="mt-8 bg-white rounded-xl p-6 comments-section">
              <h3 class="text-xl font-bold text-gray-800 mb-4 text-center">发表评论</h3>
              <div class="mb-4">
                <textarea v-model="newComment" placeholder="写下你的评论..." class="w-full p-3 border rounded resize-none" rows="4" :disabled="isPosting"></textarea>
              </div>
              <div class="flex items-center justify-between">
                <div class="text-sm text-gray-500">支持文明交流，尊重他人。</div>
                <div>
                  <button @click="postComment" :disabled="!newComment || isPosting" class="submit-btn">{{ isPosting ? '发布中...' : '发布评论' }}</button>
                </div>
              </div>

              <div class="mt-6">
                <h4 class="text-lg font-semibold mb-3">最新评论</h4>
                <div v-if="comments.length === 0" class="text-gray-500">还没有评论，快来抢沙发！</div>
                <div v-for="c in comments" :key="c.id || c._id || c.createdAt" class="border-b py-3">
                  <div class="flex items-start space-x-3">
                    <div class="w-10 h-10 bg-gray-200 rounded-full flex items-center justify-center text-sm text-gray-600">{{ (String(c.userId || "访客")).slice(0, 4) }}</div>
                    <div class="flex-1">
                      <div class="text-sm text-gray-700">{{ c.content }}</div>
                      <div class="text-xs text-gray-400 mt-1">{{ formatDate(c.createdAt) }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>
    </el-main>
  </el-container>

  <!-- 简单 toast 提示 -->
  <div v-if="toast.show" class="fixed right-6 bottom-6 z-50 px-4 py-2 rounded shadow-lg bg-black bg-opacity-80 text-white">{{ toast.message }}</div>
</template>

<script setup lang="ts">
import { reactive, toRef, watchEffect} from 'vue';
import { ref, onMounted, onUnmounted, watch } from 'vue';
import * as echarts from 'echarts/core';
import { PieChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  LegendComponent
} from 'echarts/components';
import { LabelLayout, UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';

// 注册必须的组件
echarts.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  LegendComponent,
  PieChart,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer
]);

interface Candidate {
  optionId: number;
  optionText: string;
  count: number;
  votes?: number;
  desc?: string;
  imageUrl?: string;
}

const candidates = reactive<Candidate[]>([]);

const mychartRef = ref<HTMLElement | null>(null);
let myChart: echarts.ECharts | null = null;

// 获取颜色的方法
const getColorFromClassName = (optionId: number): string => {
  const colors = [
    '#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de',
    '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc', '#546570'
  ];

  const index = Math.abs(optionId) % colors.length;
  return colors[index];
};

// 获取选项文本中"-"前的数据
const getPrefix = (optionText: string): string => {
  if (!optionText) return '';
  const parts = optionText.split('-'); // 用"-"分割字符串
  return parts[0].trim(); // 取第一个部分 + 去掉空格
};

const initChart = () => {
  if (!mychartRef.value) return;

  // 初始化图表实例
  myChart = echarts.init(mychartRef.value);

  // 过滤掉票数为0的候选人
  const validCandidates = candidates.filter(candidate => (candidate.count || candidate.votes || 0) > 0);

  // 如果没有有效候选人，显示提示
  if (validCandidates.length === 0) {
    const option = {
      title: {
        text: '暂无投票数据',
        left: 'center',
        top: 'center',
        textStyle: {
          fontSize: 16,
          fontWeight: 'normal'
        }
      },
      series: [{
        type: 'pie',
        radius: ['0%', '0%'],
        data: []
      }]
    };
    myChart.setOption(option);
    return;
  }

  // 构建饼图数据
  const pieData = validCandidates.map(candidate => ({
    value: candidate.count || candidate.votes || 0,
    name: getPrefix(candidate.optionText) || `选项${candidate.optionId}`,
    itemStyle: {
      color: getColorFromClassName(candidate.optionId || 0)
    }
  }));

  const totalVotes = validCandidates.reduce((sum, candidate) => sum + (candidate.count || candidate.votes || 0), 0);

  // 配置项
  const option = {
    title: {
      text: "总票数: " + totalVotes + "票",
      top: "0%",
      left: "center",
      textStyle: {
        fontSize: 13
      },
      subtextStyle: {
        fontSize: 12
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c}票 ({d}%)'
    },
    series: [
      {
        name: '个人统计',
        type: 'pie',
        radius: ['30%', '60%'],
        center: ['50%', '45%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 8,
          borderColor: '#fff',
          borderWidth: 2
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        data: pieData
      }
    ]
  };

  // 设置配置项
  myChart.setOption(option);
};

// 监听候选人数据变化
watch(
  () => candidates,
  () => {
    if (myChart) {
      myChart.clear(); // 清空现有图表
      initChart(); // 重新初始化
    }
  },
  { deep: true }
);

onMounted(() => {
  initChart();
  loadComments();
  // 添加窗口大小变化监听器
  window.addEventListener('resize', () => {
    if (myChart) {
      myChart.resize();
    }
  });
});

onUnmounted(() => {

  if (myChart) {
    myChart.dispose(); // 销毁图表实例
  }

  // 移除事件监听器
  window.removeEventListener('resize', () => {});
});

defineOptions({ name: 'VotePage' });

import { useRoute } from 'vue-router';
//路由传参
const route=useRoute();

//传参解析
const query=toRef(route,'query');
// 响应式状态
const selectedCandidate = ref<number | null>(null);
const toast = ref({ show: false, message: '', type: 'info' });
const hasVoted = ref<boolean>(false);
const submitButtonText = ref<string>('提交投票');
const queryId = toRef(query.value,'id');// 可以从路由参数获取
const pageUrl = `https://api.2dcode.biz/v1/create-qr-code?data=${encodeURIComponent(window.location.href)}`;
// 投票状态
const voteStatusText = ref<string>('您尚未投票，请选择您支持的候选人');
const voteStatusIcon = ref<string>('fas fa-check-circle text-green-500');
const voteStatusClass = ref<string>('');
const showModal = ref<boolean>(false);
const isSubmitting = ref<boolean>(false);
// 候选人数据
import axios from 'axios';
import { userStore } from '@/stores/user';

const poll=reactive<any>({});
const title=ref<string>('');
const description=ref<string>('');
const voteEndTime=ref<string>('');
getpoll();
console.log(poll);

// 向给定pollid的选项查询发起请求
async function getOptions() {
  const res=await axios({
    url: 'https://frp-six.com:11086/api/options?pollId=' + queryId.value,
    method: 'GET',
  })
  return res.data.data;
}
console.log(candidates);

// 向给定pollid的选项查询发起请求
async function getpoll() {
  const res=await axios({
    url: 'https://frp-six.com:11086/api/poll/poll?pollId=' + queryId.value,
    method: 'GET',
  })
  title.value=res.data.data["0"].title;
  description.value=res.data.data["0"].description;
  voteEndTime.value=res.data.data["0"].endTime;
  return res.data.data;
}

getOptions().then((res)=>{
  candidates.splice(0,candidates.length,...res);//清空并替换内容
});

// 方法
const selectCandidate = (candidate: any) => {
  if (hasVoted.value) return;
  const id = candidate.id || candidate.optionId;
  selectedCandidate.value = id;
  // 短时提示用户已选择
  toast.value.message = `已选择：${getPrefix(candidate.optionText) || candidate.optionText || '候选人'}`;
  toast.value.type = 'info';
  toast.value.show = true;
  setTimeout(() => (toast.value.show = false), 1400);
};

// 计算并保证百分比显示总和为 100%（显示整数百分比），进度条使用保留一位小数的精确比例
const percentages = ref<Record<string, number>>({}); // 整数百分比，用于文本显示，保证总和为100
const rawPercentages = ref<Record<string, number>>({}); // 精确百分比（浮点），用于进度条宽度

watchEffect(() => {
  const items = candidates.map((c, idx) => {
    const key = String(c.id ?? c.optionId ?? idx);
    const count = Number(c.count ?? c.votes ?? 0) || 0;
    return { key, count };
  });

  const total = items.reduce((s, it) => s + it.count, 0);
  if (total === 0) {
    // 全部为 0
    items.forEach(it => {
      percentages.value[it.key] = 0;
      rawPercentages.value[it.key] = 0;
    });
    return;
  }

  const raw = items.map(it => (it.count / total) * 100);
  const floored = raw.map(r => Math.floor(r));
  let remainder = 100 - floored.reduce((a, b) => a + b, 0);

  // 最大余数分配法：按小数部分从大到小分配 +1
  const fracs = raw.map((r, i) => ({ i, frac: r - floored[i] })).sort((a, b) => b.frac - a.frac);
  const final = floored.slice();
  for (let i = 0; remainder > 0 && i < fracs.length; i++, remainder--) {
    final[fracs[i].i]++;
  }

  items.forEach((it, i) => {
    percentages.value[it.key] = final[i];
    rawPercentages.value[it.key] = raw[i];
  });
});

const percentDisplay = (c: any): string => {
  const key = String(c.id ?? c.optionId ?? candidates.indexOf(c));
  return `${percentages.value[key] ?? 0}%`;
};

const percentWidth = (c: any): string => {
  const key = String(c.id ?? c.optionId ?? candidates.indexOf(c));
  return `${(rawPercentages.value[key] ?? 0).toFixed(1)}%`;
};
const localIP = ref('')
const getLocalIp = async () => {
 const RTCPeerConnection = window.RTCPeerConnection;
  if (RTCPeerConnection) {
    const pc = new RTCPeerConnection({ iceServers: [] });
    pc.createDataChannel('');
    pc.createOffer().then(sdp => pc.setLocalDescription(sdp)).catch(console.error);
    pc.onicecandidate = ice => {
      if (ice && ice.candidate && ice.candidate.candidate) {
        const s = ice.candidate.candidate.split(' ');
        if (s[7] === 'host') {
          localIP.value = s[4];
          pc.onicecandidate = null;
          console.log('获取',localIP.value)
        }
      }
    };
  } else {
    console.error('暂不支持获取本地IP,请输入本地IP');
  }
}
getLocalIp();
async function addVote(optionId: number) {
      const res=await axios({
      url: 'https://frp-six.com:11086/api/voting',
      method: 'POST',
      data: {
        pollId: queryId.value,
        optionId: optionId,
        voteAt: new Date().toISOString(),
        ipAddress: localIP.value, // 示例IP地址
      },
  })
  return res.data;
}
const submitVote = async (): Promise<void> => {
  if (selectedCandidate.value === null || hasVoted.value) return;

  // 显示提交中状态
  isSubmitting.value = true;
  submitButtonText.value = '投票提交中...';

  try {
    // POST API请求
    await addVote(selectedCandidate.value );

  // 更新投票状态
    hasVoted.value = true;
    voteStatusText.value = '投票成功！感谢您的参与';
    voteStatusIcon.value = 'fas fa-check-circle text-green-500';
    voteStatusClass.value = 'bg-green-50 border-green-200';
    submitButtonText.value = '投票完成';
    showModal.value = true;

    // 增加票数（本地模拟）
    const idx = candidates.findIndex(c => (c.id || c.optionId) === selectedCandidate.value);
    if (idx !== -1) {
      candidates[idx].votes = (candidates[idx].votes ?? candidates[idx].count ?? 0) + 1;
      // keep count in sync if present
      if (typeof candidates[idx].count !== 'undefined') candidates[idx].count = candidates[idx].votes;
    }

    // 可以在这里更新本地存储或发送实际请求
    //console.log(`投票给候选人 ${selectedCandidate.value}`);

  } catch (error) {
    // 处理错误情况
    console.error(error);
    voteStatusText.value = '投票失败，请稍后重试';
    voteStatusIcon.value = 'fas fa-exclamation-circle text-red-500';
    voteStatusClass.value = 'bg-red-50 border-red-200';
    submitButtonText.value = '重新提交';
  } finally {
    isSubmitting.value = false;
  }
};

const closeModal = (): void => {
  showModal.value = false;
};

// 评论区状态与 API
const comments = reactive<Array<any>>([]);
const newComment = ref<string>('');
const isPosting = ref<boolean>(false);

async function loadComments() {
  try {
    const res = await axios({
      url: 'https://frp-six.com:11086/api/comments?id=' + queryId.value,
      method: 'GET',
    });
    console.log(res.data);
    const data = res.data?.data || res.data || [];

    comments.splice(0, comments.length, ...(Array.isArray(data) ? data : []));
  } catch (e) {
    console.error('加载评论失败', e);
  }
}

async function postComment() {
  if (!newComment.value || isPosting.value) return;
  isPosting.value = true;
  try {
    const payload = {
      pollId: queryId.value,
      content: newComment.value,
      userId: userStore().userId || '0',
      //createdAt: new Date().toISOString(),
    };
    await axios({ url: 'https://frp-six.com:11086/api/comments', method: 'POST', data: payload });
    // 本地追加以即时反馈
    comments.unshift(payload);
    newComment.value = '';
    toast.value.message = '评论已发布';
    toast.value.show = true;
    setTimeout(() => (toast.value.show = false), 1500);
  } catch (e) {
    console.error('发布评论失败', e);
    toast.value.message = '发布失败，请登陆后重试';
    toast.value.show = true;
    setTimeout(() => (toast.value.show = false), 1500);
  } finally {
    isPosting.value = false;
  }
}

function formatDate(d: string | Date | undefined) {
  if (!d) return '';
  const dt = new Date(d);
  return dt.toLocaleString();
}




// Avatar interaction handlers
function handleAvatarMove(e: MouseEvent) {
  const wrapper = e.currentTarget as HTMLElement;
  const svg = wrapper.querySelector('svg') as HTMLElement | null;
  if (!svg) return;
  const rect = wrapper.getBoundingClientRect();
  const x = (e as MouseEvent).clientX - rect.left;
  const y = (e as MouseEvent).clientY - rect.top;
  const px = x / rect.width;
  const py = y / rect.height;
  const rotateY = (px - 0.5) * 10;
  const rotateX = (0.5 - py) * 10;
  svg.style.transition = 'transform 0.08s linear';
  svg.style.transform = `perspective(400px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) scale(1.06)`;
}

function handleAvatarLeave(e: MouseEvent) {
  const wrapper = e.currentTarget as HTMLElement;
  const svg = wrapper.querySelector('svg') as HTMLElement | null;
  if (!svg) return;
  svg.style.transition = 'transform 0.35s cubic-bezier(.2,.8,.2,1)';
  svg.style.transform = 'perspective(400px) rotateX(0deg) rotateY(0deg) scale(1)';
}
</script>
<style scoped>
@import './src/assets/index.css';
/* Core UI styles */
.vote-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(0,0,0,0.1);
}

.photo-button {
  position: relative;
  overflow: hidden;
}

.photo-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(59,130,246,0.8) 0%, rgba(147,51,234,0.8) 100%);
  z-index: 1;
}

.button-content { position: relative; z-index: 2; }

.fade-in { animation: fadeIn 0.8s ease-in-out; }
@keyframes fadeIn { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }

.vote-card { will-change: transform, box-shadow; }
.vote-card img { display: block; }
.vote-card .shadow-inner { box-shadow: inset 0 -6px 18px rgba(0,0,0,0.06); }
.photo-button { border-radius: 12px; }
.photo-button[disabled] { filter: grayscale(0.3) brightness(0.9); }

/* Progress bar animation */
.bg-blue-600 { background: linear-gradient(90deg,#3b82f6,#60a5fa); }
.bg-green-600 { background: linear-gradient(90deg,#10b981,#34d399); }
.bg-purple-600 { background: linear-gradient(90deg,#8b5cf6,#a78bfa); }

/* Modal & transition */
.fade-enter-active, .fade-leave-active { transition: opacity 0.25s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

/* Avatar size (smaller) and responsive behavior */
.avatar-size {
  width: 160px;
  height: 160px;
  max-width: 160px;
  max-height: 160px;
  max-width: 90vw;
  max-height: 90vw;
}
.avatar-size svg { display: block; width: 100%; height: 100%; }
@media (max-width: 640px) { .avatar-size { width: 28vw; height: 28vw; } }

/* Ensure avatar container remains visually square when rounded-full class present */
.avatar-size.rounded-full { border-radius: 12px !important; overflow: hidden; }

/* Ensure SVG rect strokes (if any) scale cleanly */
.avatar-size svg rect { vector-effect: non-scaling-stroke; shape-rendering: geometricPrecision; }

/* Avatar ring and inner img styles (thinner ring) */
.avatar-ring {
  width: 100%;
  height: 100%;
  padding: 4px; /* ring thickness reduced */
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(90deg,#6ee7b7,#60a5fa);
}
.avatar-img { width: 100%; height: 100%; display: block; border-radius: 8px; object-fit: cover; }


/* Gradient title */
.gradient-title {
  background: linear-gradient(90deg,#3b82f6,#8b5cf6);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

/* card hover overlay */
.vote-card:hover .card-hover { opacity: 1; pointer-events: auto; }
.card-hover { background: linear-gradient(180deg, rgba(59,130,246,0.85), rgba(147,51,234,0.85)); }
.card-hover .hover-inner { padding: 12px; }

/* spinner */
.spinner {
  width: 28px;
  height: 28px;
  border: 3px solid rgba(255,255,255,0.3);
  border-top-color: #fff;
  border-radius: 9999px;
  animation: spin 1s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* 评论区样式 */
.comments-section textarea { min-height: 96px; max-height: 300px; }
.comments-section .border-b { border-color: rgba(0,0,0,0.06); }
.comments-section .w-10 { min-width: 40px; min-height: 40px; }
.comments-section .text-sm { line-height: 1.4; }
.comments-section button[disabled] { opacity: 0.6; cursor: not-allowed; }

/* Content container styles */

    .qr-section {
      flex: 1;
      min-width: auto;
      text-align: center;
    }
    .chart-section {
      flex: 1;
      min-width: auto;
      text-align: center;
    }
      .qr-image {
      max-width: auto;
      height: auto;
      border: 1px solid #ddd;
      margin: 0 auto;

    }
    .stats-title {
      text-align: center;
      font-size: 1.5rem;
      font-weight: bold;
      color: #333;
      margin-bottom: 20px;
    }

    /* 修复饼图容器样式 - 手机高度更小 */
    .echart {
      height: 200px;
      width: 100%;
    }

    @media (min-width: 640px) {
      .echart {
        height: 250px;
      }
    }

    @media (min-width: 768px) {
      .echart {
        height: 400px;
      }
    }
</style>
