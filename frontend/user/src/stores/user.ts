import { defineStore } from "pinia";
import type { UnionUser } from "@/utils/interface";
import { localLoad, localRemove, localSave } from "../utils/storage";


const useUserStore = defineStore("user", {
    state: () => ({
        isInit: false,
        keyName: "userStore",
        data: {
            name: '小哆啦',
            expires: -1,
        },
        isLogin: false,
    }),
    getters: {
        userInfo: (state: any): UnionUser => state.data,
    },
    actions: {
        init(force=false) {
            if (!force && this.isInit) return;
            this.isInit = true;

            this.data = { ...localLoad(this.keyName, {}) };
        },
        load() {
            this.init(true);
            return this;
        },
        save() {
            localSave(this.keyName, this.data);
            return this;
        },
        clear() {
            localRemove(this.keyName);
            return this;
        },
        setUser(user: UnionUser) {
            // 设置用户信息
            const { avatar, name, username, createDatetime } = user;
            this.data = {
                ...user,
                name: name || username || this.data.name,
                expires: ((new Date()).getTime() + 1000 * 3600 * 24),
            };

            // 修改登录状态
            this.isLogin = true;
            return this;
        },
        isExpired() {
            if (!this.data.expires) return true;
            return this.data.expires <= new Date().getTime();
        },
    }
});

export default useUserStore;
