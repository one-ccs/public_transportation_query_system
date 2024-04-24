import { defaultSuccessCallback, defaultFailureCallback } from '.';
import type { Notice, PageQuery } from '@/utils/interface';
import request from '@/utils/request';
import useGlobalStore from '@/stores/global';

const globalStore = useGlobalStore();

/**
 * 获取公告信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiNoticeGet(id: number, successCallback = defaultSuccessCallback, failureCallback = defaultFailureCallback) {
    return request({
		url: '/api/notice',
		params: {
            id,
        },
        token: globalStore.token,
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
export function apiNoticePut(notice: Notice, successCallback = defaultSuccessCallback, failureCallback = defaultFailureCallback) {
    return request({
		url: '/api/notice',
		method: 'PUT',
        data: {
            ...notice,
        },
        contentType: 'JSON',
        token: globalStore.token,
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
export function apiNoticePost(notice: Notice, successCallback = defaultSuccessCallback, failureCallback = defaultFailureCallback) {
    return request({
		url: '/api/notice',
		method: 'POST',
        data: {
            ...notice,
        },
        contentType: 'JSON',
        token: globalStore.token,
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
export function apiNoticeDelete(id: number | number[], successCallback = defaultSuccessCallback, failureCallback = defaultFailureCallback) {
    return request({
		url: '/api/notice',
		method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        token: globalStore.token,
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
export function apiNoticePageQuery(query: PageQuery, successCallback = defaultSuccessCallback, failureCallback = defaultFailureCallback) {
    return request({
		url: '/api/notice/pageQuery',
		params: {
            ...query,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}
