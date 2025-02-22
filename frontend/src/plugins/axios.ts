// Add a request interceptor
import axios from "axios";
import store from "@/store";

// 创建一个 Axios 实例
const instance = axios.create({
  baseURL: "http://localhost:8081",
});

// 添加请求拦截器
axios.interceptors.response.use(
  (config) => {
    const token = store.state.user.loginUser.token;
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

export default instance;
