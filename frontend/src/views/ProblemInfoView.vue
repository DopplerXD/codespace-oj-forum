<template>
  <div>
    <h1>{{ problem?.title }}</h1>
    <p>{{ problem?.description }}</p>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { ProblemControllerService } from "../../generated";

const route = useRoute();
const problem = ref<any>();

onMounted(async () => {
  const pid = route.params.pid as string;
  const response = await fetchProblemInfo(pid);
  problem.value = response.data;
  console.log(problem.value);
});

const fetchProblemInfo = async (pid: string) => {
  return ProblemControllerService.problemGet(pid);
};
</script>

<style scoped></style>
