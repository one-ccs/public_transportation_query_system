<script setup lang="ts">
import { ref } from 'vue';
import { showDialog } from 'vant';
import type { RouteBO, StationBO } from '@/utils/interface';
import useHistoryStore from '@/stores/history';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import IconBox from '@/components/IconBox.vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const historyStore = useHistoryStore();
const bodyRef = ref();

const parseInfoText = (history: RouteBO & StationBO) => {
    if (history.no) {
        return `${history.stations![0].sitename} - ${history.stations![history.stations!.length - 1].sitename}`
    }
    if (history.sitename) {
        return `${history.routes?.length} 条公交线路`;
    }
    return '';
};

const onHistoryClick = (history: RouteBO & StationBO) => {
    // 站点
    if (history.sitename) {
        router.push({
            name: 'historyStationDetail',
            query: {
                stationId: history.id,
            },
        });
    }
    // 线路
    if (history.no) {
        router.push({
            name: 'historyRouteDetail',
            query: {
                routeId: history.id,
            },
        });
    }
};

// 清空历史记录
const onDeleteClick = () => {
    showDialog({
        showCancelButton: true,
        message: '确认清空历史记录吗?',
    }).then(() => {
        historyStore.clear();
    }).catch(() => {});
};
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <div class="body" ref="bodyRef">
            <div class="history-card link-button" v-for="history in historyStore.history" @click="onHistoryClick(history)">
                <icon-box class="icon" class-prefix="fa" name="bus"></icon-box>
                <div class="content">
                    <div class="title">{{ history.sitename || history.no }}</div>
                    <div class="describe">{{ parseInfoText(history) }}</div>
                </div>
                <van-icon
                    class="delete"
                    class-prefix="fa"
                    name="close"
                    @click="historyStore.deleteHistory(history)"
                ></van-icon>
            </div>
            <van-back-top v-if="bodyRef" offset="120" bottom="80" z-index="1" teleport=".body" />
            <van-empty v-if="!historyStore.history.length" image="search" description="没有历史记录哦" />
            <div class="btn-clear link-button">
                <icon-box name="delete" @click="onDeleteClick"></icon-box>
            </div>
        </div>
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    padding: 12px 0;

    .body {
        .history-card {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            border-bottom: 1px solid #ddd;
            padding: 12px 18px;
            background-color: #fff;

            &:last-child {
                border-bottom: none;
            }

            .icon {
                margin-right: 12px;
            }
            .content {
                flex: 1 0 auto;

                .describe {
                    margin-top: 5px;
                    color: #666;
                    font-size: .9rem;
                }
            }
            .delete {
                flex: 0 0 auto;
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
</style>
