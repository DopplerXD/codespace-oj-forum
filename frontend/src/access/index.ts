import router from "@/router";
import store from "@/store";
import ACCESS_ENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";

router.beforeEach(async (to, from, next) => {
  // 在应用启动时检查 localStorage 中的 token
  const token = localStorage.getItem("token");
  if (token) {
    // 将 token 存储到 Vuex 中
    store.commit("user/updateUser", { token });
    // 尝试获取登录用户信息
    store
      .dispatch("user/getLoginUser")
      .then(() => {
        // 获取用户信息成功
      })
      .catch(() => {
        // 获取用户信息失败，清除 token
        localStorage.removeItem("token");
        store.commit("user/updateUser", {
          nickname: "未登录",
          avatar: "",
          role: ACCESS_ENUM.NOT_LOGIN,
          token: "",
        });
      });
  }
  console.log("登陆用户信息", store.state.user.loginUser);
  const loginUser = store.state.user.loginUser;
  // // 如果之前没登陆过，自动登录
  if (!loginUser || !loginUser.userRole) {
    // 加 await 是为了等用户登录成功之后，再执行后续的代码
    await store.dispatch("user/getLoginUser");
  }
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN;
  // 要跳转的页面必须要登陆
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // 如果没登陆，跳转到登录页面
    if (!loginUser || !loginUser.userRole) {
      next(`/user/login?redirect=${to.fullPath}`);
      return;
    }
    // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
