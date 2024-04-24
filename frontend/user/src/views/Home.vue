<script setup lang="ts">
import { RouterView } from 'vue-router'
import SearchBar from '@/components/SearchBar.vue';
import Tabbar from '@/components/Tabbar.vue';
</script>

<template>
    <div class="view">
        <search-bar></search-bar>
        <div class="client-view">
            <router-view v-slot="{ Component, route }">
                <transition name="fade" mode="out-in">
                    <component :is="Component"></component>
                </transition>
            </router-view>
        </div>
        <tabbar></tabbar>
    </div>
</template>

<style scoped lang="less">
.view {
    .client-view {
        width: 100%;
        height: calc(100% - var(--van-tabbar-height) - var(--search-bar-height));
        background-color: var(--background-color);
    }
}
</style>
