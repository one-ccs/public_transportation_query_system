<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { showFailToast } from 'vant';
import { useRoute, useRouter } from 'vue-router';
import type { ResponseData, RouteBO, Station } from '@/utils/interface';
import { apiStationGet, apiStationRoutes } from '@/utils/api';
import { useDocumentTitle } from '@/utils/use';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import BackNavBar from '@/components/BackNavBar.vue';

const route = useRoute();
const router = useRouter();
const stationDetail = ref<Station>();
const passStationRoutes = ref<RouteBO[]>([]);
const routeId = Number(route.query.routeId);
const stationId = Number(route.query.stationId);
const routesRef = ref();
const isLoading = ref(false);

const getData = () => {
    isLoading.value = true;

    apiStationGet(stationId, (data: ResponseData) => {
        stationDetail.value = data.data;
    });
    apiStationRoutes(stationId, (data: ResponseData) => {
        isLoading.value = false;
        passStationRoutes.value = data.data;
        useDocumentTitle(stationDetail.value?.sitename!);
    }, (data: ResponseData) => {
        isLoading.value = false;
        showFailToast(data.message);
    });
};

const getNextStationText = (route: RouteBO & {_invert?: boolean}) => {
    const stations = route.stations || [];
    const length = stations?.length || 0;

    for (let i = 0; i < length; i++) {
        if (stations[i].id === stationId) {
            if (route._invert) {
                return i === 0 ? '本站为终点站' : `下一站·${stations[i - 1].sitename}`;
            }
            return i < length - 1 ? `下一站·${stations[i + 1].sitename}` : '本站为终点站';
        }
    }
    return 'Null';
};

const onHomeClick = () => {
    router.push({ name: 'nearby' });
};
const onStationClick = (routeBO: RouteBO) => {
    router.push({
        path: route.matched[1].path + '/routeDetail',
        query: {
            routeId: routeBO.id,
            stationId: stationId,
        },
    });
};
const onInvertClick = (route: RouteBO & {_invert?: boolean}) => {
    route._invert = !route._invert;
};

onMounted(() => {
    getData();
});
</script>

<template>
    <div class="view">
        <right-slide-router-view />
        <back-nav-bar class="view-header" @click-right="onHomeClick()">
            <template #title>
                <span>{{ stationDetail?.sitename }}</span>
                <span class="danger-super" v-if="!stationDetail?.status">暂未开通</span>
            </template>
            <template #right>
                <van-icon name="wap-home" size="1.3rem" color="#666"/>
            </template>
        </back-nav-bar>
        <div class="view-container">
            <van-loading v-if="isLoading">加载中...</van-loading>

            <div class="routes" ref="routesRef">
                <div class="route" v-for="route in passStationRoutes">
                    <div class="info" @click="onStationClick(route)">
                        <div class="title">
                            <span class="no">
                                <span>{{ route.no }}路</span>
                                <span class="danger-super" v-if="!route.status">暂未开通</span>
                            </span>
                            <span class="time-fl">{{ route.firstTime }}-{{ route.lastTime }}</span>
                        </div>
                        <div class="describe">
                            <span class="next">{{ getNextStationText(route) }}</span>
                            <span class="state">等待发车</span>
                        </div>
                    </div>
                    <div class="invert link-button" @click="onInvertClick(route)">
                        <van-icon name="sort"></van-icon>
                    </div>
                </div>

                <van-back-top v-if="routesRef" offset="120" bottom="80" z-index="1" teleport=".routes" />
            </div>
        </div>
    </div>
</template>

<style scoped lang="less">
.view {
    .view-container {
        padding: var(--padding);

        .routes {
            border-radius: var(--border-radius);
            background-color: #fff;

            .route {
                display: flex;
                align-items: center;
                border-bottom: var(--border-divider);
                padding: var(--padding) 0;

                &:last-child {
                    border-bottom: unset;
                }
                .info {
                    flex: 1 0 auto;
                    padding: 0 var(--padding);
                    border-right: var(--border-divider);

                    .title {
                        display: flex;
                        font-size: 1.05rem;

                        & > * {
                            font-weight: bold;
                        }
                        .time-fl {
                            margin-left: auto;
                        }
                    }
                    .describe {
                        display: flex;
                        margin-top: 1rem;
                        color: #999;
                        font-size: .8rem;

                        .state {
                            margin-left: auto;
                        }
                    }
                }
                .invert {
                    padding: 0 var(--padding);

                    .van-icon {
                        color: #3498DB;
                        transform: rotateZ(90deg);
                    }
                }
            }
        }
    }
}
</style>
