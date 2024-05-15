import { api } from '.';
import type { Lost, PageQuery } from '@/utils/interface';
import useGlobalStore from '@/stores/global';

const globalStore = useGlobalStore();

/**
 * 获取失物招领信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLostGet(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/lost',
		params: {
            id,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 添加失物招领
 * @param lost 失物招领信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLostPut(lost: Lost, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/lost',
		method: 'PUT',
        data: {
            ...lost,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 修改失物招领
 * @param lost 失物招领信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLostPost(lost: Lost, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/lost',
		method: 'POST',
        data: {
            ...lost,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 删除失物招领
 * @param id 失物招领 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLostDelete(id: number | number[], successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/lost',
		method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 分页查询失物招领列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLostPageQuery(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/lost/pageQuery',
		params: {
            ...query,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}
