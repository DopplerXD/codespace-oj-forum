import axios from "axios";
import router from "@/router";

const instance = axios.create({
  baseURL: "http://localhost:8081", // 设置你的API基础URL
  timeout: 5000, // 设置请求超时时间
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("token"); // 假设你的access token存储在localStorage中
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`; // 设置请求头中的Authorization字段
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      // 假设401表示access token过期
      // 刷新access token的逻辑
      return instance
        .get("/refresh-token")
        .then((response) => {
          const newAccessToken = response.data.accessToken;
          localStorage.setItem("accessToken", newAccessToken); // 更新access token
          error.config.headers["Authorization"] = `Bearer ${newAccessToken}`; // 更新请求头中的Authorization字段
          return instance(error.config); // 重新发送原来的请求
        })
        .catch((error) => {
          // 处理刷新access token失败的情况
          // 例如，跳转到登录页面
          router.push("/user/login");
          return Promise.reject(error);
        });
    }
    return Promise.reject(error);
  }
);

export default instance;
