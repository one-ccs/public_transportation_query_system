import { api } from '.';
import type { Notice, PageQuery } from '@/utils/interface';

/**
 * 获取公告信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiNoticeGet(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/notice',
		params: {
            id,
        },
        successCallback,
        failureCallback,
    });
}

/**
 * 添加公告
 * @param notice 公告信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiNoticePut(notice: Notice, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/notice',
		method: 'PUT',
        data: {
            ...notice,
        },
        contentType: 'JSON',
        successCallback,
        failureCallback,
    });
}

/**
 * 修改公告
 * @param notice 公告信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiNoticePost(notice: Notice, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/notice',
		method: 'POST',
        data: {
            ...notice,
        },
        contentType: 'JSON',
        successCallback,
        failureCallback,
    });
}

/**
 * 删除公告
 * @param id 公告 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiNoticeDelete(id: number | number[], successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/notice',
		method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        successCallback,
        failureCallback,
    });
}

/**
 * 分页查询公告列表
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiNoticePageQuery(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/notice/pageQuery',
		params: {
            ...query,
        },
        successCallback,
        failureCallback,
    });
}
