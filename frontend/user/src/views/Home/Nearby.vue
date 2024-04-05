<script setup lang="ts">
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import { reactive } from 'vue';

const center = reactive({
    lng: 106.577052,
    lat: 29.557259,
});
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <baidu-map class="map" :center="center" :zoom="5"></baidu-map>
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    .map {
        width: 100%;
        height: 300px;
    }
}
</style>
