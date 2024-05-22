import { defineStore } from "pinia";
import { showFailToast, showLoadingToast, showSuccessToast } from "vant";
import axios from "axios";
import { localLoad, localRemove, localSave } from "@/utils/storage";
import type { RouteBO, StationBO } from "@/utils/interface";


// 测试 api 服务器是否连通
const testApi = (apiHost: string, path: string) => axios.get(apiHost + path, {
    timeout: 3000,
});

const nullSearchResult = {
    'routes': <RouteBO[]>[],
    'stations': <StationBO[]>[],
};

const useGlobalStore = defineStore("global", {
    state: () => ({
        isInit: false,
        keyName: "globalStore",
        data: {
            token: '',
        },
        _apiHostList: [
            'http://127.0.0.1:8080',
            'http://192.168.137.1:8080',
            'http://192.168.37.1:8080',
            'http://192.168.71.106:8080',
        ],
        apiHost: 'https://www.one-ccs.com:8080',
        onConnectedServer: (apiHost: string) => {},
        timeout: 5000,
        search: '',
        onSearch: () => {},
        isSearched: false,
        isSearching: false,
        searchResult: {
            ...nullSearchResult,
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

            this.connectApiServer();
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
        async connectApiServer() {
            const toast = showLoadingToast({
                type: 'loading',
                message: `正在尝试连接\n服务器...`,
                overlay: true,
                forbidClick: false,
                duration: 0,
            });
            let isSuccess = false, i = 0;

            for (let apiHost of this._apiHostList) {
                toast.message = `正在尝试连接\n服务器：${++i}`;

                try {
                    const data = await testApi(apiHost, '/api/util/connected');

                    if (data.data) {
                        isSuccess = true;
                        this.apiHost = apiHost;
                        this.onConnectedServer(apiHost);
                        break;
                    }
                }
                catch(e) {}
            }
            isSuccess ? showSuccessToast('服务器连接\n成功') : showFailToast('服务器连接\n失败');
        },
        setToken(token: string) {
            this.data.token = token;
        },
        clearSearchResult() {
            this.searchResult = {...nullSearchResult};
        },
    }
});

export default useGlobalStore;
