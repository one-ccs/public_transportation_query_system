import { ElMessage } from 'element-plus';
import request from '../utils/request';


const authItemName = 'access_token';


function defaultSuccess(res : any) {
    ElMessage.success(res.message);
}

function defaultFailure(url : string, code : number, message : string) {
    // console.warn(`请求失败：${url} ${code} ${message}`);
    ElMessage.warning(message);
}

function defaultError(err : any) {
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

function storeAccessToken(token : string, expire : Date, remember = false) {
    const authObj = {token, expire};
    const str = JSON.stringify(authObj);

    if (remember) {
        localStorage.setItem(authItemName, str);
    } else {
        sessionStorage.setItem(authItemName, str);
    }
}

function fetchPost(url: string, data: any, headers: any, success : Function, failure : Function, error = defaultError) {
    request.post(url, data, { headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function fetchParamPost(url: string, params: any, headers: any, success : Function, failure : Function, error = defaultError) {
    request({ url, method: 'post', params, headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function fetchGet(url: string, headers = {}, success : Function, failure : Function, error = defaultError) {
    request.get(url, { headers }).then(res => {
        if (res.data.code == 200) {
            success(res.data);
        } else {
            failure(url, res.data.code, res.data.message);
        }
    }).catch(err => error(err));
}

function loginApi(username: string, password : string, remember: boolean, success : Function, failure = defaultFailure) {
    fetchParamPost('/api/auth/login', { username, password}, {}, (data : any) => {
        storeAccessToken(data.token, data.expire);
        success(data);
    }, failure);
}

export {
    loginApi,
}
