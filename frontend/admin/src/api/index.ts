import { ElMessage } from 'element-plus';
import request from '../utils/request';
import encryptMD5 from '../utils/encryptMD5';


const authItemName = 'access_token';


function defaultSuccess(data: any) {
    ElMessage.success(data.message);
}

function defaultFailure(url: string, code: number, message: string) {
    // console.warn(`请求失败：${url} ${code} ${message}`);
    ElMessage.warning(message);
}

function defaultError(err: any) {
    // console.error(err);
    ElMessage.error('发生了一些错误，请联系管理员。')
}

function deleteAccessToken() {
    localStorage.removeItem(authItemName);
    sessionStorage.removeItem(authItemName);
}

function takeAccessToken() {
    const str = localStorage.getItem(authItemName) || sessionStorage.getItem(authItemName) || '';
    const authObj = JSON.parse(str);

    if (authObj.expire <= new Date()) {
        deleteAccessToken();
        ElMessage.warning('登录已过期，请重新登录！');
        setTimeout(() => {
            location.href = '/login';
        }, 1200);

        return {token: null};
    }
    return authObj;
}

function storeAccessToken(token: string, expire: Date, remember = false) {
    const authObj = {token, expire};
    const str = JSON.stringify(authObj);

    if (remember) {
        localStorage.setItem(authItemName, str);
    } else {
        sessionStorage.setItem(authItemName, str);
    }
}

interface FetchConfig {
    method?: string;
    params?: any;
    data?: any;
    headers?: { [key: string]: string };
    contentType?: 'form' | 'json';
    success?: Function;
    failure?: Function;
    error?: Function;
}

/**
 * 封装通用请求逻辑并设置默认值
 * @param url 请求链接
 * @param config 配置
 */
function fetch(url: string, config: FetchConfig) {
    let {
        method = 'GET',
        params = {},
        data = {},
        headers = {},
        contentType = 'form',
        success = defaultSuccess,
        failure = defaultFailure,
        error = defaultError
    } = config;

    if (!('Authorization' in headers)) headers['Authorization'] = `Bearer ${takeAccessToken().token}`;
    if (!('Content-Type' in headers)) {
        if (contentType === 'form') headers['Content-Type'] = 'multipart/form-data';
        if (contentType === 'json') headers['Content-Type'] = 'application/json';
    };
    if (method.toLocaleLowerCase() === 'get' && Object.keys(data).length !== 0) console.error('FetchError: GET 方法不允许携带 data 参数，请检查你的配置。');

    request(url, { method, params, data, headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else if (res.data.code == 401) {
            ElMessage.warning('登录已过期，即将跳转登录界面！');
            setTimeout(() => {
                location.href = '/login';
            }, 1200);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

/**
 * 登录
 * @param username 用户名
 * @param password 密码
 * @param remember 是否记住
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiLogin(username: string, password: string, remember: boolean, success?: Function, failure?: Function) {
    fetch('/api/user/login', {
        method: 'post',
        data: {
            username, password: encryptMD5(password)
        },
        success: (data: any) => {
            storeAccessToken(data.data.token, data.data.expire);
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
function apiPageUser(query: object, success?: Function, failure?: Function) {
    fetch('/api/user', {
        params: query,
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
function apiAddUser(user: object, success?: Function, failure?: Function) {
    fetch('/api/user', {
        method: 'put',
        data: user,
        contentType: 'json',
        success,
        failure
    });
}

function apiModifyUser(user: object, success?: Function, failure?: Function) {
    fetch('/api/user', {
        method: 'post',
        data: user,
        contentType: 'json',
        success,
        failure
    });
}

/**
 * 获取角色列表
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
function apiGetRoles(success?: Function, failure?: Function) {
    fetch('/api/role', {
        success,
        failure
    });
}

const fetchData = () => {
    return request({
        url: 'http://127.0.0.1:5173/table.json',
        method: 'get'
    });
};

export {
    apiLogin,
    apiPageUser,
    apiAddUser,
    apiModifyUser,
    apiGetRoles,
    fetchData
}
