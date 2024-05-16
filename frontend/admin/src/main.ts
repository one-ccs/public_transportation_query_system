import 'element-plus/dist/index.css';
import './assets/css/icon.css';
import '@/assets/css/font_830376_qzecyukz0s.css'

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

import App from './App.vue';
import router from './router';
import pinia from './stores/pinia';

import useGlobalStore from './stores/global';
import usePermissStore from './stores/permiss';
import useUserStore from './stores/user';


const app = createApp(App);

app.use(pinia);
app.use(router);

// 初始化 store
useGlobalStore().init();
useUserStore().init();

// 注册elementplus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
// 自定义权限指令
const permiss = usePermissStore();
app.directive('permiss', {
    mounted(el, binding) {
        if (!permiss.key.includes(String(binding.value))) {
            el['hidden'] = true;
        }
    },
});

app.mount('#app');
