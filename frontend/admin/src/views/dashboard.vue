<template>
    <dv-border-box11
        class="root-box"
        ref="rootBoxRef"
        title="重庆公交查询系统"
    >
        <div class="btn-full-screen link-button" @click="toggleFullScreen()">
                <el-icon v-if="!isFullscreen()" class="tooltip left" tooltip="全屏" :size="18" color="#ddd">
                    <FullScreen />
                </el-icon>
                <el-icon v-else class="tooltip left" tooltip="退出全屏" :size="18" color="#ddd">
                    <CloseBold />
                </el-icon>
        </div>

        <div class="body">
            <div class="number-charts">
                <div class="number-chart">
                    <div class="title">用户数</div>
                    <dv-digital-flop :config="options.userCount"></dv-digital-flop>
                </div>
                <div class="number-chart">
                    <div class="title">线路数</div>
                    <dv-digital-flop :config="options.routeCount"></dv-digital-flop>
                </div>
                <div class="number-chart">
                    <div class="title">站点数</div>
                    <dv-digital-flop :config="options.stationCount"></dv-digital-flop>
                </div>
            </div>
        </div>
    </dv-border-box11>
</template>

<script setup lang="ts" name="dashboard">
import { apiStatistics } from '@/utils/api';
import { isFullscreen, requestFullscreen, exitFullscreen } from '@/utils/fullscreen';
import type { ResponseData } from '@/utils/interface';
import { onMounted, reactive, ref } from 'vue';

const rootBoxRef = ref();
const options = reactive({
    userCount: {
        number: [233],
        content: '{nt}位',
    },
    routeCount: {
        number: [77],
        content: '{nt}条',
    },
    stationCount: {
        number: [23],
        content: '{nt}条',
    },
})

const toggleFullScreen = () => {
    isFullscreen() ? exitFullscreen() : requestFullscreen(rootBoxRef.value.$el);
};

const getData = () => {
    apiStatistics((data: ResponseData) => {
        console.log(data.data);

        options.userCount.number[0] = data.data.userCount;
        options.routeCount.number[0] = data.data.routeCount;
        options.stationCount.number[0] = data.data.stationCount;
    });
};

onMounted(() => {
    getData();
    setTimeout(() => {
        rootBoxRef.value?.initWH();
    }, 500);
});
</script>

<style scoped lang="less">
    .root-box {
        position: relative;
        border-radius: calc(var(--border-radius) * 2);
        width: 100%;
        height: 100%;
        background-color: #0C1426;

        .btn-full-screen {
            opacity: .3;
            position: absolute;
            top: 5px;
            right: 20px;

            &:hover {
                opacity: 1;
            }
        }
        .body {
            .number-charts {
                display: flex;

                .number-chart {
                    width: 200px;
                    color: #0B46A1;
                    background-color: #2677a333;

                    canvas {
                        width: 200px;
                        height: 30px;
                    }
                }
            }
        }
    }
</style>
