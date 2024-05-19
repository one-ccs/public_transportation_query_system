<script setup lang="ts">
import { ref } from 'vue';
import { showDialog } from 'vant';
import useGlobalStore from '@/stores/global';
import useHistoryStore from '@/stores/history';

const globalStore = useGlobalStore();
const historyStore = useHistoryStore();
const bodyRef = ref();

globalStore.onSearch = () => {

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
        <div class="body" ref="bodyRef">
            <van-back-top v-if="bodyRef" offset="120" bottom="80" z-index="1" teleport=".body" />
            <van-empty v-if="!historyStore.search.length" image="search" description="没有搜索历史哦" />
            <div class="btn-clear link-button">
                <icon-box name="delete" @click="onDeleteClick"></icon-box>
            </div>
        </div>
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    .body {

        .btn-clear {
            position: fixed;
            left: 50%;
            bottom: 80px;
            transform: translateX(-50%);
        }
    }
}
</style>
