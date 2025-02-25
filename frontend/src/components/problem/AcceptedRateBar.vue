<template>
  <a-tooltip :content="percentageText" mini>
    <div class="progress-bar-container">
      <div class="progress-bar" :style="{ width: `${percentageValue}%` }"></div>
    </div>
  </a-tooltip>
</template>

<script setup lang="ts">
import { computed, defineProps } from "vue";

const props = defineProps<{
  submitNum: number;
  acceptedNum: number;
}>();

const percentageValue = computed(() => {
  if (props.submitNum === 0) return 0;
  return Math.round((props.acceptedNum / props.submitNum) * 10000) / 100;
});

const percentageText = computed(() => {
  if (props.submitNum === 0) return `0/0, 0%`;
  return `${props.acceptedNum}/${props.submitNum}, ${percentageValue.value}%`;
});
</script>

<style scoped>
.progress-bar-container {
  position: relative;
  width: 100%;
  height: 16px;
  background-color: #f5f5f5; /* 灰色背景 */
  border-radius: 3px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background-color: #1890ff; /* 蓝色进度条 */
  transition: width 0.3s ease;
}

.percentage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #1890ff;
  font-size: 12px;
  white-space: nowrap;
}
</style>
