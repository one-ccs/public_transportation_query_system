<script setup lang="ts">
import { ref } from 'vue';
import type { ResponseData } from '@/utils/interface';
import { apiUtilRoutePlanning } from '@/utils/api';
import i18n from '@/utils/i18n';
import RightSlideRouterView from '@/components/RightSlideRouterView.vue';
import IconBox from '@/components/IconBox.vue';

const startPoint = ref<any>("当前位置");
const endPoint = ref<any>(null);
const planningList = ref<{
    name: string;
    list: {methods: string, length: string, target: string}[];
}[]>([]);

const onExchangeClick = () => {
    const t = startPoint.value;
    startPoint.value = endPoint.value;
    endPoint.value = t;
};
const onSearch = () => {
    apiUtilRoutePlanning({
        startPoint: startPoint.value,
        endPoint: endPoint.value,
    }, (data: ResponseData) => {
        planningList.value = data.data;
    });
};
</script>

<template>
    <div class="client-wrapper">
        <right-slide-router-view />
        <van-cell-group class="path">
            <van-field :class="{ 'cp': startPoint === '当前位置'}" v-model="startPoint" placeholder="请输入起点" autocomplete="off"></van-field>
            <van-field :class="{ 'cp': endPoint === '当前位置'}" v-model="endPoint" placeholder="请输入终点" autocomplete="off"></van-field>
            <div class="exchange link-button" @click="onExchangeClick()">
                <icon-box name="exchange"></icon-box>
            </div>
            <div class="search link-button" @click="onSearch()">
                <icon-box name="search"></icon-box>
            </div>
        </van-cell-group>

        <div class="search-result">
            <div class="planning" v-for="planning in planningList">
                <div class="title">{{ planning.name }}</div>
                <div class="list">
                    <div class="content" v-for="(plan, i) in planning.list">
                        {{ `${i18n(i + 1, 'serialNumber')} ${plan.methods} ${plan.length} 到达 ${plan.target}` }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="less">
.client-wrapper {
    .path {
        position: relative;

        :deep(.cp) {
            input {
                color: #1989FA;
            }
        }
        .exchange {
            position: absolute;
            top: 50%;
            right: 60px;
            transform: translateY(-50%) rotateZ(90deg);
        }
        .search {
            position: absolute;
            top: 50%;
            right: 18px;
            transform: translateY(-50%);

            .icon-box {
                background-color: #9998;
            }
        }
    }
    .search-result {
        padding: var(--padding);

        .planning {
            margin-top: 8px;
            border-radius: var(--border-radius);
            padding: var(--padding);
            background-color: #fff;

            &:first-child {
                margin-top: unset;
            }
            .title {
                font-weight: bold;
                font-size: 1.1rem;
                line-height: 2rem;
            }
            .content {
                line-height: 1.5rem;
            }
        }
    }
}
</style>
