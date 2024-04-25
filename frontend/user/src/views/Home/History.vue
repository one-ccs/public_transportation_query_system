<script setup lang="ts">
import useHistoryStore from '@/stores/history';
import type { RouteBO, StationBO } from '@/utils/interface';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import IconBox from '@/components/IconBox.vue';

const historyStore = useHistoryStore();

const parseInfoText = (history: RouteBO & StationBO) => {
    if (history.no) {
        return `${history.stations![0].sitename} - ${history.stations![history.stations!.length - 1].sitename}`
    }
    if (history.sitename) {
        return `${history.routes?.length} 条公交线路`;
    }
    return '';
};
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <div class="body">
            <div class="history-card" v-for="history in historyStore.get">
                <icon-box class="icon" class-prefix="fa" name="bus"></icon-box>
                <div class="content">
                    <div class="title">{{ history.sitename || history.no }}</div>
                    <div class="describe">{{ parseInfoText(history) }}</div>
                </div>
                <van-icon
                    class="delete"
                    class-prefix="fa"
                    name="close"
                    @click="historyStore.delete(history)"
                ></van-icon>
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
    }
}
</style>
