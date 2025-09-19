<template>
  <div class="container">
    <div class="vote-page">

      <div class="section-title">
        <h2>23软工之星</h2>
        <p>为23软件工程选择你最喜欢的人物</p>
      </div>
      <!-- 单选框 -->
      <input type="radio" value="0" v-model="who">deai
      <input type="radio" value="1" v-model="who">小deai
      <input type="radio" value="2" v-model="who">小小deai
      <button @click="submit">提交</button>

    </div>
  </div>
</template>


<script setup lang="ts">
import { ref } from 'vue';

const who = ref('0');
const submit = () => {
  alert(`你投给了${who.value}`);
};
</script>

