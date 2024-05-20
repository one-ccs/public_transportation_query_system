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
        histories: state => state.data.history,
        searches: state => state.data.search,
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
        addHistory(history: StationBO & RouteBO) {
            if (!this.data.history) this.data.history = [];
            this.data.history = this.data.history.filter(h => {
                // 线路
                if (h.no && h.no === history.no) return false;
                // 站点
                if (h.sitename && h.sitename === history.sitename) return false;

                return true;
            });
            this.data.history.unshift(history);
            this.save();
        },
        deleteHistory(history: StationBO & RouteBO) {
            const index = this.data.history.indexOf(history);
            this.data.history.splice(index, 1);
            this.save();
        },
        addSearch(search: string) {
            if (!search) return;
            if (!this.data.search) this.data.search = [];
            this.data.search = this.data.search.filter(s => s !== search);
            this.data.search.unshift(search);
            this.save();
        },
        deleteSearch(query: string) {
            this.data.search = this.data.search.filter(text => text !== query);
            this.save();
        },
    }
});

export default useHistoryStore;
