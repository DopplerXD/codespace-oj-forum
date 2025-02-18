import { createApp } from "vue";
import ArcoVue from "@arco-design/web-vue";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import App from "./App.vue";
import "@arco-design/web-vue/dist/arco.css";
import router from "./router";

createApp(App).use(ArcoVue).use(ArcoVueIcon).use(router).mount("#app");

//main.ts
// eslint-disable-next-line @typescript-eslint/ban-types
const debounce = (fn: Function, delay: number) => {
  let timer: any;
  return (...args: any) => {
    if (timer) {
      clearTimeout(timer);
    }
    timer = setTimeout(() => {
      fn(...args);
    }, delay);
  };
};
const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback: ResizeObserverCallback) {
    super(debounce(callback, 10));
  }
};
