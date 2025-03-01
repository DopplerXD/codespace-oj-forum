<template>
  <div>
    <BasicComponent>
      <ProblemEditForm :problem="problem" :is-new-problem="false" />
    </BasicComponent>
  </div>
</template>

<script lang="ts" setup>
import { useRoute } from "vue-router";
import { onMounted, ref } from "vue";
import { ProblemControllerService } from "../../../generated";
import BasicComponent from "@/components/BasicComponent.vue";
import ProblemEditForm from "@/components/problem/ProblemEditForm.vue";

const route = useRoute();
const problem = ref<any>();

onMounted(async () => {
  const pid = route.params.pid as string;
  const response = await fetchProblemInfo(pid);
  problem.value = response.data;
});

const fetchProblemInfo = async (pid: string) => {
  return ProblemControllerService.problemGetDetail(pid);
};
</script>

<style scoped></style>
