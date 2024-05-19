import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "../utils/storage";
import type { RouteBO, StationBO } from "@/utils/interface";

const useHistoryStore = defineStore("history", {
    state: () => ({
        isInit: false,
        keyName: "historyStore",
        data: {
            history: <Array<StationBO & RouteBO>>[],
        },
    }),
    getters: {
        get: (state) => state.data.history,
    },
    actions: {
        init(force=false) {
            if (!force && this.isInit) return;
            this.isInit = true;

            this.data = { ...localLoad(this.keyName, {}) };
            if (!this.data.history) this.data.history = [];
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
        set(history: StationBO | StationBO[] | RouteBO | RouteBO[]) {
            if (!this.data.history) this.data.history = [];
            Array.isArray(history) ?
                this.data.history.push(...history) :
                this.data.history.push(history);
            this.save();
        },
        delete(history: StationBO | RouteBO) {
            const index = this.data.history.indexOf(history);
            this.data.history.splice(index, 1);
            this.save();
        },
    }
});

export default useHistoryStore;
