<script setup lang="ts">
import { ref } from 'vue';
import logo from '@/assets/img/logo.png';

const query = ref('');

const onSearch = () => {

};
</script>

<template>
    <div class="search-bar">
        <van-image :src="logo" width="50" height="50"></van-image>
        <van-search
            class="search-box"
            v-model="query"
            placeholder="搜索线路或站点"
            background="transparent"
            @search="onSearch()"
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
    background-color: #fff;

    .search-box {
        flex: 1 0 auto;

        :deep(.van-search__content) {
            border-radius: var(--border-radius);
        }
    }
}
</style>
