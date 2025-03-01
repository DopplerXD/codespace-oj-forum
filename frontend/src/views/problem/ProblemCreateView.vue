<template>
  <div>
    <BasicComponent>
      <ProblemEditForm problem="" :is-new-problem="true" />
    </BasicComponent>
  </div>
</template>

<script lang="ts" setup>
import BasicComponent from "@/components/BasicComponent.vue";
import ProblemEditForm from "@/components/problem/ProblemEditForm.vue";
</script>

<style scoped></style>
