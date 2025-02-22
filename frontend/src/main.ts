import { createApp } from "vue";
import ArcoVue from "@arco-design/web-vue";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import App from "./App.vue";
import "@arco-design/web-vue/dist/arco.css";
import router from "./router";
import store from "./store";
import "@/access";

createApp(App)
  .use(ArcoVue)
  .use(ArcoVueIcon)
  .use(router)
  .use(store)
  .mount("#app");
