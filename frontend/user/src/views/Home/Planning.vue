<script setup lang="ts">
import { ref } from 'vue';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import IconBox from '@/components/IconBox.vue';

const startPoint = ref<any>(null);
const endPoint = ref<any>(null);
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <van-cell-group class="path">
            <van-field v-model="startPoint" placeholder="请输入起点" autocomplete="off"></van-field>
            <van-field v-model="endPoint" placeholder="请输入终点" autocomplete="off"></van-field>
            <div class="exchange link-button">
                <icon-box name="exchange"></icon-box>
            </div>
        </van-cell-group>
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    .path {
        position: relative;

        .exchange {
            position: absolute;
            top: 50%;
            right: 18px;
            transform: translateY(-50%) rotateZ(90deg);
        }
    }
}
</style>
