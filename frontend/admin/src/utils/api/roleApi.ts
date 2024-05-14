import { api } from '.';
import type { PageQuery, Role } from '@/utils/interface';
import useGlobalStore from '@/stores/global';

const globalStore = useGlobalStore();

/**
 * 获取角色信息
 * @param id 角色 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRoleGet(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/role',
		params: {
            id,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 添加角色
 * @param role 角色信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRolePut(role: Role, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/role',
		method: 'PUT',
        data: {
            ...role,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 修改角色
 * @param role 角色信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRolePost(role: Role, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/role',
		method: 'POST',
        data: {
            ...role,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 删除角色
 * @param id 角色 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRoleDelete(id: number | number[], successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/role',
		method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 分页查询角色列表
 * @param query 分页查询信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiRolePageQuery(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/role/pageQuery',
		params: {
            ...query,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}
