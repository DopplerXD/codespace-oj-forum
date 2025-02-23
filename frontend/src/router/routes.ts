import { RouteRecordRaw } from "vue-router";
import ACCESS_ENUM from "@/access/accessEnum";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "首页",
    component: () =>
      import(/* webpackChunkName: "homepage" */ "../views/HomeView.vue"),
  },
  {
    path: "/problem/list",
    name: "题库",
    component: () =>
      import(
        /* webpackChunkName: "problems-list" */ "../views/ProblemListView.vue"
      ),
  },
  {
    path: "/problems",
    name: "题单",
    component: () =>
      import(/* webpackChunkName: "problems" */ "../views/ProblemsView.vue"),
  },
  {
    path: "/blog",
    name: "博客",
    component: () =>
      import(/* webpackChunkName: "blog" */ "../views/BlogView.vue"),
  },
  {
    path: "/about",
    name: "关于",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/login",
    name: "登录",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/LoginView.vue"),
    meta: { hidden: true }, // 不显示在导航栏中
  },
  {
    path: "/register",
    name: "注册",
    component: () =>
      import(/* webpackChunkName: "register" */ "../views/RegisterView.vue"),
    meta: { hidden: true }, // 不显示在导航栏中
  },
  {
    path: "/admin/test",
    name: "管理员测试",
    component: () =>
      import(/* webpackChunkName: "admin-test" */ "../views/HomeView.vue"),
    meta: { access: ACCESS_ENUM.ADMIN },
  },
  {
    path: "/noAuth",
    name: "404",
    component: () =>
      import(/* webpackChunkName: "404" */ "../views/NoAuth.vue"),
    meta: { hidden: true }, // 不显示在导航栏中
  },
  {
    path: "/problem/:pid",
    name: "problemInfo",
    component: () =>
      import(
        /* webpackChunkName: "ProblemInfoView" */ "../views/ProblemInfoView.vue"
      ),
  },
];
