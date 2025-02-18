<template>
  <div id="app">
    <BasicLayout />
  </div>
</template>

<style></style>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { onMounted } from "vue";

onMounted(() => {
  document.title = "Code Space";
});
</script>
