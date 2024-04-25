<script setup lang="ts">
import { computed } from 'vue';

const props = withDefaults(defineProps<{
    classPrefix?: string;
    name?: string;
    size?: string | number;
    fontSize?: string | number;
}>(), {
    classPrefix: "",
    name: "",
    size: 38,
    fontSize: 24,
});

const _size = computed(() => {
    if (typeof props.size === "number") return props.size + "px";
    return props.size;
});
const _fontSize = computed(() => {
    if (typeof props.fontSize === "number") return props.fontSize + "px";
    return props.fontSize;
});
</script>

<template>
    <div class="icon-box">
        <van-icon class="icon" :class-prefix="props.classPrefix" :name="props.name" ></van-icon>
    </div>
</template>

<style scoped lang="less">

.icon-box {
    display: flex;
    align-items: center;
    justify-content: center;
    flex: 0 0 auto;
    border-radius: 50%;
    width: v-bind(_size);
    height: v-bind(_size);
    font-size: v-bind(_fontSize);
    color: #fff;
    background-color: #3498DB;

    .icon {
        position: relative;

        &::before {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
    }
}
</style>
