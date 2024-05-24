<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { showFailToast } from 'vant';
import type { Lost, LostQuery, ResponseData } from '@/utils/interface';
import { apiLostPageQuery } from '@/utils/api';
import BackNavBar from '@/components/BackNavBar.vue';
import useGlobalStore from '@/stores/global';

const globalStore = useGlobalStore();
const lostListRef = ref();
const query = reactive<LostQuery>({
    pageIndex: 1,
    pageSize: 10,
    query: '',
    startDatetime: '',
    endDatetime: '',
    status: 0,
});
const total = ref(0);
const lostList = reactive<Lost[]>([]);
const isLoading = ref(false);

// 分页查询
const getPageData = () => {
    isLoading.value = true;

    apiLostPageQuery(query, (data: ResponseData) => {
        isLoading.value = false;
        total.value = data.data.total;
        lostList.length = 0;
        lostList.push(...data.data.list);
    }, (data: ResponseData) => {
        isLoading.value = false;
        showFailToast(data.message);
    });
};
// 搜索事件
const onSearch = () => {
    query.pageIndex = 1;
    getPageData();
};

onMounted(() => {
    getPageData();
});
</script>

<template>
    <div class="view">
        <back-nav-bar class="view-header" />
        <div class="view-container">
            <van-search
                class="search-box"
                v-model="query.query"
                placeholder="请输入拾取地点、描述"
                autocomplete="off"
                @search="onSearch"
                @clear="onSearch"
            ></van-search>
            <van-loading v-if="isLoading">加载中...</van-loading>
            <van-empty v-if="!lostList.length" image="search" description="暂无数据" />

            <div class="lost-list" ref="lostListRef">
                <div class="lost" v-for="lost in lostList">
                    <div class="info">
                        <div class="pick-datetime">
                            <div class="title">拾取时间</div>
                            <div class="content">{{ lost.pickDatetime?.substring(0, 10) }}</div>
                        </div>
                        <div class="address">
                            <div class="title">拾取地点</div>
                            <div class="content">{{ lost.address }}</div>
                        </div>
                        <div class="describe">
                            <div class="title">描述</div>
                            <div class="content">{{ lost.describe }}</div>
                        </div>
                    </div>
                    <div class="image">
                        <van-image :src="globalStore.lostApi + lost.imgUrl" :width="100" />
                    </div>
                </div>

                <van-back-top v-if="lostListRef" offset="120" bottom="80" z-index="1" teleport=".lost-list" />
            </div>

            <van-pagination
                class="van-safe-area-bottom"
                v-model="query.pageIndex"
                :items-per-page="query.pageSize"
                :total-items="total"
                :show-page-size="5"
                force-ellipses
                @change="getPageData()"
            />
        </div>
    </div>
</template>

<style scoped lang="less">
.view {
    .view-container {
        .search-box {
            position: static;
            top: 0;
            left: 0;
            padding: 8px;
        }
        .van-loading {
            z-index: 9;
            position: absolute;
            width: 100%;
            height: 100%;
            background-color: var(--background-color);
        }
        .lost-list {
            padding: var(--padding);
            height: calc(100% - var(--back-nav-bar-height) - 50px);
            overflow-y: auto;

            .lost {
                display: flex;
                align-items: center;
                background-color: #fff;
                margin-top: 8px;
                border-radius: var(--border-radius);
                padding: var(--padding);

                &::first-child {
                    margin-top: unset;
                }
                .info {
                    line-height: 1.5rem;
                    .title {
                        font-size: 1.1rem;
                        font-weight: bold;
                    }
                }
                .image {
                    margin-left: auto;
                }
            }
        }
    }
}
</style>
