<template>
  <div class="registerForm">
    <a-form
      :model="registerUser"
      :style="{ width: '600px' }"
      @submit="userRegister()"
      clear
    >
      <a-form-item field="用户名" tooltip="可用来登录" label="用户名">
        <a-input
          v-model="registerUser.username"
          placeholder="请输入用户名"
          allow-clear
        />
      </a-form-item>
      <a-form-item field="密码" label="密码">
        <a-input-password
          v-model:visibility="visibility"
          v-model="registerUser.password"
          placeholder="请输入密码"
          allow-clear
        />
      </a-form-item>
      <a-form-item field="确认密码" label="确认密码">
        <a-input-password
          v-model:visibility="visibility"
          v-model="registerUser.checkPassword"
          placeholder="请确认密码"
          allow-clear
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
import { UserControllerService } from "../../../generated";
import { Message } from "@arco-design/web-vue";

const visibility = ref(true);

const registerUser = ref({
  username: "",
  password: "",
  checkPassword: "",
});

const userRegister = async () => {
  const res = await UserControllerService.userRegister(registerUser.value);
  if (res.code === 0) {
    // 注册成功，跳转到登录页
    Message.success("注册成功\n正在跳转到登录页...");
    setTimeout(() => {
      toLogin();
    }, 1000);
  } else {
    Message.error(res.message);
  }
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
