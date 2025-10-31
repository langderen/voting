<template>
    <div class="container">
        <section class="assignments">
        <div class="container">
            <div class="section-title">
                <h2>已结束的投票</h2>
                <p>让我们看看往期投票中哪些内容最受欢迎吧</p>
            </div>

            <div class="assignment-grid">
                <!-- 作业卡片1 -->
                 <RouterLink to="/vote?id=-1" class="assignment-card-link"  >
                  <div class="assignment-card">

                      <div class="assignment-info">
                          <h3>2025软工之星</h3>
                          <div class="assignment-meta">
                              <span>截止: 2025/12/31</span>
                              <span>发布者：班长</span>
                          </div>
                          <div>
                              <span class="assignment-tag tag-voted">已结束</span>
                          </div>
                          <p>第一名：deai</p>
                          <p>第二名：小deai</p>
                          <p>第三名：小小deai</p>

                      </div>
                    </div>
                  </RouterLink>


              </div>
        </div>
      </section>
    </div>
</template>

