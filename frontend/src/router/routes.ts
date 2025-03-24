import { RouteRecordRaw } from "vue-router";
import ACCESS_ENUM from "@/access/accessEnum";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "首页",
    component: () =>
      import(/* webpackChunkName: "homepage" */ "../views/public/HomeView.vue"),
  },
  {
    path: "/problem/list",
    name: "题库",
    component: () =>
      import(
        /* webpackChunkName: "problems-list" */ "../views/problem/ProblemListView.vue"
      ),
  },
  {
    path: "/blog",
    name: "博客",
    component: () =>
      import(/* webpackChunkName: "blog" */ "../views/blog/BlogView.vue"),
    meta: { hidden: true },
  },
  {
    path: "/record/list",
    name: "评测列表",
    component: () =>
      import(
        /* webpackChunkName: "JudgeListView" */ "../views/judge/JudgeListView.vue"
      ),
  },
  {
    path: "/about",
    name: "关于",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/public/AboutView.vue"),
  },
  {
    path: "/login",
    name: "登录",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/user/LoginView.vue"),
    meta: { hidden: true }, // 不显示在导航栏中
  },
  {
    path: "/register",
    name: "注册",
    component: () =>
      import(
        /* webpackChunkName: "register" */ "../views/user/RegisterView.vue"
      ),
    meta: { hidden: true }, // 不显示在导航栏中
  },
  {
    path: "/problem/manage",
    name: "管理题目",
    component: () =>
      import(
        /* webpackChunkName: "ProblemManageView" */ "../views/problem/ProblemManageView.vue"
      ),
    meta: { access: ACCESS_ENUM.USER },
  },
  {
    path: "/admin/test",
    name: "管理员测试",
    component: () =>
      import(
        /* webpackChunkName: "admin-test" */ "../views/public/HomeView.vue"
      ),
    meta: { access: ACCESS_ENUM.ADMIN },
  },
  {
    path: "/noAuth",
    name: "404",
    component: () =>
      import(/* webpackChunkName: "404" */ "../views/public/NoAuth.vue"),
    meta: { hidden: true }, // 不显示在导航栏中
  },
  {
    path: "/problem/:pid",
    name: "题目详情",
    component: () =>
      import(
        /* webpackChunkName: "ProblemInfoView" */ "../views/problem/ProblemInfoView.vue"
      ),
    meta: { hidden: true },
  },
  {
    path: "/problem/create",
    name: "创建题目",
    component: () =>
      import(
        /* webpackChunkName: "ProblemInfoView" */ "../views/problem/ProblemCreateView.vue"
      ),
    meta: { hidden: true },
  },
  {
    path: "/problem/update/:pid",
    name: "更新题目",
    component: () =>
      import(
        /* webpackChunkName: "ProblemInfoView" */ "../views/problem/ProblemUpdateView.vue"
      ),
    meta: { hidden: true },
  },
  {
    path: "/record/:submitId",
    name: "评测记录",
    component: () =>
      import(
        /* webpackChunkName: "JudgeInfoView" */ "../views/judge/JudgeInfoView.vue"
      ),
    meta: { hidden: true },
  },
];
