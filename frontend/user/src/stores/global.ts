import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "../utils/storage";

const useGlobalStore = defineStore("global", {
    state: () => ({
        isInit: false,
        keyName: "globalStore",
        data: {
            version: '0.0.1',
        },
        apiHost: 'http://127.0.0.1:8080/api',
        defaultAvatarUrl: '/static/img/avatar.jpg',
    }),
    getters: {

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
    }
});

export default useGlobalStore;
