import { StoreOptions } from "vuex";
import ACCESS_ENUM from "@/access/accessEnum";
import { UserControllerService } from "../../generated";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      nickname: "未登录",
      avatar: "",
      role: ACCESS_ENUM.NOT_LOGIN,
      token: "",
    },
  }),
  actions: {
    async getLoginUser({ commit, state }, payload) {
      const res = await UserControllerService.getLoginUser();
      console.log("getLoginUser", res);
      if (res.code === 0) {
        commit("updateUser", {
          nickname: res.data?.nickname,
          role: res.data?.role,
          avatar: res.data?.avatar,
        });
      } else {
        commit("updateUser", {
          nickname: "未登录",
          avatar: "",
          role: ACCESS_ENUM.NOT_LOGIN,
          token: "",
        });
      }
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
