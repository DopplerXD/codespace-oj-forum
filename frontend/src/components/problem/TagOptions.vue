<template>
  <div>
    <a-option v-for="tag in tags" :key="tag">{{ tag }}</a-option>
  </div>
</template>

<script setup lang="ts">
const tags = [
  "双指针",
  "数论",
  "字符串",
  "动态规划",
  "贪心",
  "二分查找",
  "排序",
  "哈希表",
  "前缀和",
  "树",
  "图论",
  "回溯",
  "分治",
  "位运算",
  "堆",
  "栈",
  "队列",
  "数组",
  "链表",
  "矩阵",
  "模拟",
  "搜索",
  "递归",
  "数据结构",
];
</script>

<style scoped></style>
