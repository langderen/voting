<!--首页-->
<template>
    <div class="container">
        <section class="assignments">
        <div class="container">
            <div class="section-title">
                <h2>不要吝啬你手里的票数</h2>
                <p>选择你感兴趣的内容并为其投票</p>
            </div>

            <div >
              <ul v-infinite-scroll="load" class="assignment-grid" style="overflow: auto">
                  <div v-for="card in cards" :key="card.pollId">
                    <RouterLink :to="`/vote?id=${card.pollId}`" class="assignment-card-link"  >
                      <div class="assignment-card">
                          <div class="assignment-info">
                              <h3>{{card.title}}</h3>
                              <div class="assignment-meta">
                                  <span>截止: {{card.endTime}}</span>
                                  <span>发布者：{{card.creatorId}}</span>
                              </div>
                              <div>
                                  <span class="assignment-tag tag-voting">{{card.status}}</span>
                              </div>
                              <p>第一名：{{card.firstPlace}}</p>
                              <p>第二名：{{card.secondPlace}}</p>
                              <p>第三名：{{card.thirdPlace}}</p>
                          </div>
                        </div>
                      </RouterLink>
                  </div>
                  <div v-if="cards.length==0 && !loading" class="empty-state">
                    <i class="fas fa-inbox"></i>
                    <p>暂无投票数据</p>
                  </div>
                </ul>
              </div>

        </div>
      </section>
    </div>
    <h3>{{ pageNum }}</h3>



  <el-backtop :right="100" :bottom="100" style="color: #4a6bff"/>


</template>

<script lang="ts" setup>
import { computed, reactive, ref, toRef } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
//路由传参
const route = useRoute()

//传参解析
const pageNum = computed(() => {
  const page = route.query.pageNum;
  return page ? parseInt(page) : 1;
});

const count = ref(0)
const load = () => {
  count.value += 2
}
const cards = reactive<Array<any>>([]);
getList().then((res) => {
  console.log(res);
  cards.splice(0, cards.length, ...res); // 清空并替换内容
});
const loading = ref(false)

async function getList() {
  const res=await axios({
    url: 'https://frp-six.com:11086/api/poll/voteslist?pageNum=' + pageNum.value,
    method: 'GET',
  })
  return res.data.data;
}
</script>
