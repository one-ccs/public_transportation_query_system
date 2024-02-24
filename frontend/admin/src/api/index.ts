import { ElMessage } from 'element-plus';
import { localLoad, localSave } from '../utils/local';
import request from '../utils/request';
import encryptMD5 from '../utils/encryptMD5';

const TOKEN_NAME = 'token';

/**
 * 默认成功处理函数
 * @param message 提示消息
 */
function defaultSuccess(data: any) {
    ElMessage.success(data.message);
}

/**
 * 默认失败处理函数
 * @param url 请求地址
 * @param code 状态码
 */
function defaultFailure(data: any, status: number, url: string) {
    ElMessage.warning(data.message);
}

/**
 * 登录
 * @param username 用户名
 * @param password 密码
 * @param remember 是否记住
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiLogin(username: string, password: string, remember: boolean, success?: Function, failure: Function = defaultFailure) {
    return request('/api/user/login', {
        method: 'post',
        data: {
            username,
            password: encryptMD5(password),
            remember
        },
        contentType: 'form',
        success: (data: any) => {
            localSave(TOKEN_NAME, data.data.token);
            success && success(data);
        }
        ,failure
    });
}

/**
 * 分页获取用户列表
 * @param query 分页查询信息
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiPageUser(query: object, success?: Function, failure: Function = defaultFailure) {
    return request('/api/user', {
        params: query,
        token: localLoad(TOKEN_NAME)!,
        success,
        failure
    });
}

/**
 * 添加用户
 * @param user 用户信息
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiAddUser(user: any, success?: Function, failure: Function = defaultFailure) {
    return request('/api/user', {
        method: 'put',
        data: {
            username: user.username,
            password: encryptMD5(user.password),
            email: user.email || null,
            roles: user.roles
        },
        token: localLoad(TOKEN_NAME)!,
        success,
        failure
    });
}

/**
 * 修改用户
 * @param user 用户信息
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiModifyUser(user: any, success?: Function, failure: Function = defaultFailure) {
    return request('/api/user', {
        method: 'post',
        data: {
            id: user.id,
            username: user.username,
            password: user.passwordModified ? encryptMD5(user.password): null,
            email: user.email,
            status: user.status,
            roles: user.roles
        },
        token: localLoad(TOKEN_NAME)!,
        success,
        failure
    });
}

/**
 * 删除用户信息
 * @param id 用户 id
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiDeleteUser(id: number, success?: Function, failure: Function = defaultFailure) {
    return request('/api/user', {
        method: 'delete',
        data: { id },
        token: localLoad(TOKEN_NAME)!,
        success,
        failure
    })
}

/**
 * 获取角色列表
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiGetRoles(success?: Function, failure?: Function) {
    return request('/api/role', {
        token: localLoad(TOKEN_NAME)!,
        success,
        failure
    });
}

const fetchData = () => {
    return request('http://127.0.0.1:5173/table.json');
};

export {
    apiLogin,
    apiPageUser,
    apiAddUser,
    apiModifyUser,
    apiDeleteUser,
    apiGetRoles,
    fetchData
}
