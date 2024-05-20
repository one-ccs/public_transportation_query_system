<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import type { ResponseData, RouteBO } from '@/utils/interface';
import { apiNoticeGet, apiRouteDetail } from '@/utils/api';
import { useDocumentTitle } from '@/utils/use';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import BackNavBar from '@/components/BackNavBar.vue';
import { showDialog } from 'vant';

const route = useRoute();
const router = useRouter();
const routeDetail = ref<RouteBO>();
const routeId = Number(route.query.routeId);
const stationId = computed(() => {
    const queryStationId = Number(route.query.stationId);
    let detailStationId = 0;

    if (routeDetail.value?.stations) {
        detailStationId = routeDetail.value.stations[currentIndex.value].id || 0;
    }

    return queryStationId ? queryStationId : detailStationId;
});
const currentIndex = ref(0);
const isInvert = ref(false);
const notice = ref('[服务热线]：\n...');
const title = computed(() => {
    return routeDetail.value?.no + '路';
});

const getData = () => {
    routeId && apiRouteDetail(routeId, (data: ResponseData) => {
        routeDetail.value = data.data;
        useDocumentTitle(title.value);

        const length = routeDetail.value?.stations?.length || 0;
        for (let index = 0; index < length; index++) {

            if (routeDetail.value?.stations![index].id === stationId.value) {
                currentIndex.value = index;
                break;
            }
        }
    });
    apiNoticeGet(18, (data: ResponseData) => {
        notice.value = data.data.content;
    });
};

// 获取始-终站信息
const stationSE = computed(() => {
    const station = routeDetail.value?.stations || [];

    if (!station.length) return 'Null';

    const startSite = station[0].sitename;
    const endSite = station[station.length - 1].sitename;

    return !isInvert.value ? `${startSite}→${endSite}` : `${endSite}→${startSite}`;
});
const routeInfo = computed(() => {
    return `${routeDetail.value?.firstTime?.substring(0, 5)}-${routeDetail.value?.lastTime?.substring(0, 5)}·票价${routeDetail.value?.price}元`;
});

const onHomeClick = () => {
    router.push({ name: 'nearby' });
};

onMounted(() => {
    getData();
});
</script>

<template>
    <div class="view">
        <right-slide-router-view />
        <back-nav-bar class="view-header" :title="title" @click-right="onHomeClick()">
            <template #right>
                <van-icon name="wap-home" size="1.3rem" color="#666"/>
            </template>
        </back-nav-bar>
        <div class="view-container">
            <div class="head">
                <div class="info">
                    <div class="route-se">{{ stationSE }}</div>
                    <div class="route-describe">{{ routeInfo }}</div>
                </div>
                <van-notice-bar
                    class="notice"
                    left-icon="volume-o"
                    mode="link"
                    :scrollable="true"
                    :text="notice"
                    @click="showDialog({ message: notice, messageAlign: 'left', closeOnClickOverlay: true })"
                />
            </div>
            <div class="body">
                <div class="info-box">
                    <div class="info">
                        <div class="text">等待发车</div>
                        <div class="describe">上一班7分钟前过站</div>
                    </div>
                    <div class="same-station-route link-button" @click="router.push({ path: `${$route.matched[1].path}/stationDetail`, query: { stationId: stationId } })">
                        <span>同站线路</span>
                        <i class="spirit same-route"></i>
                    </div>
                </div>
                <van-steps class="stations" :active="currentIndex" direction="vertical" active-color="#07c160">
                    <van-step
                        class="station"
                        v-for="(station, index) in routeDetail?.stations"
                        @click="$router.push({ path: `${$route.matched[1].path}/stationDetail`, query: { stationId: station.id } })"
                    >
                        <span class="index">{{ index + 1 }}</span>
                        <span class="sitename">{{ station.sitename }}</span>
                    </van-step>
                </van-steps>
                <van-back-top v-if="routeDetail" offset="120" bottom="80" teleport=".body" />
            </div>
        </div>
    </div>
</template>

<style scoped lang="less">
.view {
    .view-container {
        display: flex;
        flex-direction: column;

        .head {
            background-color: #fff;

            .info {
                padding: var(--padding);

                .route-se {
                    font-size: 1.2rem;
                }
                .route-describe {
                    margin-top: .5rem;
                }
            }
            .notice {
                border-top: var(--border-divider);
            }
        }
        .body {
            padding: var(--padding);
            overflow-y: auto;

            .info-box {
                display: flex;
                flex-direction: column;
                align-items: center;
                border-radius: var(--border-radius);
                background-color: #fff;

                .info {
                    padding: calc(var(--padding) * 2) 0;
                    text-align: center;

                    .text {
                        font-size: 1.5rem;
                        color: #3498DB;
                    }
                    .describe {
                        margin-top: 3px;
                    }
                }
                .same-station-route {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    border-top: var(--border-divider);
                    padding: calc(var(--padding) * .5) 0;
                    width: 100%;
                    text-align: center;

                    .spirit {
                        transform: scale(.5) scaleY(.8) translateX(-25%);
                    }
                }
            }
            .stations {
                margin-top: var(--padding);
                border-radius: var(--border-radius);
                padding: 15px 15px 15px 38px;

                .station {
                    border-radius: var(--border-radius);
                    padding-left: 8px;

                    &:hover {
                        background-color: rgba(0, 0, 0, 0.03);
                    }
                    .index {
                        display: inline-block;
                        margin-right: .5rem;
                        width: 1rem;
                        text-align: right;
                    }
                    .sitename {
                        font-size: 1rem;
                    }
                }
            }
        }
    }
}
</style>
