<template>
  <Teleport to="body">
    <Transition name="message-fade">
      <div v-if="visible" :class="['message-container', type]">
        <div class="message-content">
          <span class="message-icon">
            <i v-if="type === 'success'" class="fa fa-check-circle"></i>
            <i v-else-if="type === 'error'" class="fa fa-times-circle"></i>
            <i v-else-if="type === 'warning'" class="fa fa-exclamation-circle"></i>
            <i v-else class="fa fa-info-circle"></i>
          </span>
          <span class="message-text">{{ message }}</span>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

interface MessageProps {
  message: string
  type?: 'success' | 'error' | 'warning' | 'info'
  duration?: number
}

const visible = ref(false)
const message = ref('')
const type = ref<MessageProps['type']>('info')
const duration = ref(3000)
let timer: ReturnType<typeof setTimeout> | null = null

const show = (options: MessageProps) => {
  message.value = options.message
  type.value = options.type || 'info'
  duration.value = options.duration || 3000
  visible.value = true
  
  if (timer) clearTimeout(timer)
  timer = setTimeout(() => {
    visible.value = false
  }, duration.value)
}

const hide = () => {
  visible.value = false
}

defineExpose({
  show,
  hide
})
</script>

<style scoped>
.message-container {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
  padding: 12px 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  min-width: 300px;
  max-width: 80vw;
}

.message-container.success {
  background-color: #f0f9eb;
  border: 1px solid #e1f3d8;
  color: #67c23a;
}

.message-container.error {
  background-color: #fef0f0;
  border: 1px solid #fde2e2;
  color: #f56c6c;
}

.message-container.warning {
  background-color: #fdf6ec;
  border: 1px solid #faecd8;
  color: #e6a23c;
}

.message-container.info {
  background-color: #edf2fc;
  border: 1px solid #e4e7ed;
  color: #909399;
}

.message-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.message-icon {
  font-size: 16px;
}

.message-text {
  font-size: 14px;
  line-height: 1.5;
}

.message-fade-enter-active,
.message-fade-leave-active {
  transition: all 0.3s ease;
}

.message-fade-enter-from,
.message-fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-20px);
}
</style>
