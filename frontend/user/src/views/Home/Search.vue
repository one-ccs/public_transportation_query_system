<script setup lang="ts">
import { reactive, ref } from 'vue';
import { showDialog, showFailToast } from 'vant';
import type { ResponseData, TimeRangePageQuery } from '@/utils/interface';
import { apiUtilSearch } from '@/utils/api';
import useGlobalStore from '@/stores/global';
import useHistoryStore from '@/stores/history';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';

const globalStore = useGlobalStore();
const historyStore = useHistoryStore();
const bodyRef = ref();
const query = reactive<TimeRangePageQuery>({
	pageIndex: 1,
	pageSize: 15,
	query: '',
    startDatetime: '',
    endDatetime: '',
});

globalStore.onSearch = () => {
    globalStore.clearSearchResult();
    globalStore.isSearched = globalStore.isSearching = true;
    historyStore.addSearch(globalStore.search);
    query.query = globalStore.search;

    apiUtilSearch(query, (data: ResponseData) => {
        globalStore.isSearching = false;
        globalStore.searchResult.routes = data.data.routes.list;
        globalStore.searchResult.stations = data.data.stations.list;
    }, (data: ResponseData) => {
        globalStore.isSearching = false;
        showFailToast(data.message);
    });
};

const onHistoryClick = (search: string) => {
    globalStore.search = search;
    globalStore.onSearch();
};

// 清空历史记录
const onDeleteClick = () => {
    showDialog({
        showCancelButton: true,
        message: '确认清空搜索历史吗?',
    }).then(() => {
        historyStore.clear();
    }).catch(() => {});
};
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <div class="body" ref="bodyRef">
            <van-loading v-if="globalStore.isSearching">搜索中...</van-loading>

            <div v-if="globalStore.isSearched" class="search-result">
                <van-empty v-if="!(globalStore.searchResult.routes.length + globalStore.searchResult.stations.length)" image="search" description="没有查找到相关信息哦" />

                <div v-if="globalStore.searchResult.routes.length" class="routes">
                    <div class="title">线路</div>
                    <div
                        class="item route link-button"
                        v-for="route in globalStore.searchResult.routes"
                        @click="$router.push({ name: 'searchRouteDetail', query: { routeId: route.id } })"
                    >
                        <i class="spirit bus"></i>
                        <span>{{ route.no }}路</span>
                    </div>
                </div>
                <div v-if="globalStore.searchResult.stations.length" class="stations">
                    <div class="title">站点</div>
                    <div
                        class="item station"
                        v-for="station in globalStore.searchResult.stations"
                        @click="$router.push({ name: 'searchStationDetail', query: { stationId: station.id } })"
                    >
                        <i class="spirit station"></i>
                        <span>{{ station.sitename }}</span>
                    </div>
                </div>
            </div>
            <div v-else class="search-history">
                <van-empty v-if="!historyStore.searches.length" image="search" description="没有搜索历史哦" />

                <template v-else>
                    <div class="title">搜索历史</div>
                    <div
                        class="history link-button"
                        v-for="search in historyStore.searches"
                    >
                        <div class="text" @click="onHistoryClick(search)">
                            <van-icon name="clock-o" />
                            <span>{{ search }}</span>
                        </div>
                        <van-icon
                            class="delete"
                            class-prefix="fa"
                            name="close"
                            @click="historyStore.deleteSearch(search)"
                        ></van-icon>
                    </div>

                    <div class="btn-clear link-button" v-if="historyStore.searches.length">
                        <icon-box name="delete" @click="onDeleteClick"></icon-box>
                    </div>
                </template>
            </div>

            <van-back-top v-if="bodyRef" offset="120" bottom="80" z-index="1" teleport=".body" />
        </div>
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    .body {
        height: 100%;

        .search-result {
            .title {
                padding: var(--padding);
                color: #999;
                background-color: #FBFBFD;
            }
            .item {
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: flex-start;
                padding: var(--padding);
                background-color: #fff;

                .spirit {
                    margin-right: 8px;
                }
            }
        }
        .search-history {
            height: 100%;

            .title {
                padding: var(--padding);
                background-color: #FBFBFD;
            }
            .history {
                display: flex;
                align-items: center;
                border-top: var(--border-divider);
                background-color: #fff;

                &:first-child {
                    border-top: unset;
                }
                .text {
                    flex: 1 0 auto;
                    padding: var(--padding);
                    font-size: 1rem;

                    .van-icon {
                        margin-right: 8px;
                    }
                }
                .delete {
                    flex: 0 0 auto;
                    width: 3rem;
                    height: 2.5rem;
                    line-height: 2.5rem;
                    color: #888;
                }
            }
            .btn-clear {
                position: fixed;
                left: 50%;
                bottom: 80px;
                transform: translateX(-50%);
            }
        }
    }
}
</style>
