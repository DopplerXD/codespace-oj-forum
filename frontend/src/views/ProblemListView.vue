<template>
  <div>
    <a-table
      :columns="columns"
      :data="problemList"
      :filter-icon-align-left="alignLeft"
      :pagination="false"
      column-resizable
      style="margin-top: 20px"
    >
      <template #optional="{ record }">
        <a-link @click="problemGet(record.problemId)">{{ record.title }}</a-link>
      </template>
    </a-table>
    <a-pagination
      :total="totalItems"
      :current="currentPage"
      show-jumper
      @change="handlePageChange(currentPage)"
      style="margin-top: 16px"
    />
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { ProblemControllerService } from "../../generated";
import router from "@/router";

// 定义响应式数据
const problemList = ref<any[]>([]);
const totalItems = ref<number>(0);
const currentPage = ref<number>(1);
const alignLeft = ref(true);

// 定义表格列
const columns = [
  {
    title: "题目 ID",
    dataIndex: "problemId",
    key: "problemId",
    width: 150,
  },
  {
    title: "题目标题",
    slotName: "optional",
    width: 300,
  },
  {
    title: "点赞数",
    dataIndex: "favourNum",
    key: "favourNum",
    width: 80,
  },
  {
    title: "提交数",
    dataIndex: "submitNum",
    key: "submitNum",
    width: 80,
  },
  {
    title: "通过数",
    dataIndex: "acceptedNum",
    key: "acceptedNum",
    width: 80,
  },
  {
    title: "标签",
    dataIndex: "tags",
    key: "tags",
    width: 200,
    customRender: (text: string) => {
      try {
        const tagArray = JSON.parse(text);
        return tagArray.join(", ");
      } catch (error) {
        return text;
      }
    },
  },
  {
    title: "难度",
    dataIndex: "difficulty",
    key: "difficulty",
    width: 80,
  },
];

// 挂载时获取第一页数据
onMounted(() => {
  getProblemList(currentPage.value);
});

// 获取题目列表的函数
const getProblemList = async (page: number) => {
  try {
    const res = await ProblemControllerService.problemGetList(page);
    console.log(res);
    if (res.code === 0) {
      problemList.value = res.data?.problems || [];
      totalItems.value = res.data?.total || 0;
      currentPage.value = page;
    } else {
      console.error("获取题目列表失败:", res.message);
    }
  } catch (error) {
    console.error("请求出错:", error);
  }
};

// 处理表格变化（排序、筛选、分页）的函数
const handleTableChange = (pagination: any, filters: any, sorter: any) => {
  const newPage = pagination.current;
  getProblemList(newPage);
};

// 处理分页器页码变化的函数
const handlePageChange = (page: number) => {
  getProblemList(page);
};

const problemGet = (problemId: string) => {
  router.push({
    path: "/problem/" + problemId,
  });
};
</script>

<style scoped></style>
