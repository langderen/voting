<!--首页-->
<template>
    <div class="container">
        <section class="assignments">
        <div class="container">
            <div class="section-title">
                <h2>不要吝啬你手里的票数</h2>
                <p>选择你感兴趣的内容并为其投票</p>
            </div>

<<<<<<< HEAD
            <div class="search-container">
                <el-input
                    v-model="searchKeyword"
                    placeholder="搜索投票标题或发布者..."
                    clearable
                    @input="handleSearch"
                    @keyup.enter="handleSearch"
                    class="search-input"
                >
                    <template #prefix>
                        <el-icon><Search /></el-icon>
                    </template>
                </el-input>
                <el-button type="primary" :icon="Search" @click="handleSearch" class="search-btn">搜索</el-button>
                <span class="search-hint" v-if="searchKeyword">找到 {{ filteredCards.length }} 条结果</span>
            </div>

            <div >
              <ul v-infinite-scroll="load" class="assignment-grid" style="overflow: auto">
                  <div v-for="card in displayCards" :key="card.pollId">
=======
            <div >
              <ul v-infinite-scroll="load" class="assignment-grid" style="overflow: auto">
                  <div v-for="card in cards" :key="card.pollId">
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
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

                          </div>
                        </div>
                      </RouterLink>
                  </div>
<<<<<<< HEAD
                  <div v-if="displayCards.length==0 && !loading" class="empty-state">
                    <i class="fas fa-inbox"></i>
                    <p>{{ searchKeyword ? '未找到匹配的投票' : '暂无投票数据' }}</p>
=======
                  <div v-if="cards.length==0 && !loading" class="empty-state">
                    <i class="fas fa-inbox"></i>
                    <p>暂无投票数据</p>
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
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
<<<<<<< HEAD
    :total="searchKeyword ? filteredCards.length : totalCount"></el-pagination>
=======
    :total="35"></el-pagination>
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
  </div>

</template>

<script lang="ts" setup>
<<<<<<< HEAD
import { computed, reactive, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { api } from '@/utils/api'
import { $message } from '@/utils/message'
import { Search } from '@element-plus/icons-vue'

const route = useRoute()

const pageNum = computed(() => {
  const page = route.query.pageNum;
  if (!page) return 1;
  const pageStr = Array.isArray(page) ? page[0] : page;
  return pageStr ? parseInt(pageStr) : 1;
=======
import { computed, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
//路由传参
const route = useRoute()

//传参解析
const pageNum = computed(() => {
  const page = route.query.pageNum;
  return page ? parseInt(page) : 1;
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
});

const count = ref(0)
const load = () => {
  count.value += 2
}
<<<<<<< HEAD

const allCards = reactive<Array<any>>([]);
const cards = reactive<Array<any>>([]);
const loading = ref(false)
const searchKeyword = ref('')
const totalCount = ref(35)

async function getList() {
  try {
    const res=await api.get('/poll/voteslist?pageNum=' + pageNum.value)
    const data = res.data.data
    if (Array.isArray(data)) {
        allCards.splice(0, allCards.length, ...data);
        cards.splice(0, cards.length, ...data);
        totalCount.value = data.length || 35
    }
    return data;
  } catch (error) {
    console.error('获取投票列表失败:', error)
    return []
  }
}

getList()

const filteredCards = computed(() => {
    if (!searchKeyword.value.trim()) {
        return allCards
    }
    const keyword = searchKeyword.value.toLowerCase().trim()
    const results = allCards.filter(card => {
        const titleMatch = String(card.title || '').toLowerCase().includes(keyword)
        const creatorMatch = String(card.creator_id || '').toLowerCase().includes(keyword)
        return titleMatch || creatorMatch
    })
    return results
})

const displayCards = computed(() => {
    const start = (pageNum.value - 1) * pageSize.value
    const end = start + pageSize.value
    return filteredCards.value.slice(start, end)
})

function handleSearch() {
    currentPage.value = 1
}

const currentPage = ref(1)
const pageSize = ref(12)

const handleSizeChange = (val: number) => {
  currentPage.value = Math.ceil(filteredCards.value.length / val)
  pageSize.value = val
  $message.info(`每页显示 ${val} 条数据`);
}

const handleCurrentChange = (val: number) => {
  currentPage.value = val
  $message.success(`正在跳转到第 ${val} 页`);
  setTimeout(() => {
    window.location.href = `/home?pageNum=${val}`;
  }, 500);
}
</script>

=======
const cards = reactive<Array<any>>([]);
getList().then((res) => {
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
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
<<<<<<< HEAD
.search-container {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 20px;
    flex-wrap: wrap;
}
.search-input {
    max-width: 400px;
    width: 100%;
}
.search-btn {
    flex-shrink: 0;
}
.search-hint {
    color: #909399;
    font-size: 14px;
}
.empty-state {
    text-align: center;
    padding: 60px 20px;
    color: #909399;
}
.empty-state i {
    font-size: 48px;
    margin-bottom: 16px;
    display: block;
}
.empty-state p {
    margin: 0;
    font-size: 16px;
}
=======
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
</style>
