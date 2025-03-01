<template>
  <div id="editForm">
    <a-form
      :model="problem"
      style="width: 1200px"
      auto-label-width
      @submit="createOrUpdateProblem()"
    >
      <a-form-item
        field="problemId"
        label="自定义题目编号"
        :rules="[{ required: true, message: '请输入题目编号' }]"
        :validate-trigger="['change', 'input']"
      >
        <a-input v-model="problem.problemId" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item
        field="title"
        label="题目名称"
        :rules="[{ required: true, message: '请输入标题' }]"
        :validate-trigger="['change', 'input']"
      >
        <a-input v-model="problem.title" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item field="description" label="题目描述">
        <a-textarea v-model="problem.description" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item field="input" label="输入描述">
        <a-textarea v-model="problem.input" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item field="output" label="输出描述">
        <a-textarea v-model="problem.output" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item
        field="difficulty"
        label="难度"
        :rules="[{ required: true, message: '请选择难度' }]"
      >
        <a-radio-group v-model="problem.difficulty">
          <a-radio :value="0">简单</a-radio>
          <a-radio :value="1">普通</a-radio>
          <a-radio :value="2">困难</a-radio>
          <a-radio :value="3">极难</a-radio>
          <a-radio :value="4">未知</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item field="tags" label="标签">
        <a-select
          placeholder="算法"
          multiple
          style="width: 600px"
          :max-tag-count="10"
          allow-clear
          allow-search
          :scrollbar="true"
          v-model="problem.tags"
        >
          <TagOptions />
        </a-select>
      </a-form-item>
      <a-form-item field="hint" label="提示">
        <a-textarea v-model="problem.hint" allow-clear show-word-limit />
      </a-form-item>
      <a-space>
        <a-form-item field="timeLimit" label="时间限制">
          <a-input
            v-model="problem.timeLimit"
            style="width: 120px"
            allow-clear
          />
        </a-form-item>
        <a-form-item field="memoryLimit" label="内存限制">
          <a-input
            v-model="problem.memoryLimit"
            style="width: 120px"
            allow-clear
          />
        </a-form-item>
        <a-form-item field="stackLimit" label="栈限制">
          <a-input
            v-model="problem.stackLimit"
            style="width: 120px"
            allow-clear
          />
        </a-form-item>
      </a-space>
      <a-form-item
        field="isRemote"
        label="是否远程评测"
        :rules="[
          { required: true, message: '请选择是否为远程评测（系统暂不支持）' },
        ]"
        :validate-trigger="['change', 'input']"
      >
        <a-radio-group v-model="problem.isRemote">
          <a-radio :value="0">否</a-radio>
          <a-radio :value="1">是</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item field="source" label="来源">
        <a-input v-model="problem.source" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item field="examples" label="示例">
        <a-textarea v-model="problem.examples" />
      </a-form-item>
      <a-form-item field="ioScore" label="IO 分">
        <a-input v-model="problem.ioScore" allow-clear />
      </a-form-item>
      <a-form-item
        field="codeShare"
        label="是否允许代码共享"
        :rules="[{ required: true, message: '请选择是否允许用户分享提交' }]"
        :validate-trigger="['change', 'input']"
      >
        <a-radio-group v-model="problem.codeShare">
          <a-radio :value="0">否</a-radio>
          <a-radio :value="1">是</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item
        field="auth"
        label="权限"
        :rules="[{ required: true, message: '请选择题目公开范围' }]"
        :validate-trigger="['change', 'input']"
      >
        <a-radio-group v-model="problem.auth">
          <a-radio :value="1">公开</a-radio>
          <a-radio :value="2">仅自己可见</a-radio>
          <a-radio :value="3">比赛题目</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" html-type="submit">提交</a-button>
          <a-button @click="$refs.formRef.resetFields()">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { defineProps, reactive, watch } from "vue";
import TagOptions from "@/components/problem/TagOptions.vue";
import { ProblemControllerService } from "../../../generated";
import router from "@/router";
import { Message } from "@arco-design/web-vue";

let problem = reactive({
  id: 0,
  problemId: "",
  title: "",
  type: 0,
  timeLimit: 0,
  memoryLimit: 0,
  stackLimit: 0,
  description: "",
  input: "",
  output: "",
  examples: "",
  tags: [],
  isRemote: 0,
  source: "",
  difficulty: 3,
  hint: "",
  auth: 1,
  ioScore: 0,
  codeShare: 1,
});

const props = defineProps<{
  problem: any;
  isNewProblem: boolean;
}>();

// 监听 props.problem 的变化
watch(
  () => props.problem,
  (newProblem) => {
    Object.assign(problem, newProblem);
  },
  {
    immediate: true, // 立即执行一次回调，确保初始值也能更新
  }
);

const createOrUpdateProblem = async () => {
  // TODO: 调用后端接口创建或更新题目
  if (props.isNewProblem) {
    // 创建新题目
    const res = await ProblemControllerService.problemCreate(problem);
    if (res.code === 0) {
      // 题目创建成功，跳转到题目列表页面
      Message.success("题目创建成功");
      await router.push({
        path: "/problem/manage",
      });
    } else {
      // 题目创建失败，显示错误信息
      Message.error("题目创建失败：" + res.message);
    }
  } else {
    // 更新题目
    const res = await ProblemControllerService.problemUpdate(problem);
    if (res.code === 0) {
      // 题目更新成功，跳转到题目列表页面
      Message.success("题目更新成功");
      await router.push({
        path: "/problem/manage",
      });
    } else {
      // 题目更新失败，显示错误信息
      Message.error("题目更新失败：" + res.message);
    }
  }
};
</script>

<style scoped>
/*TODO: 修改底部样式*/
#editForm {
  padding: 15px;
}
</style>
