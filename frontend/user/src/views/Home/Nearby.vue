<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import type { NearbyQuery, ResponseData, StationBO } from '@/utils/interface';
import { apiStationNearby } from '@/utils/api';
import { getCurrentPosition } from '@/utils/advanced';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';

const query = reactive<NearbyQuery>({
    longitude: null,
    latitude: null,
    distance: 9999999,
})
const nearbyStations = ref<StationBO[]>([]);

const onRefreshClick = () => {
    getNearbyList();
};

const getNearbyList = () => {
    getCurrentPosition((pos: GeolocationPosition) => {
        query.longitude = pos.coords.longitude;
        query.latitude = pos.coords.latitude;

        apiStationNearby(query, (data: ResponseData) => {
            nearbyStations.value = data.data.slice(0, 5);
        });
    });
};

onMounted(() => {
    getNearbyList();
});
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <div class="body" v-if="nearbyStations.length">
            <div
                class="station-box"
                v-for="station in nearbyStations"
            >
                <div class="header">
                    <van-icon class="icon" class-prefix="fa" name="bus"></van-icon>
                    <span class="sitename">{{ station.sitename }}</span>
                </div>
                <div class="route-list"></div>
            </div>
            <van-back-top offset="120" bottom="80"></van-back-top>
            <div class="btn-refresh">
                <van-button icon="replay" type="primary" round @click="onRefreshClick"></van-button>
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
            .header {
                padding: 8px;
                height: 32px;
                font-size: 1.15rem;
                background-color: #FBFBFD;

                .icon {
                    margin-right: 8px;
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
