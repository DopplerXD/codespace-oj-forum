<template>
  <div id="view">
    <BasicComponent>
      <div id="select-bar">
        <span>题目ID</span>
        <a-input-search
          style="width: 320px; margin-inline: 10px"
          placeholder="题目ID"
          v-model="problemId"
          :press-enter="conditionalSearch()"
        />
        <span>用户名</span>
        <a-input-search
          style="width: 320px; margin-inline: 10px"
          placeholder="用户名"
          v-model="username"
          :press-enter="conditionalSearch()"
        />
        <span>语言</span>
        <a-select
          style="width: 100px; margin-inline: 10px"
          placeholder="全部"
          v-model="language"
          :options="languageOptions"
          :field-names="languageFieldNames"
          allow-clear
        >
        </a-select>
        <span>评测状态</span>
        <a-select
          style="width: 100px; margin-inline: 10px"
          placeholder="全部"
          v-model="status"
          :options="statusOptions"
          :field-names="statusFieldNames"
          allow-clear
        >
        </a-select>
      </div>
    </BasicComponent>
    <BasicComponent>
      <div id="data-table">
        <a-table
          :columns="columns"
          :data="judgeList"
          :filter-icon-align-left="alignLeft"
          :pagination="false"
          column-resizable
          style="margin-top: 20px"
        >
          <template #submitId="{ record }">
            <a-link @click="judgeGet(record.submitId)"
              >{{ record.submitId }}
            </a-link>
          </template>
          <template #result="{ record }">
            {{ getResult(record.status) }}
          </template>
          <template #submitTime="{ record }">
            {{ getSubmitTime(record.submitTime) }}
          </template>
        </a-table>
        <a-pagination
          :total="totalItems"
          :current="currentPage"
          show-jumper
          show-total
          @change="handlePageChange()"
          style="margin-top: 16px"
        />
      </div>
    </BasicComponent>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { ProblemControllerService } from "../../../generated";
import BasicComponent from "@/components/BasicComponent.vue";
import router from "@/router";

// 定义响应式数据
const judgeList = ref<any[]>([]);
const totalItems = ref<number>(0);
const currentPage = ref<number>(1);
const alignLeft = ref(true);

// 定义搜索条件
const problemId = ref<number>();
const username = ref<string>();
const language = ref<string>();
const status = ref<number>();

// 定义难度选项
const languageFieldNames = { value: "value", label: "text" };
const languageOptions = reactive([
  {
    value: "java",
    text: "Java",
  },
  {
    value: "cpp",
    text: "C++",
  },
  {
    value: "python",
    text: "Python",
  },
  {
    value: "go",
    text: "Golang",
  },
  {
    value: "javascript",
    text: "JavaScript",
  },
]);

// 定义测评状态选项
const statusFieldNames = { value: "value", label: "text" };
const statusOptions = reactive([
  {
    value: 0,
    text: "AC",
  },
  {
    value: -1,
    text: "WA",
  },
  {
    value: 7,
    text: "评测中",
  },
  {
    value: 9,
    text: "提交中",
  },
  {
    value: 1,
    text: "时间超限",
  },
  {
    value: 3,
    text: "空间超限",
  },
  {
    value: -2,
    text: "编译错误",
  },
  {
    value: 4,
    text: "运行错误",
  },
  {
    value: 5,
    text: "系统错误",
  },
]);

// 定义表格列
const columns = [
  {
    title: "提交ID",
    slotName: "submitId",
    width: 200,
  },
  {
    title: "题目 ID",
    dataIndex: "pid",
    key: "problemId",
    width: 130,
  },
  {
    title: "提交者",
    dataIndex: "username",
    key: "username",
    width: 110,
  },
  {
    title: "评测状态",
    slotName: "result",
    width: 100,
  },
  {
    title: "语言",
    dataIndex: "language",
    key: "language",
    width: 100,
  },
  {
    title: "运行时间",
    dataIndex: "time",
    key: "time",
    width: 105,
    sortable: {
      sortDirections: ["ascend", "descend"],
    },
  },
  {
    title: "运行空间",
    dataIndex: "memory",
    key: "memory",
    width: 105,
    sortable: {
      sortDirections: ["ascend", "descend"],
    },
  },
  {
    title: "提交时间",
    slotName: "submitTime",
    // width: 140,
  },
];

// 挂载时获取第一页数据
onMounted(() => {
  conditionalSearch();
});

// 处理表格变化（排序、筛选、分页）的函数
const handleTableChange = (pagination: any, filters: any, sorter: any) => {
  conditionalSearch();
};

// 处理分页器页码变化的函数
const handlePageChange = () => {
  conditionalSearch();
};

const getResult = (status: number) => {
  switch (status) {
    case 0:
      return "AC";
    case -1:
      return "WA";
    case 7:
      return "评测中";
    case 9:
      return "提交中";
    case 1:
      return "时间超限";
    case 3:
      return "空间超限";
    case -2:
      return "编译错误";
    case 4:
      return "运行错误";
    case 5:
      return "系统错误";
  }
};

const judgeGet = (submitId: string) => {
  router.push({
    path: "/record/" + submitId,
  });
};

const getSubmitTime = (dateTimeStr: string) => {
  const date = new Date(dateTimeStr);
  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    hour12: false,
  };
  const formatter = new Intl.DateTimeFormat("zh-CN", options);
  return formatter.format(date).replace(/\//g, "-");
};

const conditionalSearch = async () => {
  try {
    const res = await ProblemControllerService.judgeGetList(
      currentPage.value,
      problemId.value,
      username.value,
      status.value,
      language.value
    );
    if (res.code === 0) {
      judgeList.value = res.data?.judges || [];
      totalItems.value = res.data?.total || 0;
    } else {
      console.error("获取评测结果列表失败:", res.message);
    }
  } catch (error) {
    console.error("请求出错:", error);
  }
};
</script>

<style scoped>
.table-column-status {
  display: flex;
  align-items: center;
  justify-content: center;
}

#view {
  display: flex;
  flex-direction: column;
}

#select-bar {
  padding: 15px;
}

#data-table {
  padding: 0 15px 10px 15px;
  min-height: calc(100vh - 300px);
}
</style>
