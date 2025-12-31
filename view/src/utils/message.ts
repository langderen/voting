import { h, render } from 'vue'
import Message from '../components/Message.vue'

interface MessageOptions {
  message: string
  type?: 'success' | 'error' | 'warning' | 'info'
  duration?: number
}

let messageInstance: any = null

function createMessage() {
  if (!messageInstance) {
    const container = document.createElement('div')
    document.body.appendChild(container)
    messageInstance = h(Message)
    render(messageInstance, container)
  }
  return messageInstance.component?.exposed
}

export function $message(options: MessageOptions) {
  const instance = createMessage()
  if (instance) {
    instance.show(options)
  }
}

$message.success = (message: string, duration?: number) => {
  $message({ message, type: 'success', duration })
}

$message.error = (message: string, duration?: number) => {
  $message({ message, type: 'error', duration })
}

$message.warning = (message: string, duration?: number) => {
  $message({ message, type: 'warning', duration })
}

$message.info = (message: string, duration?: number) => {
  $message({ message, type: 'info', duration })
}

export default $message
