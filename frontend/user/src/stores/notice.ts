import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "../utils/storage";

const useNoticeStore = defineStore("notice", {
    state: () => ({
        isInit: false,
        keyName: "noticeStore",
        data: {},
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

export default useNoticeStore;
