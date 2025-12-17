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
                    <RouterLink :to="`/vote?id=${card.poll_id}`" class="assignment-card-link"  >
                      <div class="assignment-card">
                          <div class="assignment-info">
                              <h3>{{card.title}}</h3>
                              <div class="assignment-meta">
                                  <span>截止: {{card.end_time}}</span>
                                  <span>发布者：{{card.creator_id}}</span>
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
    <el-pagination align="center" class="pagination"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="pageNum"
    :page-size="pageSize"
    layout="prev, pager, next, jumper"
    :total="35"></el-pagination>
  </div>

</template>

<script lang="ts" setup>
import { computed, reactive, ref } from 'vue'
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

//分页

// 响应式数据
const currentPage = ref(1)
const pageSize = ref(12) // 默认每页12条

// 每页条数改变时触发
const handleSizeChange = (val) => {
  console.log(`每页 ${val} 条`)
  currentPage.value = cards.length / val
  pageSize.value = val
}

// 当前页改变时触发
const handleCurrentChange = (val) => {
  console.log(`当前页: ${val}`)
  currentPage.value = val
  //跳转页面
  window.location.href = `/home?pageNum=${val}`;
}
</script>
<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
