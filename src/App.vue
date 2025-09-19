<script setup lang="ts">


</script>

<template>
  <div id="app">
  <header>
           <div class="container">
            <nav>
                <a href="http://wy667.cloudns.biz" class="logo">Voting</a>
                <ul class="nav-links">
                    <li><RouterLink to="/home">进行中的投票</RouterLink></li>
                    <li><RouterLink to="/voted">已结束的投票</RouterLink></li>
                    <li><RouterLink to="/contractus">联系我们</RouterLink></li>
                </ul>
              <RouterLink to="login" class="btn">登录</RouterLink>
            </nav>
        </div>
  </header>
</div>
<div>
  <RouterView />
</div>

</template>
