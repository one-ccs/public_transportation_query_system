import { defineStore } from "pinia";
import { localLoad, localRemove, localSave } from "@/utils/storage";
import type { RouteBO, StationBO } from "@/utils/interface";

const useGlobalStore = defineStore("global", {
    state: () => ({
        isInit: false,
        keyName: "globalStore",
        data: {
            token: '',
        },
        search: '',
        onSearch: () => {},
        isSearched: false,
        searchResult: {
            'routes': <RouteBO[]>[],
            'stations': <StationBO[]>[],
        },
    }),
    getters: {
        token: (state) => state.data.token,
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
