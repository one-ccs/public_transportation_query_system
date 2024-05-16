import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "@/utils/storage";
import defaultAvatar from '@/assets/img/img.jpg';

const useGlobalStore = defineStore("global", {
    state: () => ({
        isInit: false,
        keyName: "globalStore",
        data: {
            token: '',
        },
        apiHost: 'http://127.0.0.1:8080',
        timeout: 5000,
        defaultAvatarUrl: defaultAvatar,
    }),
    getters: {
        token: (state) => state.data.token,
        avatarApi: state => state.apiHost + '/api/file/image/avatar/',
        lostApi: state => state.apiHost + '/api/file/image/lost/',
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
        setToken(token: string) {
            this.data.token = token;
        },
    }
});

export default useGlobalStore;
