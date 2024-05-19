import { api } from '.';
import type { NearbyQuery, PageQuery, Station } from '@/utils/interface';

/**
 * 获取站点信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationGet(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/station',
		params: {
            id,
        },
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
export function apiStationPageQuery(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/station/pageQuery',
		params: {
            ...query,
        },
        successCallback,
        failureCallback,
    });
}

/**
 * 获取附件站点列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationNearby(query: NearbyQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/station/nearby',
		params: {
            ...query,
        },
        successCallback,
        failureCallback,
    });
}

/**
 * 获取经过站点的线路列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiStationRoutes(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/station/routes',
		params: {
            id,
        },
        successCallback,
        failureCallback,
    });
}
