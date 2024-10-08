<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { showFailToast } from 'vant';
import type { NearbyQuery, ResponseData, RouteBO, StationBO } from '@/utils/interface';
import { apiLostPageQuery, apiStationNearby } from '@/utils/api';
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
const isLoading = ref(false);
const nearbyStations = ref<StationBO[]>([]);
const bodyRef = ref();
const defaultLongitude = 106.53984026193238;
const defaultLatitude = 29.346101336631264;

const onRefreshClick = () => {
    nearbyStations.value.length = 0;
    getNearbyList();
};

// 获取公告列表
const lostNotice = ref('[失物公告]：...');
const getNoticeList = () => {
    apiLostPageQuery({pageIndex: 1, pageSize: 10, status: 0 } as any, (data: ResponseData) => {
        const lost = data.data.list[0];
        lost && (lostNotice.value = `[失物公告]：${lost.pickDatetime.substring(0, 10)} 于 "${lost.address}" 拾到 "${lost.describe}"。`);
    });
};
// 获取附近站点列表
const getNearbyList = () => {
    isLoading.value = true;

    getCurrentPosition((pos: GeolocationPosition) => {
        query.longitude = pos.coords.longitude;
        query.latitude = pos.coords.latitude;
        // query.longitude = defaultLongitude;
        // query.latitude = defaultLatitude;

        apiStationNearby(query, (data: ResponseData) => {
            if (data.data.length === 0) {
                query.longitude = defaultLongitude;
                query.latitude = defaultLatitude;

                apiStationNearby(query, (data: ResponseData) => {
                    isLoading.value = false;
                    // 只显示前 9 条
                    nearbyStations.value = data.data.slice(0, 9);
                }, (data: ResponseData) => {
                    isLoading.value = false;
                    showFailToast(data.message);
                });
            }
            // 只显示前 9 条
            nearbyStations.value = data.data.slice(0, 9);
        }, (data: ResponseData) => {
            isLoading.value = false;
            showFailToast(data.message);
        });
    }, () => {
        // showFailToast('没有定位权限');
        query.longitude = defaultLongitude;
        query.latitude = defaultLatitude;

        apiStationNearby(query, (data: ResponseData) => {
            isLoading.value = false;
            // 只显示前 9 条
            nearbyStations.value = data.data.slice(0, 9);
        }, (data: ResponseData) => {
            isLoading.value = false;
            showFailToast(data.message);
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
            routeId: routeId,
            stationId: station.id,
        },
    });
};
const goRouteDetail = (route: RouteBO, stationId: number) => {
    historyStore.addHistory(route);
    router.push({ name: 'nearbyRouteDetail', query: {
        routeId: route.id,
        stationId: stationId,
    }});
};

onMounted(() => {
    getNoticeList();
    getNearbyList();
});
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <van-notice-bar
            class="lost clickable"
            left-icon="volume-o"
            mode="link"
            :scrollable="true"
            :text="lostNotice"
            @click="$router.push({ name: 'nearbyLost' })"
        />
        <div class="body" ref="bodyRef" >
            <van-loading v-if="!nearbyStations.length" vertical>地点加载中...</van-loading>
            <van-empty v-if="!nearbyStations.length" image="search" description="暂无数据" />

            <div
                class="station-box"
                v-for="station in nearbyStations"
            >
                <div class="header clickable" @click="goStationDetail(station, null)">
                    <i class="spirit station"></i>
                    <span class="sitename">
                        <span>{{ station.sitename }}</span>
                        <span class="danger-super" v-if="!station.status">暂未开通</span>
                    </span>
                    <span class="distance">{{ station.distance }} <span style="font-size: .88rem;">米</span></span>
                </div>
                <div class="body">
                    <div class="route-card clickable" v-for="route in station.routes" @click="goRouteDetail(route, station.id!)">
                        <div class="title">
                            <div class="route-no">
                                <span>{{ route.no }}路</span>
                                <span class="danger-super" v-if="!route.status">暂未开通</span>
                            </div>
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
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    padding: 15px;

    .lost {
        position: sticky;
        top: 0;
        transform: translate(-15px, -15px);
        width: calc(100% + 30px);
    }
    .body {
        .van-loading {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }
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
