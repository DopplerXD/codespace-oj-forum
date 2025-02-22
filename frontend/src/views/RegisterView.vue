<template>
  <div class="registerForm">
    <a-form
      :model="registerUser"
      :style="{ width: '600px' }"
      @submit="userRegister()"
    >
      <a-form-item required field="用户名" tooltip="可用来登录" label="用户名">
        <a-input v-model="registerUser.username" placeholder="请输入用户名" />
      </a-form-item>
      <a-form-item required field="密码" label="密码">
        <a-input v-model="registerUser.password" placeholder="请输入密码" />
      </a-form-item>
      <a-form-item required field="确认密码" label="确认密码">
        <a-input
          v-model="registerUser.checkPassword"
          placeholder="请确认密码"
        />
      </a-form-item>
      <a-form-item field="nickname" tooltip="对外展示的昵称" label="昵称">
        <a-input
          v-model="registerUser.nickname"
          placeholder="请输入昵称（默认同用户名）"
        />
      </a-form-item>
      <a-row justify="center">
        <a-col :span="4">
          <a-form-item>
            <a-button @click="toLogin()">登录</a-button>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item>
            <a-button html-type="submit">注册</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import router from "@/router";

const registerUser = ref({
  username: "",
  password: "",
  checkPassword: "",
  nickname: "",
});

const userRegister = () => {
  console.log("registerUser", registerUser.value);
};

const toLogin = () => {
  router.push({
    path: "/login",
  });
};
</script>

<style scoped>
.registerForm {
  margin-top: 25vh;
}
</style>
