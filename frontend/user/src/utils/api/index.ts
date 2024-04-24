import { showFailToast, showSuccessToast } from 'vant';
import type { ResponseData } from '@/utils/interface';

/**
 * 默认成功处理函数
 * @param data 请求数据
 */
export function defaultSuccessCallback(data: ResponseData) {
    showSuccessToast(data.message);
}

/**
 * 默认失败处理函数
 * @param data 请求数据
 * @param status 请求状态
 * @param url 请求地址
 */
export function defaultFailureCallback(data: ResponseData, status: number, url: string) {
    showFailToast(data.message);
}

export * from './userApi';
export * from './roleApi';
export * from './routeApi';
export * from './stationApi';
export * from './noticeApi';
