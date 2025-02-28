<template>
  <div class="loginForm">
    <a-form
      :model="loginUser"
      :style="{ width: '600px' }"
      @submit="userLogin()"
    >
      <a-form-item field="用户名" label="用户名">
        <a-input
          v-model="loginUser.username"
          placeholder="请输入用户名"
          allow-clear
        />
      </a-form-item>
      <a-form-item field="密码" label="密码">
        <a-input-password
          v-model:visibility="visibility"
          v-model="loginUser.password"
          placeholder="请输入密码"
          allow-clear
        />
      </a-form-item>
      <a-form-item field="isRead">
        <a-checkbox v-model="loginUser.autoLogin"> 7 天内自动登录</a-checkbox>
      </a-form-item>
      <a-row justify="center">
        <a-col :span="4">
          <a-form-item>
            <a-button html-type="submit">登录</a-button>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item>
            <a-button @click="toRegister()">注册</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { UserControllerService } from "../../../generated";
import store from "@/store";

const router = useRouter();
const route = useRoute();

const visibility = ref(true);

const loginUser = ref({
  username: "",
  password: "",
  autoLogin: false,
});

const userLogin = async () => {
  const res = await UserControllerService.userLogin(loginUser.value);
  if (res.code === 0) {
    await store.commit("user/updateUser", {
      username: res.data?.loginUserVO.username,
      role: res.data?.loginUserVO.role,
      avatar: res.data?.loginUserVO.avatar,
      token: res.data?.token,
    });
    console.log(store.state.user.loginUser);
    // 将 token 存储到 localStorage 中
    localStorage.setItem("token", store.state.user.loginUser.token);
    // 登录成功，跳转到首页
    await router.push({
      path: "/",
    });
  }
};

const toRegister = () => {
  router.push({
    path: "/register",
  });
};
</script>

<style scoped>
.loginForm {
  margin-top: 25vh;
}
</style>
