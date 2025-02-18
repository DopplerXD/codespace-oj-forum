<template>
  <div class="menu">
    <div id="menu-left">
      <a-menu
        mode="horizontal"
        :selected-keys="selectedKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div
            :style="{
              width: '80px',
              height: '30px',
              borderRadius: '2px',
              background: 'var(--color-fill-3)',
              cursor: 'text',
            }"
          />
        </a-menu-item>
        <!--        <a-menu-item key="1">首页</a-menu-item>-->
        <!--        <a-menu-item key="2">题单</a-menu-item>-->
        <!--        <a-menu-item key="3">题库</a-menu-item>-->
        <!--        <a-menu-item key="4">比赛</a-menu-item>-->
        <!--        <a-menu-item key="5">讨论区</a-menu-item>-->
        <a-menu-item v-for="item in routes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </div>
    <div id="menu-right">
      <a-menu mode="horizontal">
        <a-menu-item disabled>
          <a-button>
            <icon-github />
          </a-button>
        </a-menu-item>
        <a-menu-item disabled>
          <a-button>test</a-button>
        </a-menu-item>
        <a-menu-item disabled>
          <a-button>test</a-button>
        </a-menu-item>
        <a-menu-item disabled>
          <a-button>test</a-button>
        </a-menu-item>
      </a-menu>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { ref } from "vue";

const router = useRouter();

// 默认主页
const selectedKeys = ref(["/"]);

// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

const doMenuClick = (e: string) => {
  router.push({
    path: e,
  });
};
</script>

<style scoped>
.menu {
  box-sizing: border-box;
  padding-top: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#menu-left {
  padding-left: 50px;
  flex: 1;
}

#menu-right {
  padding-left: 20px;
  padding-right: 50px;
  text-align: right;
  flex: 1;
}
</style>
