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
                <transition name="van-fade" mode="out-in">
                    <component :is="Component"></component>
                </transition>
            </router-view>
        </div>
        <tabbar></tabbar>
    </div>
</template>

<style scoped lang="less">
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.view {
    .client-view {
        width: 100%;
        height: calc(100% - var(--van-tabbar-height));
        background-color: var(--background-color);
    }
}
</style>
