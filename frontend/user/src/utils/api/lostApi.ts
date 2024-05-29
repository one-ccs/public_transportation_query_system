import { api } from ".";
import type { LostQuery } from "../interface";

/**
 * 分页查询失物招领列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLostPageQuery(query: LostQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/lost/pageQuery',
		params: {
            ...query,
        },
        successCallback,
        failureCallback,
    });
}
