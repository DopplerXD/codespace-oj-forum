<template>
  <a-layout>
    <a-layout-header>
      <NavBar />
    </a-layout-header>
    <a-layout-content>
      <div class="content-wrapper">
        <div class="left-background"></div>
        <div class="middle-content">
          <router-view />
        </div>
        <div class="right-background"></div>
      </div>
    </a-layout-content>
    <a-layout-footer>
      <FootBar />
    </a-layout-footer>
  </a-layout>
</template>

<script lang="ts" setup>
import NavBar from "@/components/NavBar.vue";
import FootBar from "@/components/FootBar.vue";
</script>

<style scoped>
.content-wrapper {
  display: flex;
  justify-content: center; /* 水平居中 */
  min-height: calc(100vh - 70px - 65px); /* 确保容器至少占满视口高度 */
}

.left-background,
.right-background {
  flex: 1; /* 左右两侧各占 10% 的宽度 */
  /*background-image: url("https://pic1.imgdb.cn/item/63cb667abe43e0d30e531a95.jpg"); !* 背景图片 URL *!*/
  /*background-size: cover; !* 覆盖整个元素 *!*/
  /*background-position: center; !* 背景图片居中显示 *!*/
}

.middle-content {
  display: flex;
  justify-content: center; /* 水平居中 */
  /*align-items: center; !* 垂直居中 *!*/
  width: 70%; /* 中间区域占 70% 的宽度 */
}
</style>
