<script setup lang="ts">
import useGlobalStore from '@/stores/global';

interface Props {
    src: string | null,
    size?: string | number,
    round?: boolean,
    alt?: string,
};

const {
    src, size, round, alt,
} = withDefaults(defineProps<Props>(), {
    src: null,
    size: 64,
    round: true,
    alt: '头像图片',
});

const globalStore = useGlobalStore();
</script>

<template>
    <el-avatar
        class="avatar"
        :src="src || globalStore.defaultAvatarUrl"
        :size="size"
        :alt="alt"
        :shape="round ? 'circle' : 'square'"
    >
        <img :src="globalStore.defaultAvatarUrl" alt="头像加载失败">
    </el-avatar>
</template>

<style scoped>
</style>
