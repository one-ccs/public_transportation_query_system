import { api } from '.';
import type { PageQuery, Route } from '@/utils/interface';
import useGlobalStore from '@/stores/global';

const globalStore = useGlobalStore();

/**
 * 获取线路信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRouteGet(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/route',
		params: {
            id,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 添加线路
 * @param route 线路信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRoutePut(route: Route, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/route',
		method: 'PUT',
        data: {
            ...route,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 修改线路
 * @param route 线路信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRoutePost(route: Route, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/route',
		method: 'POST',
        data: {
            ...route,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 删除线路
 * @param id 线路 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRouteDelete(id: number | number[], successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/route',
		method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 分页查询线路列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRoutePageQuery(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/route/pageQuery',
		params: {
            ...query,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}
