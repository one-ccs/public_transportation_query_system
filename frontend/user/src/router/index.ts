import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import HomeView from '@/views/Home.vue';


const routes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/nearby',
    },
    {
        path: '/',
        name: 'home',
        component: HomeView,
        children: [
            {
                path: 'search',
                name: 'search',
                meta: {
                    title: '搜索',
                },
                component: () => import('@/views/Home/Search.vue'),
            },
            {
                path: 'nearby',
                name: 'nearby',
                meta: {
                    title: '附近线路',
                },
                component: () => import('@/views/Home/Nearby.vue'),
                children: [
                    {
                        path: 'routeDetail',
                        name: 'nearbyRouteDetail',
                        meta: {
                            title: '线路详情',
                        },
                        component: () => import('@/views/Home/RouteDetail.vue'),
                    },
                    {
                        path: 'stationDetail',
                        name: 'nearbyStationDetail',
                        meta: {
                            title: '站点详情',
                        },
                        component: () => import('@/views/Home/StationDetail.vue'),
                    },
                ],
            },
            {
                path: 'history',
                name: 'history',
                meta: {
                    title: '最近使用',
                },
                component: () => import('@/views/Home/History.vue'),
                children: [
                    {
                        path: 'routeDetail',
                        name: 'historyRouteDetail',
                        meta: {
                            title: '线路详情',
                        },
                        component: () => import('@/views/Home/RouteDetail.vue'),
                    },
                    {
                        path: 'stationDetail',
                        name: 'historyStationDetail',
                        meta: {
                            title: '站点详情',
                        },
                        component: () => import('@/views/Home/StationDetail.vue'),
                    },
                ],
            },
            {
                path: 'planning',
                name: 'planning',
                meta: {
                    title: '线路规划',
                },
                component: () => import('@/views/Home/Planning.vue'),
                children: [
                    {
                        path: 'routeDetail',
                        name: 'planningRouteDetail',
                        meta: {
                            title: '线路详情',
                        },
                        component: () => import('@/views/Home/RouteDetail.vue'),
                    },
                    {
                        path: 'stationDetail',
                        name: 'planningStationDetail',
                        meta: {
                            title: '站点详情',
                        },
                        component: () => import('@/views/Home/StationDetail.vue'),
                    },
                ],
            },
        ],
    },
    {
        path: '/login',
        name: 'login',
        meta: {
            title: '登录',
        },
        component: () => import('@/views/Login.vue'),
    },
    {
        path: '/forgot',
        name: 'forgot',
        meta: {
            title: '找回密码',
        },
        component: () => import('@/views/Forgot.vue'),
    },
    {
        path: '/about',
        name: 'about',
        meta: {
            title: '关于',
        },
        component: () => import('@/views/About.vue'),
    },
    {
        path: '/:pathMatch(.*)*',
        name: '404',
        meta: {
            title: '404',
        },
        component: () => import('@/views/404.vue'),
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | 重庆公交查询系统`;

    next();
});

export default router;
