<template>
  <div>
    <a-tag :color="getDifficultyColor(props.difficulty)">
      {{ getDifficultyText(props.difficulty) }}
    </a-tag>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";

const props = defineProps<{
  difficulty: number;
}>();

const getDifficultyText = (difficulty: number) => {
  switch (difficulty) {
    case 0:
      return "简单";
    case 1:
      return "中等";
    case 2:
      return "困难";
    case 3:
      return "极难";
    default:
      return "未知";
  }
};

const getDifficultyColor = (difficulty: number) => {
  switch (difficulty) {
    case 0:
      return "#f5222d";
    case 1:
      return "#f39c11";
    case 2:
      return "#52c41a";
    case 3:
      return "#9d3dcf";
    default:
      return "#0e1d69";
  }
};
</script>

<style scoped></style>
