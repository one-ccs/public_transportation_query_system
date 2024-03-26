import { ElMessage } from 'element-plus';
import type { ResponseData } from '@/utils/interface';
import tableJson from '@/assets/table.json';

/**
 * 默认成功处理函数
 * @param data 请求数据
 */
export function defaultSuccessCallback(data: ResponseData) {
    ElMessage.success(data.message);
}

/**
 * 默认失败处理函数
 * @param data 请求数据
 * @param status 请求状态
 * @param url 请求地址
 */
export function defaultFailureCallback(data: ResponseData, status: number, url: string) {
    ElMessage.warning(data.message);
}

export function fetchData() {
    return Promise.resolve({
        code: 200,
        message: 'ok',
        data: tableJson
    });
};

export * from './userApi';
export * from './roleApi';
export * from './noticeApi';
