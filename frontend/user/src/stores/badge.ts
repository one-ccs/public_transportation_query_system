import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "../utils/storage";

const useBadgeStore = defineStore("badge", {
    state: () => ({
        isInit: false,
        keyName: "badgeStore",
        data: {
            notice: 0,
        },
    }),
    getters: {
        notice: (state: any) => state.data.notice || '',
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

export default useBadgeStore;
