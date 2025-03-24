<template>
  <div class="outer">
    <div class="box">
      <div class="inner">
        <h2>评测结果</h2>
        <a-alert type="success" v-if="judge?.score === 1"> Accepted </a-alert>
        <a-alert type="info" v-else-if="judge?.status === 3">
          Time Limit Exceeded Or Memory Limit Exceeded
        </a-alert>
        <a-alert type="warning" v-else-if="judge?.score === 2">
          Compile Error
        </a-alert>
        <a-alert type="error" v-else> Worng Answer </a-alert>
      </div>
      <div class="inner">
        <h2>代码</h2>
        <MdPreview :id="id" :modelValue="code" />
        <MdCatalog :editorId="id" :scrollElement="scrollElement" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { ProblemControllerService } from "../../../generated";
import { useRoute } from "vue-router";
import { MdCatalog, MdPreview } from "md-editor-v3";

const route = useRoute();
const judge = ref<any>();

const id = "preview-only";
const code = ref("");
const scrollElement = document.documentElement;

onMounted(async () => {
  const submitId = route.params.submitId as number;
  const response = await fetchJudgeInfo(submitId);
  judge.value = response.data;
  code.value = "```java\n" + judge.value.code + "\n```";
});

const fetchJudgeInfo = async (submitId: number) => {
  return ProblemControllerService.judgeGet(submitId);
};
</script>

<style scoped>
.outer {
  display: flex;
  flex-direction: row;
  min-height: 100%;
  width: 100%;
}

.box {
  background-color: white;
  border-radius: 5px;
  flex: 1;
  padding: 10px;
  margin: 10px;
}

.inner {
  padding: 10px;
}
</style>
