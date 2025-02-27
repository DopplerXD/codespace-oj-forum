<template>
  <div id="view">
    <BasicComponent>
      <div id="select-bar">
        <span>标签</span>
        <a-select
          placeholder="算法"
          multiple
          style="width: 300px; margin-inline: 10px"
          :max-tag-count="3"
          allow-clear
          allow-search
          :scrollbar="true"
          v-model="tags"
        >
          <a-option>双指针</a-option>
          <a-option>数论</a-option>
          <a-option>字符串</a-option>
        </a-select>
        <span>关键词</span>
        <a-input-search
          style="width: 320px; margin-inline: 10px"
          placeholder="算法、标题或题目编号"
          v-model="keyword"
          :press-enter="searchByKeywords(keyword)"
        />
      </div>
    </BasicComponent>
    <BasicComponent>
      <div id="data-table">
        <a-table
          :columns="columns"
          :data="problemList"
          :filter-icon-align-left="alignLeft"
          :pagination="false"
          column-resizable
          style="margin-top: 20px"
        >
          <template #status="{ record }">
            <div class="table-column-status">
              <icon-check v-if="record.status === 1" />
              <icon-close v-else-if="record.status === -1" />
              <icon-minus v-else />
            </div>
          </template>
          <template #title="{ record }">
            <a-link @click="problemGet(record.problemId)"
              >{{ record.title }}
            </a-link>
          </template>
          <template #acceptedRate="{ record }">
            <AcceptedRateBar
              :submit-num="record.submitNum"
              :accepted-num="record.acceptedNum"
            />
          </template>
          <template #tags="{ record }">
            <ProblemTags :tag="getProblemTags(record.tags)" />
          </template>
          <template #difficulty="{ record }">
            <ProblemDifficultyTag :difficulty="record.difficulty" />
          </template>
        </a-table>
        <a-pagination
          :total="totalItems"
          :current="currentPage"
          show-jumper
          show-total
          @change="handlePageChange(currentPage)"
          style="margin-top: 16px"
        />
      </div>
    </BasicComponent>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { ProblemControllerService } from "../../../generated";
import router from "@/router";
import AcceptedRateBar from "@/components/problem/AcceptedRateBar.vue";
import ProblemTags from "@/components/problem/ProblemTags.vue";
import ProblemDifficultyTag from "@/components/problem/ProblemDifficultyTag.vue";
import BasicComponent from "@/components/BasicComponent.vue";

// 定义响应式数据
const problemList = ref<any[]>([]);
const totalItems = ref<number>(0);
const currentPage = ref<number>(1);
const alignLeft = ref(true);

// 定义搜索条件
const tags = ref([]);
const keyword = ref("");

// 定义表格列
const columns = [
  {
    title: "状态",
    slotName: "status",
    width: 60,
  },
  {
    title: "题目 ID",
    dataIndex: "problemId",
    key: "problemId",
    width: 130,
    sortable: {
      sortDirections: ["ascend", "descend"],
    },
  },
  {
    title: "题目标题",
    slotName: "title",
    width: 500,
    sortable: {
      sortDirections: ["ascend", "descend"],
    },
  },
  {
    title: "标签",
    slotName: "tags",
    width: 160,
  },
  {
    title: "难度",
    slotName: "difficulty",
    width: 80,
    sortable: {
      sortDirections: ["ascend", "descend"],
    },
  },
  {
    title: "通过率",
    slotName: "acceptedRate",
    width: 120,
    sortable: {
      sortDirections: ["ascend", "descend"],
    },
  },
];

// 挂载时获取第一页数据
onMounted(() => {
  getProblemList(currentPage.value);
});

const getProblemTags = (tagsJson: string) => {
  try {
    // console.log(tagsJson);
    // 类型断言确保解析后的数组元素是字符串
    return JSON.parse(tagsJson) as string[];
  } catch (error) {
    console.error("Invalid JSON format:", error);
    return [];
  }
};

// 获取题目列表的函数
const getProblemList = async (page: number) => {
  try {
    const res = await ProblemControllerService.problemGetList(page);
    // console.log(res);
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

const conditionalSearch = (
  difficulty: number,
  tags: string[],
  keyword: string
) => {
  // TODO: 实现根据难度、标签和关键词搜索的逻辑
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
