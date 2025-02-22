<template>
  <div id="app">
    <BasicLayout />
  </div>
</template>

<style></style>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { onMounted } from "vue";

const doInit = () => {
  console.log("project init");
};
onMounted(() => {
  document.title = "Code Space";
  doInit();
});
</script>
