import '@vant/touch-emulator';
import 'vant/es/toast/style';
import 'vant/es/dialog/style';
import 'vant/es/notify/style';
import 'vant/es/image-preview/style';

import 'font-awesome/css/font-awesome.min.css';

import '@/assets/css/main.css';

import { createApp } from 'vue';
import { Lazyload } from 'vant';

import App from './App.vue';
import pinia from './stores/pinia';
import router from './router';

import useGlobalStore from './stores/global';
import useNoticeStore from './stores/notice';
import useHistoryStore from './stores/history';

const app = createApp(App);

app.use(pinia);
app.use(router);
app.use(Lazyload);

// 异步初始化 store
setTimeout(() => {
    useGlobalStore().init();
    useNoticeStore().init();
    useHistoryStore().init();
}, 0);

app.mount('#app');
