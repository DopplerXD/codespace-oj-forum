<template>
  <div>
    <a-tag v-for="tag in props.tag" :key="tag" color="#2949b4"
      >{{ tag }}
    </a-tag>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";

const props = defineProps<{
  tag: string[];
}>();
</script>

<style scoped></style>
