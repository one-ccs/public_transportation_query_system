import { showFailToast } from "vant";


function defaultPositionErrorCallback<PositionErrorCallback>(error: { code: number, message: string }) {
    if (error.code === 1) showFailToast('操作失败\n没有定位权限');
}

/**
 * 获取当前定位
 * @param successCallback 成功回调函数
 * @param errorCallback 失败回调函数
 * @param options 配置选项
 */
export function getCurrentPosition(successCallback: PositionCallback, errorCallback: PositionErrorCallback = defaultPositionErrorCallback, options?: PositionOptions) {
    navigator.geolocation?.getCurrentPosition(successCallback, errorCallback, options);
}
