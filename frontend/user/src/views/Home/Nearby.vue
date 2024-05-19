<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import type { NearbyQuery, ResponseData, Route, RouteBO, Station, StationBO } from '@/utils/interface';
import { apiStationNearby } from '@/utils/api';
import { getCurrentPosition } from '@/utils/advanced';
import useHistoryStore from '@/stores/history';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import IconBox from '@/components/IconBox.vue';

const router = useRouter();
const historyStore = useHistoryStore();
const query = reactive<NearbyQuery>({
    longitude: null,
    latitude: null,
    distance: 1500,
})
const nearbyStations = ref<StationBO[]>([]);
const bodyRef = ref();

const onRefreshClick = () => {
    nearbyStations.value.length = 0;
    getNearbyList();
    // bodyRef.value.scrollIntoView({
    //     behavior: 'smooth',
    // });
};

// 获取附近站点列表
const getNearbyList = () => {
    getCurrentPosition((pos: GeolocationPosition) => {
        query.longitude = pos.coords.longitude;
        query.latitude = pos.coords.latitude;

        apiStationNearby(query, (data: ResponseData) => {
            if (data.data.length === 0) {
                query.longitude = 106.521978;
                query.latitude = 29.380991;

                apiStationNearby(query, (data: ResponseData) => {
                    // 只显示前 9 条
                    nearbyStations.value = data.data.slice(0, 9);
                });
            }
            // 只显示前 9 条
            nearbyStations.value = data.data.slice(0, 9);
        });
    });
};
const parseNextText = (station: StationBO, route: RouteBO) => {
    for (let [index, _station] of route.stations!.entries()) {
        if (_station.id === station.id && route.stations![index + 1]) {
            return '下一站·' + route.stations![index + 1].sitename;
        }
    }
    return '终点站';
};
const goStationDetail = (station: StationBO, routeId: number | null) => {
    historyStore.addHistory(station);
    router.push({
        name: 'nearbyStationDetail',
        query: {
            'routeId': routeId,
            'stationId': station.id,
        },
    });
};
const goRouteDetail = (route: RouteBO, stationId: number) => {
    historyStore.addHistory(route);
    router.push({ name: 'nearbyRouteDetail', query: {
        'routeId': route.id,
        'stationId': stationId,
    }});
};

onMounted(() => {
    getNearbyList();
});
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <div class="body" ref="bodyRef" v-if="nearbyStations.length">
            <div
                class="station-box"
                v-for="station in nearbyStations"
            >
                <div class="header clickable" @click="goStationDetail(station, null)">
                    <i class="spirit station"></i>
                    <span class="sitename">{{ station.sitename }}</span>
                    <span class="distance">{{ station.distance }} <span style="font-size: .88rem;">米</span></span>
                </div>
                <div class="body">
                    <div class="route-card clickable" v-for="route in station.routes" @click="goRouteDetail(route, station.id!)">
                        <div class="title">
                            <div class="route-no">{{ route.no }}路</div>
                            <div class="time">{{ route.firstTime }}-{{ route.lastTime }}</div>
                        </div>
                        <div class="describe">
                            <van-icon class-prefix="fa" name="arrow-circle-o-right"></van-icon>
                            <div class="info">{{ parseNextText(station, route) }}</div>
                            <div class="state">等待发车</div>
                        </div>
                    </div>
                </div>
            </div>
            <van-back-top v-if="bodyRef" offset="120" bottom="80" z-index="1" teleport=".body" />
            <div class="btn-refresh link-button">
                <icon-box name="replay" @click="onRefreshClick"></icon-box>
            </div>
        </div>
        <van-empty v-else image="search" description="暂无数据" />
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    padding: 8px;

    .body {
        .station-box {
            margin-bottom: 8px;

            .header {
                display: flex;
                align-items: center;
                justify-content: flex-start;
                padding: 12px 8px;
                font-size: 1.1rem;
                background-color: #FBFBFD;

                .spirit {
                    margin-right: 8px;
                }
                .distance {
                    margin-left: auto;
                }
            }
            .body {
                background-color: #fff;

                .route-card {
                    padding: 8px;
                    border-bottom: 1px solid #ddd;

                    &:last-child {
                        border-bottom: none;
                    }
                    .title {
                        display: flex;
                        margin-bottom: 12px;
                        color: #333;

                        .time {
                            margin-left: auto;
                            font-size: .9rem;
                        }
                    }
                    .describe {
                        display: flex;
                        align-items: center;
                        justify-content: flex-start;
                        color: #888;
                        font-size: .9rem;

                        .state {
                            margin-left: auto;
                        }
                    }
                }
            }
        }
        .btn-refresh {
            z-index: 9;
            position: fixed;
            left: 50%;
            bottom: 70px;
            transform: translateX(-50%);
        }
    }
}
</style>
