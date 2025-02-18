<template>
  <a-layout>
    <a-layout-header><NavBar /></a-layout-header>
    <a-layout-content><router-view /></a-layout-content>
    <a-layout-footer>Footer</a-layout-footer>
  </a-layout>
</template>

<script lang="ts" setup>
import NavBar from "@/components/NavBar.vue";
</script>

<style scoped></style>
