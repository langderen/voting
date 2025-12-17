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
                <label for="vote-description" class="block text-sm font-medium text-gray-700 mb-1">投票描述（可选）</label>
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
                    <i class="fa fa-trash-o"></i>
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

              <!-- 截止日期 -->
              <div>
                <label for="end-date" class="block text-sm font-medium text-gray-700 mb-1">截止日期（可选）</label>
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

          <!-- 高级设置 -->
          <div class="bg-white rounded-xl p-6 card-shadow hover-lift">
            <button
              id="advanced-settings-toggle"
              class="flex justify-between items-center w-full text-left"
              @click="advancedSettingsOpen = !advancedSettingsOpen"
            >
              <h2 class="text-lg font-semibold flex items-center">
                <i class="fa fa-sliders text-primary mr-2"></i>
                高级设置
              </h2>
              <i class="fa fa-chevron-down text-gray-400 transition-transform duration-300" :class="{ 'rotate-180': advancedSettingsOpen }"></i>
            </button>

            <div
              id="advanced-settings-content"
              class="mt-4 space-y-5"
              v-if="advancedSettingsOpen"
            >
              <!-- 访问权限 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">访问权限</label>
                <select
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  v-model="vote.accessPermission"
                  @change="updatePasswordProtection"
                >
                  <option value="public">公开 - 任何人可见可参与</option>
                  <option value="password">密码保护 - 需要密码才能参与</option>
                  <option value="private">私密 - 仅邀请的人可见</option>
                </select>
              </div>

              <!-- 密码保护 -->
              <div id="password-protection-container" v-if="showPasswordProtection">
                <label for="vote-password" class="block text-sm font-medium text-gray-700 mb-1">投票密码</label>
                <input
                  type="password"
                  id="vote-password"
                  v-model="vote.password"
                  placeholder="设置访问密码"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                >
              </div>

              <!-- 结果可见性 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">结果可见性</label>
                <select
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
                  v-model="vote.resultVisibility"
                >
                  <option value="always">始终可见 - 任何人随时可见结果</option>
                  <option value="voted">投票后可见 - 只有参与投票后才能看到结果</option>
                  <option value="closed">结束后可见 - 投票结束后才公开结果</option>
                </select>
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
                <p id="preview-end-date" class="mt-1">{{ preview.endDate ? `截止日期: ${formatDate(preview.endDate)}` : '无截止日期' }}</p>
              </div>
            </div>

            <!-- 操作按钮 -->
            <div class="mt-6 space-y-3">
              <button
                id="save-draft-btn"
                class="w-full py-3 px-4 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors flex items-center justify-center"
                @click="saveDraft"
              >
                <i class="fa fa-save mr-2"></i> 保存为草稿
              </button>

              <button
                id="publish-btn"
                class="w-full py-3 px-4 bg-primary text-white rounded-lg font-medium hover:bg-primary/90 transition-colors flex items-center justify-center shadow-md hover:shadow-lg"
                @click="publishVote"
              >
                <i class="fa fa-paper-plane mr-2"></i> 发布投票
              </button>

              <button
                id="preview-full-btn"
                class="w-full py-2 px-4 text-primary hover:text-primary/80 text-sm font-medium transition-colors flex items-center justify-center"
                @click="showFullPreview"
              >
                <i class="fa fa-expand mr-1"></i> 查看完整预览
              </button>
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

<script>
export default {
  data() {
    return {
      // 用户信息
      userName: '张小明',
      userAvatar: 'https://picsum.photos/id/64/200/200',

      // 投票数据
      vote: {
        title: '',
        description: '',
        type: 'single', // single 或 multiple
        multipleLimit: 2,
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
      },

      // 预览数据
      preview: {
        title: '',
        description: '',
        type: 'single',
        multipleLimit: 2,
        endDate: '',
        options: [
          { text: '', imageUrl: '' },
          { text: '', imageUrl: '' }
        ]
      },

      // 状态管理
      advancedSettingsOpen: false,
      showPasswordProtection: false,
      isScrolled: false,
      showSuccessModal: false,
      copied: false
    };
  },

  mounted() {
    // 初始化预览
    this.updatePreview();

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

    // 添加选项
    addOption() {
      this.vote.options.push({ text: '', imageUrl: '' });
      this.updatePreview();
    },

    // 移除选项
    removeOption(index) {
      if (this.vote.options.length > 2) {
        this.vote.options.splice(index, 1);
        this.updatePreview();
      }
    },

    // 处理图片上传
    handleImageUpload(event, index) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.vote.options[index].imageUrl = e.target.result;
          this.updatePreview();
        };
        reader.readAsDataURL(file);
      }
    },

    // 移除图片
    removeImage(index) {
      this.vote.options[index].imageUrl = '';
      this.updatePreview();
    },

    // 更新密码保护显示状态
    updatePasswordProtection() {
      this.showPasswordProtection = this.vote.accessPermission === 'password';
    },

    // 更新预览
    updatePreview() {
      // 复制基本信息
      this.preview.title = this.vote.title;
      this.preview.description = this.vote.description;
      this.preview.type = this.vote.type;
      this.preview.multipleLimit = this.vote.multipleLimit;
      this.preview.endDate = this.vote.endDate;

      // 复制选项
      this.preview.options = [...this.vote.options];
    },

    // 格式化日期显示
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      });
    },

    // 保存草稿
    saveDraft() {
      // 简单验证
      if (!this.validateVote()) return;

      // 模拟保存API
      console.log('保存草稿:', this.vote);
      alert('草稿已保存');
    },

    // 发布投票
    publishVote() {
      // 验证投票信息
      if (!this.validateVote()) return;

      // 模拟发布API
      console.log('发布投票:', this.vote);
      this.showSuccessModal = true;
    },

    // 验证投票信息
    validateVote() {
      if (!this.vote.title.trim()) {
        alert('请输入投票标题');
        return false;
      }

      // 验证选项
      for (let i = 0; i < this.vote.options.length; i++) {
        if (!this.vote.options[i].text.trim()) {
          alert(`请完善选项 ${i + 1} 的内容`);
          return false;
        }
      }

      // 验证密码（如果需要）
      if (this.vote.accessPermission === 'password' && !this.vote.password.trim()) {
        alert('请设置投票密码');
        return false;
      }

      return true;
    },

    // 查看完整预览
    showFullPreview() {
      alert('完整预览功能即将上线');
    },

    // 复制投票链接
    copyVoteLink() {
      // 模拟链接复制
      const dummyLink = 'https://votehub.example.com/v/abc123';
      navigator.clipboard.writeText(dummyLink).then(() => {
        this.copied = true;
        setTimeout(() => {
          this.copied = false;
        }, 2000);
      });
    },

    // 创建新投票
    createNewVote() {
      if (confirm('确定要创建新投票吗？当前内容将被清空。')) {
        // 重置表单
        this.vote = {
          title: '',
          description: '',
          type: 'single',
          multipleLimit: 2,
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
        };

        // 更新预览
        this.updatePreview();

        // 关闭弹窗
        this.showSuccessModal = false;
      }
    }
  }

};

// 示例的投票添加函数
import axios from 'axios';
import { ref } from 'vue';
async function addVote() {
      const res=await axios({
      url: 'https://frp-six.com:11086/api/voting',
      method: 'POST',
      data: {
        pollid: queryId.value,
        optionid: selectedCandidate.value,
      },
  })
  return res.data;
}
</script>

<style scoped>
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
