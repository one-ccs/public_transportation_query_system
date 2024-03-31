import { defaultSuccessCallback, defaultFailureCallback } from '.';
import type { PageQuery, Station } from '@/utils/interface';
import request from '@/utils/request';
import useGlobalStore from '@/store/global';

const globalStore = useGlobalStore();

/**
 * 获取站点信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationGet(id: number, successCallback: Function = defaultSuccessCallback, failureCallback: Function = defaultFailureCallback) {
    return request('/api/station', {
        params: {
            id,
        },
        token: globalStore.data.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 添加站点
 * @param station 站点信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationPut(station: Station, successCallback: Function = defaultSuccessCallback, failureCallback: Function = defaultFailureCallback) {
    return request('/api/station', {
        method: 'PUT',
        data: {
            ...station,
        },
        contentType: 'JSON',
        token: globalStore.data.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 修改站点
 * @param station 站点信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationPost(station: Station, successCallback: Function = defaultSuccessCallback, failureCallback: Function = defaultFailureCallback) {
    return request('/api/station', {
        method: 'POST',
        data: {
            ...station,
        },
        contentType: 'JSON',
        token: globalStore.data.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 删除站点
 * @param id 站点 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationDelete(id: number | number[], successCallback: Function = defaultSuccessCallback, failureCallback: Function = defaultFailureCallback) {
    return request('/api/station', {
        method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        token: globalStore.data.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 分页查询站点列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationPageQuery(query: PageQuery, successCallback: Function = defaultSuccessCallback, failureCallback: Function = defaultFailureCallback) {
    return request('/api/station/pageQuery', {
        params: {
            ...query,
        },
        token: globalStore.data.token,
        successCallback,
        failureCallback,
    });
}
