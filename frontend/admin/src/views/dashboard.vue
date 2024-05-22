<template>
    <dv-border-box11
        class="root-box"
        ref="rootBoxRef"
        title="重庆公交查询系统"
    >
        <div class="btn-full-screen link-button" @click="toggleFullScreen()">
                <el-icon v-if="!isFullscreen()" class="tooltip left" tooltip="全屏" :size="18" color="#ddd">
                    <FullScreen />
                </el-icon>
                <el-icon v-else class="tooltip left" tooltip="退出全屏" :size="18" color="#ddd">
                    <CloseBold />
                </el-icon>
        </div>

        <!-- <dv-border-box12>

        </dv-border-box12> -->
    </dv-border-box11>
</template>

<script setup lang="ts" name="dashboard">
import { isFullscreen, requestFullscreen, exitFullscreen } from '@/utils/fullscreen';
import { onMounted, ref } from 'vue';

const rootBoxRef = ref();

const toggleFullScreen = () => {
    isFullscreen() ? exitFullscreen() : requestFullscreen(rootBoxRef.value.$el);
};

onMounted(() => {
    setTimeout(() => {
        rootBoxRef.value?.initWH();
    }, 500);
});
</script>

<style scoped lang="less">
    .root-box {
        position: relative;
        border-radius: calc(var(--border-radius) * 2);
        width: 100%;
        height: 100%;
        background-color: #3B3B3B;

        .btn-full-screen {
            opacity: .3;
            position: absolute;
            top: 5px;
            right: 20px;

            &:hover {
                opacity: 1;
            }
        }
    }
</style>
