<template>
  <div id="layout">
    <div id="info-and-submit">
      <BasicComponent>
        <div class="inner">
          <h2>{{ problem?.problemId }}</h2>
          <h1>{{ problem?.title }}</h1>
          <a-button type="primary" @click="submitJudge()">提交代码</a-button>
        </div>
      </BasicComponent>
    </div>
    <div id="description-and-editor">
      <a-split style="height: 100%; width: 100%" :min="0.2" :max="0.8">
        <template #first>
          <div id="outer">
            <div class="box" id="left">
              <div class="inner">
                <h1>题目描述</h1>
                <MdPreview :id="id" :modelValue="description" />
                <MdCatalog :editorId="id" :scrollElement="scrollElement" />
              </div>
              <div class="inner">
                <h1>输入格式</h1>
                <MdPreview :id="id" :modelValue="input" />
              </div>
              <div class="inner">
                <h1>输出格式</h1>
                <MdPreview :id="id" :modelValue="output" />
              </div>
            </div>
          </div>
        </template>
        <template #second>
          <div id="outer">
            <div class="box" id="right">
              <div class="inner" id="language-selector">
                <span>语言选择：</span>
                <a-select
                  v-model="highlightLanguage"
                  style="width: 140px"
                  default-value="cpp"
                  @change="changeLanguage"
                >
                  <a-option
                    v-for="item of languages"
                    :key="item.value"
                    :value="item.value"
                    :label="item.label"
                  />
                </a-select>
              </div>
              <Codemirror
                v-model:value="code"
                :options="cmOptions"
                height="calc(100% - 50px)"
                ref="cmRef"
              />
            </div>
          </div>
        </template>
      </a-split>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { ProblemControllerService } from "../../../generated";
import { MdPreview, MdCatalog } from "md-editor-v3";
import "md-editor-v3/lib/preview.css";

import "codemirror/mode/clike/clike.js";
import "codemirror/mode/javascript/javascript.js";
import "codemirror/mode/python/python.js";
import Codemirror from "codemirror-editor-vue3";
import type { CmComponentRef } from "codemirror-editor-vue3";
import type { Editor, EditorConfiguration } from "codemirror";
import BasicComponent from "@/components/BasicComponent.vue";

const route = useRoute();
const problem = ref<any>();

const id = "preview-only";
const description = ref("");
const input = ref("");
const output = ref("");
const hint = ref("");
const scrollElement = document.documentElement;

const code = ref("");
const cmRef = ref<CmComponentRef>();
const cmOptions: EditorConfiguration = {
  mode: "text/x-java",
};
const highlightLanguage = ref("cpp");

const languages = [
  { label: "C++", value: "cpp" },
  { label: "Java", value: "java" },
  { label: "Python", value: "python" },
  { label: "Golang", value: "go" },
  { label: "JavaScript", value: "javascript" },
];

onMounted(async () => {
  const pid = route.params.pid as string;
  const response = await fetchProblemInfo(pid);
  problem.value = response.data;
  description.value = response.data?.description;
  input.value = response.data?.input;
  output.value = response.data?.output;
  hint.value = response.data?.hint;

  // 初始化 Codemirror 的 mode
  changeLanguage();
});

const fetchProblemInfo = async (pid: string) => {
  return ProblemControllerService.problemGet(pid);
};

const submitJudge = () => {
  console.log("submitJudge, id: " + problem.value.id);
  // TODO: 提交代码
  const judgeId = ProblemControllerService.judgeSubmit({
    code: code.value,
    language: highlightLanguage.value,
    pid: problem.value.id,
    share: 1,
  });
  console.log("submitJudge: " + problem.value.id + ", " + judgeId);
};

const changeLanguage = () => {
  let mode = "text/x-java"; // 默认模式
  switch (highlightLanguage.value) {
    case "cpp":
      mode = "text/x-c++src";
      break;
    case "java":
      mode = "text/x-java";
      break;
    case "python":
      mode = "text/x-python";
      break;
    case "go":
      mode = "text/x-go";
      break;
    case "javascript":
      mode = "text/javascript";
      break;
    default:
      mode = "text/x-c++src";
  }

  // 更新 cmOptions.mode
  cmOptions.mode = mode;

  // 如果 Codemirror 实例已初始化，则设置新的 mode
  if (cmRef.value?.cminstance) {
    cmRef.value.cminstance.setOption("mode", mode);
  }
};
</script>

<style scoped>
#layout {
  display: flex;
  flex-direction: column;
  width: 100%;
}

#info-and-submit {
  /*height: 130px;*/
  margin: 0 10px 20px 10px;
  display: flex;
  align-items: center;
}

#description-and-editor {
  flex: 1; /* 填满剩余空间 */
  overflow: hidden; /* 防止内容溢出 */
}

.inner {
  padding: 10px;
}

#outer {
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

#left {
  width: 48%;
  box-sizing: border-box;
  margin-right: 5px;
}

#right {
  width: 48%;
  box-sizing: border-box;
  margin-left: 5px;
}

#language-selector {
  display: flex;
  align-items: center;
}
</style>
