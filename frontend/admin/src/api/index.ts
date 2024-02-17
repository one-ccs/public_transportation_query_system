import { ElMessage } from 'element-plus';
import request from '../utils/request';
import encryptMD5 from '../utils/encryptMD5';


const authItemName = 'access_token';


function defaultSuccess(res: any) {
    ElMessage.success(res.message);
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
        return null;
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

function fetchGet(url: string, headers: any = {}, success: Function, failure: Function, error = defaultError) {
    request.get(url, { headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function fetchPost(url: string, data: any, headers: any = {}, success: Function, failure: Function, error = defaultError) {
    headers['Content-Type'] = 'multipart/form-data';

    request.post(url, data, { headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function fetchPut(url: string, data: any, headers: any = {}, success: Function, failure: Function, error = defaultError) {
    headers['Content-Type'] = 'multipart/form-data';

    request.put(url, data, { headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function fetchDelete(url: string, data: any, headers: any = {}, success: Function, failure: Function, error = defaultError) {
    headers['Content-Type'] = 'multipart/form-data';

    request.delete(url, { headers, data }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function loginApi(username: string, password: string, remember: boolean, success: Function, failure = defaultFailure) {
    fetchPost('/api/user/login', { username, password: encryptMD5(password)}, {}, (data: any) => {
        storeAccessToken(data.data.token, data.data.expire);
        success(data);
    }, failure);
}

const fetchData = () => {
    return request({
        url: 'http://127.0.0.1:5173/table.json',
        method: 'get'
    });
};


export {
    loginApi,
    fetchData
}
