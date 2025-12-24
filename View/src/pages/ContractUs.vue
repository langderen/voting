
<template>
      <!-- 联系表单 -->
    <section class="contact">
        <div class="container">
            <div class="section-title">
                <h2>联系我们</h2>
                <p>有任何问题或建议？请随时与我们联系</p>
            </div>

            <div class="contact-form">
                <form @submit.prevent="sendmessage">

                    <div class="form-group">
                        <label for="message">留言内容</label>
                        <textarea id="message" v-model="message" class="form-control" placeholder="请输入您的留言内容" required></textarea>
                    </div>

                    <button type="submit" class="submit-btn">发送留言</button>
                </form>
            </div>
        </div>
    </section>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { userStore } from '@/stores/user';
const user = userStore();

const name = ref('');
const email = ref('');
const subject = ref('');
const message = ref('');

const sendmessage = async () => {
  try {
    await axios.post('https://frp-six.com:11086/api/comments', {
      userId: user.userId,
      pollId:19736040,
      content: message.value
    });
    // clear form after send
    name.value = '';
    email.value = '';
    subject.value = '';
    message.value = '';
  } catch (err) {
    console.error(err);
  }
};
</script>
