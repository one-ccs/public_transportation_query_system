<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="sidebar.collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-sub-menu :index="item.index" :key="item.index" v-permiss="item.permiss">
                        <template #title>
                            <el-icon>
                                <component :is="item.icon"></component>
                            </el-icon>
                            <span>{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in <any>item.subs">
                            <el-sub-menu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                                v-permiss="item.permiss"
                            >
                                <template #title>{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-sub-menu>
                            <el-menu-item v-else :index="subItem.index" v-permiss="item.permiss">
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-sub-menu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index" v-permiss="item.permiss">
                        <el-icon>
                            <component :is="item.icon"></component>
                        </el-icon>
                        <template #title>{{ item.title }}</template>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import useSidebarStore from '@/stores/sidebar';
import { useRoute } from 'vue-router';

const items = [
    {
        icon: 'Odometer',
        index: '/dashboard',
        title: '系统首页',
        permiss: '1',
    },
    {
        icon: 'User',
        index: '1',
        title: '用户管理',
        permiss: '2',
        subs: [
            {
                index: '/users',
                title: '普通用户管理',
                permiss: '2',
            },
            {
                index: '/admins',
                title: '管理员管理',
                permiss: '2',
            },
            {
                index: '/roles',
                title: '角色管理',
                permiss: '2',
            },
        ],
    },
    {
        icon: 'SemiSelect',
        index: '/route',
        title: '线路管理',
        permiss: '3',
    },
    {
        icon: 'Place',
        index: '/station',
        title: '站点管理',
        permiss: '4',
    },
    {
        icon: 'ChatLineSquare',
        index: '/notice',
        title: '公告管理',
        permiss: '5',
    },
    {
        icon: 'Sell',
        index: '/lost',
        title: '失物招领管理',
        permiss: '6',
    },
    {
        icon: 'Calendar',
        index: '2',
        title: '表格相关',
        permiss: '7',
        subs: [
            {
                index: '/table',
                title: '常用表格',
                permiss: '7',
            },
            {
                index: '/import',
                title: '导入Excel',
                permiss: '7',
            },
            {
                index: '/export',
                title: '导出Excel',
                permiss: '7',
            },
        ],
    },
    {
        icon: 'DocumentCopy',
        index: '/tabs',
        title: 'tab选项卡',
        permiss: '8',
    },
    {
        icon: 'Edit',
        index: '3',
        title: '表单相关',
        permiss: '9',
        subs: [
            {
                index: '/form',
                title: '基本表单',
                permiss: '9',
            },
            {
                index: '/upload',
                title: '文件上传',
                permiss: '9',
            },
            {
                index: '4',
                title: '三级菜单',
                permiss: '9',
                subs: [
                    {
                        index: '/editor',
                        title: '富文本编辑器',
                        permiss: '9',
                    },
                    {
                        index: '/markdown',
                        title: 'markdown编辑器',
                        permiss: '9',
                    },
                ],
            },
        ],
    },
    {
        icon: 'Setting',
        index: '/icon',
        title: '自定义图标',
        permiss: '10',
    },
    {
        icon: 'Setting',
        index: '/icon2',
        title: '自定义图标2',
        permiss: '11',
    },
    {
        icon: 'PieChart',
        index: '/charts',
        title: 'schart图表',
        permiss: '12',
    },
    // {
    //     icon: 'Warning',
    //     index: '/permission',
    //     title: '权限管理',
    //     permiss: '13',
    // },
];

const route = useRoute();
const onRoutes = computed(() => {
    return route.path;
});

const sidebar = useSidebarStore();
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    min-height: 100%;
}
</style>
