import { api } from '.';
import type { UserLogin, UserVO, ResponseData, PageQuery } from '@/utils/interface';
import encryptMD5 from '@/utils/encryptMD5';
import useGlobalStore from '@/stores/global';

const globalStore = useGlobalStore();

/**
 * 获取用户信息
 * @param id 用户 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiUserGet(id: number, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/user',
		params: {
            id,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 添加用户
 * @param user 用户信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiUserPut(user: UserVO, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/user',
		method: 'PUT',
        data: {
            ...user,
            password: user.password ? encryptMD5(user.password) : null,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 修改用户
 * @param user 用户信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiUserPost(user: UserVO, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/user',
		method: 'POST',
        data: {
            ...user,
            password: user.passwordModified ? encryptMD5(user.password!): null,
        },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 删除用户信息
 * @param id 用户 id
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiUserDelete(id: number | number[], successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/user',
		method: 'DELETE',
        data: id.hasOwnProperty('length') ? { ids: id } : { id },
        contentType: 'JSON',
        token: globalStore.token,
        successCallback,
        failureCallback,
    })
}

/**
 * 分页获取用户列表
 * @param query 分页查询信息
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiPageUser(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/user/pageQuery',
		params: {
            ...query,
        },
        token: globalStore.token,
        successCallback,
        failureCallback,
    });
}

/**
 * 登录
 * @param username 用户名
 * @param password 密码
 * @param remember 是否记住
 * @param successCallback 成功回调函数
 * @param failureCallback 失败回调函数
 * @returns Promise
 */
export function apiLogin(user: UserLogin, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/user/login',
		method: 'POST',
        data: {
            ...user,
            password: user.password ? encryptMD5(user.password!): null,
        },
        successCallback: (data: ResponseData) => {
            globalStore.setToken(data.data.token);
            globalStore.save();
            successCallback && successCallback(data);
        },
        failureCallback,
    });
}
