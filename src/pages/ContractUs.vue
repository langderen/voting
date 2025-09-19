<script setup lang="ts">

</script>

<template>
      <!-- 联系表单 -->
    <section class="contact">
        <div class="container">
            <div class="section-title">
                <h2>联系我们</h2>
                <p>有任何问题或建议？请随时与我们联系</p>
            </div>

            <div class="contact-form">
                <form>
                    <div class="form-group">
                        <label for="name">姓名</label>
                        <input type="text" id="name" class="form-control" placeholder="请输入您的姓名" required>
                    </div>

                    <div class="form-group">
                        <label for="email">电子邮箱</label>
                        <input type="email" id="email" class="form-control" placeholder="请输入您的电子邮箱" required>
                    </div>

                    <div class="form-group">
                        <label for="subject">主题</label>
                        <input type="text" id="subject" class="form-control" placeholder="请输入主题" required>
                    </div>

                    <div class="form-group">
                        <label for="message">留言内容</label>
                        <textarea id="message" class="form-control" placeholder="请输入您的留言内容" required></textarea>
                    </div>

                    <button type="submit" class="submit-btn">发送留言</button>
                </form>
            </div>
        </div>
    </section>
</template>
