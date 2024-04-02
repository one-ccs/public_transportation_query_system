<script setup lang="ts">
import useBadgeStore from '@/stores/badge';

const badgeStore = useBadgeStore();

const tabbarList = [
    {
        icon: 'chat-o',
        text: '附近线路',
        badge: badgeStore.notice,
        to: '/nearby',
    },
    {
        icon: 'apps-o',
        text: '最近使用',
        badge: badgeStore.notice,
        to: '/history',
    },
    {
        icon: 'contact-o',
        text: '线路规划',
        badge: badgeStore.notice,
        to: '/planning',
    },
];
</script>

<template>
    <van-tabbar route class="tabbar" :fixed="false" safe-area-inset-bottom>
        <van-tabbar-item
            v-for="tabbar in tabbarList"
            :key="tabbar.to"
            :icon="tabbar.icon"
            :badge="tabbar.badge"
            :to="tabbar.to"
            replace
        >{{ tabbar.text }}</van-tabbar-item>
    </van-tabbar>
</template>

<style scoped lang="less">
</style>
