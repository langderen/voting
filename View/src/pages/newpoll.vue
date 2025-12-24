<template>
  <div class="font-inter bg-neutral min-h-screen text-dark">
    <!-- 主内容区 -->
    <main class="container mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 页面标题 -->
      <div class="mb-8">
        <h1 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-dark">创建新投票</h1>
        <p class="text-gray-500 mt-2">设计一个投票，收集大家的意见和想法</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- 左侧：投票设置表单 -->
        <div class="lg:col-span-2 space-y-6">
          <!-- 投票标题设置 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <h2 class="text-lg font-semibold mb-4 flex items-center">
              <i class="fa fa-heading text-primary mr-2"></i>
              投票标题与描述
            </h2>
            <div class="space-y-4">
              <div>
                <label for="vote-title" class="block text-sm font-medium text-gray-700 mb-1">投票标题 <span class="text-danger">*</span></label>
                <input
                  type="text"
                  id="vote-title"
                  v-model="vote.title"
                  placeholder="请输入投票标题"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  required
                  @input="updatePreview"
                >
                <p class="text-xs text-gray-500 mt-1">简洁明了的标题有助于获得更多参与</p>
              </div>
              <div>
                <label for="vote-description" class="block text-sm font-medium text-gray-700 mb-1">投票描述</label>
                <textarea
                  id="vote-description"
                  rows="3"
                  v-model="vote.description"
                  placeholder="请输入投票的详细描述或背景信息..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none resize-none"
                  @input="updatePreview"
                ></textarea>
              </div>
            </div>
          </div>

          <!-- 投票选项设置 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <h2 class="text-lg font-semibold mb-4 flex items-center">
              <i class="fa fa-list-ul text-primary mr-2"></i>
              投票选项
            </h2>
            <p class="text-sm text-gray-500 mb-4">添加投票选项，至少需要2个选项，可选择为选项添加图片</p>

            <div id="options-container" class="space-y-6">
              <div class="option-item" v-for="(option, index) in vote.options" :key="index">
                <div class="flex items-center space-x-2 mb-3">
                  <div class="flex-1">
                    <input
                      type="text"
                      :placeholder="`选项 ${index + 1}`"
                      v-model="option.text"
                      class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none option-text"
                      @input="updatePreview"
                    >
                  </div>
                  <button
                    type="button"
                    class="text-danger hover:text-danger/80 p-2 opacity-50 hover:opacity-100 transition-opacity delete-option"
                    :disabled="vote.options.length <= 2"
                    :class="{ 'opacity-70': vote.options.length > 2, 'opacity-50': vote.options.length <= 2 }"
                    @click="removeOption(index)"
                  >
                    <i class="fa fa-trash-o">×</i>
                  </button>
                </div>

                <!-- 图片上传区域 -->
                <div class="flex items-center space-x-3">
                  <label class="flex items-center justify-center w-16 h-16 border-2 border-dashed border-gray-300 rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100 transition-colors">
                    <input
                      type="file"
                      class="hidden option-image-upload"
                      accept="image/*"
                      @change="handleImageUpload($event, index)"
                    />
                    <div class="flex flex-col items-center justify-center pt-5 pb-6">
                      <i class="fa fa-cloud-upload text-gray-400 mb-1"></i>
                      <span class="text-xs text-gray-500">添加图片</span>
                    </div>
                  </label>

                  <div class="option-image-preview" :class="{ hidden: !option.imageUrl }">
                    <img :src="option.imageUrl" alt="预览图" class="image-preview rounded-lg border border-gray-200 w-full">
                    <button
                      type="button"
                      class="mt-1 text-xs text-danger hover:text-danger/80 remove-image"
                      @click="removeImage(index)"
                    >
                      <i class="fa fa-times-circle mr-1"></i> 移除图片
                    </button>
                  </div>

                  <p class="text-xs text-gray-500 flex-1">支持 JPG、PNG 格式，建议尺寸 400x300px</p>
                </div>
              </div>
            </div>

            <button
              id="add-option-btn"
              type="button"
              class="mt-4 text-primary hover:text-primary/80 flex items-center text-sm font-medium transition-colors"
              @click="addOption"
            >
              <i class="fa fa-plus-circle mr-1"></i> 添加选项
            </button>
          </div>

          <!-- 投票规则设置 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <h2 class="text-lg font-semibold mb-4 flex items-center">
              <i class="fa fa-cog text-primary mr-2"></i>
              投票规则
            </h2>

            <div class="space-y-5">
              <!-- 投票类型 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">投票类型 <span class="text-danger">*</span></label>
                <div class="grid grid-cols-1 sm:grid-cols-2 gap-3">
                  <label class="flex items-center p-3 border border-gray-200 rounded-lg cursor-pointer hover:border-primary transition-colors">
                    <input
                      type="radio"
                      name="vote-type"
                      value="single"
                      class="text-primary focus:ring-primary"
                      v-model="vote.type"
                      @change="updatePreview"
                    >
                    <div class="ml-3">
                      <span class="block text-sm font-medium">单选投票</span>
                      <span class="block text-xs text-gray-500">参与者只能选择一个选项</span>
                    </div>
                  </label>

                  <label class="flex items-center p-3 border border-gray-200 rounded-lg cursor-pointer hover:border-primary transition-colors">
                    <input
                      type="radio"
                      name="vote-type"
                      value="multiple"
                      class="text-primary focus:ring-primary"
                      v-model="vote.type"
                      @change="updatePreview"
                    >
                    <div class="ml-3">
                      <span class="block text-sm font-medium">多选投票</span>
                      <span class="block text-xs text-gray-500">参与者可以选择多个选项</span>
                    </div>
                  </label>
                </div>
              </div>

              <!-- 多选限制 -->
              <div id="multiple-limit-container" v-if="vote.type === 'multiple'">
                <label for="multiple-limit" class="block text-sm font-medium text-gray-700 mb-1">最多可选数量</label>
                <input
                  type="number"
                  id="multiple-limit"
                  min="2"
                  v-model="vote.multipleLimit"
                  class="w-24 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  @change="updatePreview"
                >
              </div>

              <!-- 开始日期 -->
              <div>
                <label for="begin-date" class="block text-sm font-medium text-gray-700 mb-1">开始日期</label>
                <input
                  type="datetime-local"
                  id="begin-date"
                  v-model="vote.beginDate"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  @change="updatePreview"
                >
                <p class="text-xs text-gray-500 mt-1">不设置则为永久有效</p>
              </div>

              <!-- 截止日期 -->
              <div>
                <label for="end-date" class="block text-sm font-medium text-gray-700 mb-1">截止日期</label>
                <input
                  type="datetime-local"
                  id="end-date"
                  v-model="vote.endDate"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  @change="updatePreview"
                >
                <p class="text-xs text-gray-500 mt-1">不设置则为永久有效</p>
              </div>

              <!-- 匿名设置 -->
              <div>
                <label class="flex items-center">
                  <input
                    type="checkbox"
                    id="anonymous-vote"
                    class="text-primary focus:ring-primary rounded"
                    v-model="vote.isAnonymous"
                  >
                  <span class="ml-2 text-sm text-gray-700">匿名投票</span>
                </label>
                <p class="text-xs text-gray-500 mt-1">勾选后，参与者的投票选择不会被公开显示</p>
              </div>

              <!-- 重复投票 -->
              <div>
                <label class="flex items-center">
                  <input
                    type="checkbox"
                    id="allow-repeat"
                    class="text-primary focus:ring-primary rounded"
                    v-model="vote.allowRepeat"
                  >
                  <span class="ml-2 text-sm text-gray-700">允许重复投票</span>
                </label>
                <p class="text-xs text-gray-500 mt-1">勾选后，同一用户可以多次投票</p>
              </div>
            </div>
          </div>

        </div>

        <!-- 右侧：预览与操作 -->
        <div class="lg:col-span-1 space-y-6">
          <!-- 投票预览 -->
          <div class="bg-white rounded-xl p-6 card-shadow sticky top-20">
            <h2 class="text-lg font-semibold mb-4 flex items-center">
              <i class="fa fa-eye text-primary mr-2"></i>
              投票预览
            </h2>

            <div class="border border-gray-200 rounded-lg p-4 bg-light/50">
              <h3 id="preview-title" class="font-medium text-center mb-3 text-gray-800">{{ preview.title || '点击输入投票标题' }}</h3>
              <p
                id="preview-description"
                class="text-sm text-gray-600 text-center mb-4"
                v-if="preview.description"
              >{{ preview.description }}</p>

              <div id="preview-options" class="space-y-4 mt-4">
                <div class="preview-option flex flex-col items-center p-3 bg-white rounded border border-gray-200" v-for="(option, index) in preview.options" :key="index">
                  <div class="preview-image mb-2 w-full max-w-xs" v-if="option.imageUrl">
                    <img :src="option.imageUrl" alt="" class="rounded-lg w-full h-32 object-cover">
                  </div>
                  <div class="flex items-center w-full">
                    <input
                      type="radio"
                      name="preview-vote"
                      class="ml-1"
                      v-if="preview.type === 'single'"
                    >
                    <input
                      type="checkbox"
                      class="ml-1"
                      v-if="preview.type === 'multiple'"
                    >
                    <span class="ml-3 text-sm preview-text">{{ option.text || `选项 ${index + 1}` }}</span>
                  </div>
                </div>
              </div>

              <div class="mt-4 text-center text-xs text-gray-500">
                <p>投票规则: {{ preview.type === 'single' ? '单选投票' : `多选投票 (最多${preview.multipleLimit}项)` }}</p>
                <p id="preview-begin-date" class="mt-1">{{ preview.beginDate ? `开始日期: ${formatDate(preview.beginDate)}` : '无开始日期' }}</p>
                <p id="preview-end-date" class="mt-1">{{ preview.endDate ? `截止日期: ${formatDate(preview.endDate)}` : '无截止日期' }}</p>
              </div>
            </div>

            <!-- 操作按钮 -->
            <div class="mt-6 space-y-3">

              <button
                id="publish-btn"
                class="w-full py-3 px-4 bg-primary rounded-lg font-medium hover:bg-primary/90 transition-colors flex items-center justify-center shadow-md hover:shadow-lg"
                @click="publishVote"
              >发布投票</button>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- 发布成功弹窗 -->
    <div id="success-modal" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50" v-if="showSuccessModal">
      <div class="bg-white rounded-xl p-6 max-w-md w-full mx-4 transform transition-all">
        <div class="text-center">
          <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <i class="fa fa-check text-2xl text-green-500"></i>
          </div>
          <h3 class="text-xl font-bold text-gray-900 mb-2">投票发布成功！</h3>
          <p class="text-gray-600 mb-6">您的投票已成功发布，可以开始分享给他人参与了。</p>
          <div class="flex space-x-3">
            <button
              id="copy-link-btn"
              class="flex-1 py-2 px-4 bg-primary text-white rounded-lg font-medium hover:bg-primary/90 transition-colors"
              @click="copyVoteLink"
            >
              <i class="fa fa-link mr-1"></i> {{ copied ? '已复制' : '复制链接' }}
            </button>
            <button
              id="new-vote-btn"
              class="flex-1 py-2 px-4 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors"
              @click="createNewVote"
            >
              <i class="fa fa-plus mr-1"></i> 新建投票
            </button>
          </div>
          <button
            id="close-modal-btn"
            class="mt-4 w-full py-2 px-4 text-gray-500 hover:text-gray-700 transition-colors text-sm"
            @click="showSuccessModal = false"
          >
            返回我的投票
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { userStore } from '@/stores/user';
import axios from 'axios';
import { ref, reactive,  onMounted, onBeforeUnmount, watch } from 'vue';
// ====== 类型定义 ======
interface VoteOption {
  text: string;
  imageUrl: string;
}

interface VoteData {
  title: string;
  description: string;
  type: 'single' | 'multiple';
  multipleLimit: number;
  beginDate: string; // ISO string or empty
  endDate: string; // ISO string or empty
  isAnonymous: boolean;
  allowRepeat: boolean;
  accessPermission: 'public' | 'password';
  password: string;
  resultVisibility: 'always' | 'afterVote' | 'closed';
  options: VoteOption[];
}

// ====== 响应式状态 ======
const user=userStore();

const vote = reactive<VoteData>({
  title: '',
  description: '',
  type: 'single',
  multipleLimit: 2,
  beginDate: '',
  endDate: '',
  isAnonymous: false,
  allowRepeat: false,
  accessPermission: 'public',
  password: '',
  resultVisibility: 'always',
  options: [
    { text: '', imageUrl: '' },
    { text: '', imageUrl: '' }
  ]
});

const preview = reactive({
  title: '',
  description: '',
  type: 'single' as 'single' | 'multiple',
  multipleLimit: 2,
  beginDate: '',
  endDate: '',
  options: [...vote.options] as VoteOption[]
});

const isScrolled = ref(false);
const showSuccessModal = ref(false);
const copied = ref(false);

// ====== 方法 ======
const handleScroll = () => {
  isScrolled.value = window.scrollY > 10;
};

const addOption = () => {
  vote.options.push({ text: '', imageUrl: '' });
  updatePreview();
};

const removeOption = (index: number) => {
  if (vote.options.length > 2) {
    vote.options.splice(index, 1);
    updatePreview();
  }
};

const handleImageUpload = (event: Event, index: number) => {
  const input = event.target as HTMLInputElement;
  const file = input.files?.[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      vote.options[index].imageUrl = e.target?.result as string;
      updatePreview();
    };
    reader.readAsDataURL(file);
    // 清空 input，以便下次选择相同文件也能触发 change
    input.value = '';
  }
};

const removeImage = (index: number) => {
  vote.options[index].imageUrl = '';
  updatePreview();
};


const updatePreview = () => {
  preview.title = vote.title;
  preview.description = vote.description;
  preview.type = vote.type;
  preview.multipleLimit = vote.multipleLimit;
  preview.beginDate = vote.beginDate;
  preview.endDate = vote.endDate;
  preview.options = [...vote.options]; // 浅拷贝即可
};

const formatDate = (dateString: string) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};

const validateVote = (): boolean => {
  if (!vote.title.trim()) {
    alert('请输入投票标题');
    return false;
  }

  for (let i = 0; i < vote.options.length; i++) {
    if (!vote.options[i].text.trim()) {
      alert(`请完善选项 ${i + 1} 的内容`);
      return false;
    }
  }

  if (vote.accessPermission === 'password' && !vote.password.trim()) {
    alert('请设置投票密码');
    return false;
  }

  return true;
};


const publishVote = async () => {
  if (!validateVote()) return;
  const res=await axios({
      url: 'https://frp-six.com:11086/api/poll',
      method: 'POST',
      data: {
            "title": vote.title,
            "description": vote.description,
            "creatorId": user.userId,
            "startTime": vote.beginDate,
            "endTime": vote.endDate,
            "isPublic": vote.accessPermission==='public'?true:false,
            "isAnonymous": vote.isAnonymous,
            "allowMultiple": vote.type==="multiple"?true:false,
            "maxChoice": vote.type==="multiple"?vote.multipleLimit:1,
            "status": vote.beginDate && new Date(vote.beginDate) > new Date() ? 'closed' : 'active',
      },

  })
  console.log('创建投票返回:', res.data);
  for(let i=0;i<vote.options.length;i++){



 await axios({
      url: 'https://frp-six.com:11086/api/options',
      method: 'POST',
      data: {
            "pollId": res.data.data.pollId,
            "optionText": vote.options[i].text,
            "imageUrl": vote.options[i].imageUrl,
            "count": 0
      },
  })
  }
  showSuccessModal.value = true;
};

const copyVoteLink = async () => {
  const dummyLink = 'https://frp-six.com:11086/api/vote?id=';
  try {
    await navigator.clipboard.writeText(dummyLink);
    copied.value = true;
    setTimeout(() => (copied.value = false), 2000);
  } catch (err) {
    alert('复制失败，请手动复制链接');
  }
};

const createNewVote = () => {
  if (confirm('确定要创建新投票吗？当前内容将被清空。')) {
    Object.assign(vote, {
      title: '',
      description: '',
      type: 'single',
      multipleLimit: 2,
      beginDate: '',
      endDate: '',
      isAnonymous: false,
      allowRepeat: false,
      accessPermission: 'public',
      password: '',
      resultVisibility: 'always',
      options: [
        { text: '', imageUrl: '' },
        { text: '', imageUrl: '' }
      ]
    });
    updatePreview();
    showSuccessModal.value = false;
  }
};

// ====== 生命周期 & 监听 ======
onMounted(() => {
  updatePreview();
  window.addEventListener('scroll', handleScroll);
});

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll);
});

// 可选：监听 vote 变化自动更新预览（避免漏调用）
watch(
  () => ({ ...vote }), // 深度监听（简化写法，实际可用 deep: true）
  () => updatePreview(),
  { deep: true }
);
</script>

<style scoped>
@import 'tailwindcss/tailwind.css';
@layer utilities {
  .content-auto {
    content-visibility: auto;
  }
  .transition-height {
    transition: max-height 0.3s ease-in-out;
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
  .image-preview {
    height: 120px;
    object-fit: cover;
    transition: all 0.2s ease;
  }
  .image-preview:hover {
    opacity: 0.9;
  }
}

.hidden {
  display: none;
}
</style>
