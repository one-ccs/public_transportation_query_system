import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "../utils/storage";
import type { RouteBO, StationBO } from "@/utils/interface";

const useHistoryStore = defineStore("history", {
    state: () => ({
        isInit: false,
        keyName: "historyStore",
        data: {
            history: <Array<StationBO & RouteBO>>[],
            search: <string[]>[],
        },
    }),
    getters: {
        history: state => state.data.history,
        search: state => state.data.search,
    },
    actions: {
        init(force=false) {
            if (!force && this.isInit) return;
            this.isInit = true;

            this.data = { ...localLoad(this.keyName, {}) };
            if (!this.data.history) this.data.history = [];
            if (!this.data.search) this.data.search = [];
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
        addHistory(history: StationBO | StationBO[] | RouteBO | RouteBO[]) {
            if (!this.data.history) this.data.history = [];
            Array.isArray(history) ?
                this.data.history.unshift(...history) :
                this.data.history.unshift(history);
            this.save();
        },
        deleteHistory(history: StationBO | RouteBO) {
            const index = this.data.history.indexOf(history);
            this.data.history.splice(index, 1);
            this.save();
        },
        addSearch(query: string) {
            if (!this.data.search) this.data.search = [];
            this.data.search.unshift(query);
        },
        deleteSearch(query: string) {
            this.data.search = this.data.search.filter(text => text !== query);
        },
    }
});

export default useHistoryStore;
