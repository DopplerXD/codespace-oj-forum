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
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </div>
    <div id="menu-right">
      <a-menu mode="horizontal">
        <a-link href="https://github.com/DopplerXD/codespace-oj-forum">
          <icon-github />
        </a-link>
        <a-menu-item disabled v-if="loginUser.role === ACCESS_ENUM.NOT_LOGIN">
          <a-button @click="loginOrRegister()">登录/注册</a-button>
        </a-menu-item>
        <a-menu-item disabled v-else>
          <a-avatar>
            <img alt="avatar" :src="loginUserAvatar" />
          </a-avatar>
          {{ loginUserNickname }}
          <a-dropdown>
            <a-button>
              <icon-down />
            </a-button>
            <template #content>
              <a-doption>个人信息</a-doption>
              <a-doption @click="userLogout()">登出</a-doption>
            </template>
          </a-dropdown>
        </a-menu-item>
      </a-menu>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ref, computed } from "vue";
import checkAccess from "@/access/checkAccess";
import ACCESS_ENUM from "@/access/accessEnum";

const router = useRouter();
const store = useStore();

// 使用 computed 监听 store.state.user.loginUser 的变化
const loginUser = computed(() => store.state.user.loginUser);
const loginUserNickname = computed(() => loginUser.value.nickname);
const loginUserAvatar = computed(() => loginUser.value.avatar);

// 默认主页
const selectedKeys = ref(["/"]);

// 过滤出不需要隐藏的路由
const visibleRoutes = computed(() =>
  routes.filter(
    (item) =>
      !(item.meta && item.meta.hidden) &&
      checkAccess(loginUser.value, item?.meta?.access as string)
  )
);

// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

const doMenuClick = (e: string) => {
  router.push({
    path: e,
  });
};

const loginOrRegister = () => {
  router.push({
    path: "/login",
  });
};

const userLogout = () => {
  localStorage.removeItem("token");
  store.commit("user/updateUser", {
    nickname: "",
    role: ACCESS_ENUM.NOT_LOGIN,
    avatar: "",
    token: "",
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
