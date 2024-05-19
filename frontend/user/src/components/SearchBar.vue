<script setup lang="ts">
import useGlobalStore from '@/stores/global';
import logo from '@/assets/img/logo.png';

const globalStore = useGlobalStore();
</script>

<template>
    <div class="search-bar">
        <van-image :src="logo" width="50" height="50"></van-image>
        <van-search
            class="search-box"
            v-model="globalStore.search"
            placeholder="搜索线路或站点"
            background="transparent"
            autocomplete="off"
            @search="globalStore.onSearch"
            @click="$router.push('/search')"
        ></van-search>
        <div class="position">重庆</div>
    </div>
</template>

<style scoped lang="less">
.search-bar {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding: 0 18px;
    width: 100%;
    height: var(--search-bar-height);
    background-color: #fff;

    .search-box {
        flex: 1 0 auto;

        :deep(.van-search__content) {
            border-radius: var(--border-radius);
        }
    }
}
</style>
