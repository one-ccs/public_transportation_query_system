import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "../utils/storage";
import i18n from "@/utils/i18n";

const useUserStore = defineStore("user", {
    state: () => ({
        isInit: false,
        keyName: "userStore",
        data: {
            token: '',
            username: '',
            roles: <string[]>[],
        },
    }),
    getters: {
        userInfo: state => state.data,
        role: state => i18n(state.data.roles[0], 'zh'),
        roles: state => state.data.roles.map(role => i18n(role, 'zh')),
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
            this.$reset();
            localRemove(this.keyName);
            return this;
        },
        setUser(user: any) {
            // 设置用户信息
            this.data = { ...user, };
            this.save();
            return this;
        },
    }
});

export default useUserStore;
