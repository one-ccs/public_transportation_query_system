<template>
    <dv-border-box11
        class="root-box"
        ref="rootBoxRef"
        title="重庆公交查询系统"
    >
        <div class="btn-full-screen link-button" @click="toggleFullScreen()">
            <el-icon v-if="!isFullscreen()" class="tooltip bottom" tooltip="全屏" :size="18" color="#ddd">
                <FullScreen />
            </el-icon>
            <el-icon v-else class="tooltip bottom" tooltip="退出全屏" :size="18" color="#ddd">
                <CloseBold />
            </el-icon>
        </div>
        <div class="btn-refresh link-button" @click="getData()">
            <el-icon class="tooltip bottom" tooltip="刷新" :size="20" color="#ddd"><Refresh /></el-icon>
        </div>

        <div class="body">
            <div class="d-flex flex-row">
                <dv-border-box12 ref="routeOfStationCountBorderBoxRef">
                    <div class="chart-title">
                        <span>线路站点数排行</span>
                        <dv-decoration6 class="d-inline-block ms-2" style="width:100px;height:10px;" />
                    </div>
                    <dv-scroll-ranking-board :config="options.routeOfStationCount" />
                </dv-border-box12>
                <dv-border-box12 ref="lostStatusCountBorderBoxRef">
                    <div class="chart-title">
                        <span>失物认领状态</span>
                        <dv-decoration6 class="d-inline-block ms-2" style="width:100px;height:10px;" />
                    </div>
                    <dv-active-ring-chart :config="options.lostStatusCount" style="width:250px;height:250px;margin-top:-25px;" />
                </dv-border-box12>
                <dv-border-box12 ref="routeOfLengthRankBorderBoxRef">
                    <div class="chart-title">
                        <span>线路长度排行</span>
                        <dv-decoration6 class="d-inline-block ms-2" style="width:100px;height:10px;" />
                    </div>
                    <dv-scroll-ranking-board :config="options.routeOfLengthRank" />
                </dv-border-box12>
            </div>
            <div class="number-charts">
                <dv-border-box13 ref="numbersBorderBoxRef">
                    <div class="number-chart ms-5">
                        <div class="chart-title">
                            <dv-decoration-11 style="width:150px;height:50px;color: #55E088;font-size: 13px;">用户数</dv-decoration-11>
                        </div>
                        <dv-digital-flop :config="options.userCount" style="width: 100px;height: 50px;" />
                    </div>
                    <div class="number-chart my-0 mx-auto">
                        <div class="chart-title">
                            <dv-decoration-11 style="width:150px;height:50px;color: #55E088;font-size: 13px;">线路数</dv-decoration-11>
                        </div>
                        <dv-digital-flop :config="options.routeCount" style="width: 100px;height: 50px;" />
                    </div>
                    <div class="number-chart me-5">
                        <div class="chart-title">
                            <dv-decoration-11 style="width:150px;height:50px;color: #55E088;font-size: 13px;">站点数</dv-decoration-11>
                        </div>
                        <dv-digital-flop :config="options.stationCount" style="width: 100px;height: 50px;" />
                    </div>
                </dv-border-box13>
            </div>
        </div>
    </dv-border-box11>
</template>

<script setup lang="ts" name="dashboard">
import { onMounted, reactive, ref } from 'vue';
import elementResizeDetectorMaker from 'element-resize-detector';
import { apiStatistics } from '@/utils/api';
import { isFullscreen, requestFullscreen, exitFullscreen } from '@/utils/fullscreen';
import type { ResponseData } from '@/utils/interface';

const rootBoxRef = ref();
const routeOfStationCountBorderBoxRef = ref();
const routeOfLengthRankBorderBoxRef = ref();
const lostStatusCountBorderBoxRef = ref();
const numbersBorderBoxRef = ref();

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
        content: '{nt}个',
    },
    lostStatusCount: {
        showOriginValue: true,
        digitalFlopUnit: '个',
        lineWidth: 24,
        digitalFlopStyle: {
            fill: 'pink',
        },
        data: [
            {
                name: '待认领',
                value: 3,
            },
            {
                name: '已认领',
                value: 2,
            },
        ],
    },
    routeOfStationCount: {
        data: [{
            name: '911',
            value: 3,
        }],
        unit: '个',
        rowNum: 6,
    },
    routeOfLengthRank: {
        data: [{
            name: '911',
            value: 4092,
        }],
        unit: '米',
        rowNum: 6,
    },
})

const toggleFullScreen = () => {
    isFullscreen() ? exitFullscreen() : requestFullscreen(rootBoxRef.value.$el);
};

const getData = () => {
    options.userCount.number[0] = -1;
    options.routeCount.number[0] = -1;
    options.stationCount.number[0] = -1;

    options.lostStatusCount.data[0].value = -1;
    options.lostStatusCount.data[1].value = -1;

    apiStatistics((data: ResponseData) => {
        options.userCount.number[0] = data.data.userCount;
        options.routeCount.number[0] = data.data.routeCount;
        options.stationCount.number[0] = data.data.stationCount;

        options.lostStatusCount.data[0].value = data.data.lostStatusCount[0].count;
        options.lostStatusCount.data[1].value = data.data.lostStatusCount[1].count;

        options.routeOfStationCount.data.length = 0;
        data.data.routeOfStationCount.forEach((item: { id: number, no: string, count: number }) => {
            options.routeOfStationCount.data.push({
                name: item.no + ' 路',
                value: item.count,
            })
        });

        options.routeOfLengthRank.data.length = 0;
        data.data.routeOfLengthRank.forEach((item: { id: number, no: string, length: number }) => {
            options.routeOfLengthRank.data.push({
                name: item.no + ' 路',
                value: Number(item.length.toFixed(2),)
            })
        });
    });
};

onMounted(() => {
    getData();

    routeOfStationCountBorderBoxRef.value.$el.style.padding = '15px 15px 55px';
    routeOfLengthRankBorderBoxRef.value.$el.style.padding = '15px 15px 55px';
    lostStatusCountBorderBoxRef.value.$el.style.padding = '15px';
    numbersBorderBoxRef.value.$el.style.padding = '15px';

    const erd =  elementResizeDetectorMaker();
    erd.listenTo(rootBoxRef.value.$el, (ele) => {
        const rootWidth = ele.clientWidth - 60;
        const rootHeight = ele.clientHeight - 140;

        if (rootHeight >= 640) {
            options.routeOfStationCount.rowNum = 8;
            options.routeOfLengthRank.rowNum = 8;
        }
        else {
            options.routeOfStationCount.rowNum = 5;
            options.routeOfLengthRank.rowNum = 5;
        }

        rootBoxRef.value.initWH();

        routeOfStationCountBorderBoxRef.value.$el.style.width = rootWidth * .4 + 'px';
        routeOfStationCountBorderBoxRef.value.$el.style.height = rootHeight * .62 + 'px';
        routeOfStationCountBorderBoxRef.value.$el.querySelector('.dv-scroll-ranking-board').style.height = rootHeight * .62 + 'px';
        routeOfStationCountBorderBoxRef.value.initWH();

        routeOfLengthRankBorderBoxRef.value.$el.style.width = rootWidth * .4 + 'px';
        routeOfLengthRankBorderBoxRef.value.$el.style.height = rootHeight * .62 + 'px';
        routeOfLengthRankBorderBoxRef.value.$el.querySelector('.dv-scroll-ranking-board').style.height = rootHeight * .62 + 'px';
        routeOfLengthRankBorderBoxRef.value.initWH();

        lostStatusCountBorderBoxRef.value.$el.style.width = rootWidth * .2 + 'px';
        lostStatusCountBorderBoxRef.value.$el.style.height = rootWidth * .2 + 'px';
        lostStatusCountBorderBoxRef.value.initWH();

        numbersBorderBoxRef.value.initWH();
    });
});
</script>

<style scoped lang="less">
.root-box {
    position: relative;
    box-sizing: border-box;
    border-radius: calc(var(--border-radius) * 2);
    padding: 60px 30px 30px 30px;
    width: 100%;
    height: 100%;
    background-color: #0C1426;

    .btn-full-screen, .btn-refresh {
        opacity: .3;
        position: absolute;
        top: -55px;
        right: 0;

        &:hover {
            opacity: 1;
        }
    }
    .btn-refresh {
        top: -56px;
        right: 30px;
    }
    .body {
        overflow: hidden;

        .chart-title {
            display: flex;
            align-items: center;
            padding: 5px 0 15px;
            color: #306fcd;
        }
        .number-charts {
            position: absolute;
            display: inline-block;
            margin-top: 8px;
            left: 50%;
            width: 60%;
            transform: translateX(-52%);

            :deep(.border-box-content) {
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .number-chart {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
        }
    }
}
</style>
