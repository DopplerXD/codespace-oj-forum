import { createApp } from "vue";
import ArcoVue from "@arco-design/web-vue";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import App from "./App.vue";
import "@arco-design/web-vue/dist/arco.css";
import router from "./router";
import store from "./store";
import "@/access";
import instance from "@/plugins/axios";

const app = createApp(App);

// 全局导入 Message 组件
// Message._context = app._context;

// 在 Vue 3 中使用 app.config.globalProperties 挂载全局属性
app.config.globalProperties.$http = instance;

app.use(ArcoVue).use(ArcoVueIcon).use(router).use(store).mount("#app");
