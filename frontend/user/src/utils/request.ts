import axios, { type AxiosInstance, type AxiosError, type AxiosResponse, type InternalAxiosRequestConfig } from 'axios';
import { showFailToast } from 'vant';

const service:AxiosInstance = axios.create({
    baseURL: 'http://127.0.0.1:8080',
    timeout: 5000,
    // 小于 500 的状态码不抛出错误
    validateStatus: (status: number) => status < 500,
});

// 请求拦截器
service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response: AxiosResponse) => {
        return response;
    },
    (error: AxiosError) => {
        if (error.code === 'ERR_NETWORK') return showFailToast('网络连接超时，请稍后重试');
        showFailToast('发生了一些错误，请联系管理员。')
        return Promise.reject(error);
    }
);

export interface RequestConfig {
    url: string;
    method?: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'OPTIONS';
    params?: any;
    data?: any;
    contentType?: 'FORM' | 'JSON';
    headers?: { [key: string]: string };
    token?: string,
    tokenType?: 'Bearer';
    successCallback?: Function;
    failureCallback?: Function;
    errorCallback?: Function;
}

/**
 * 封装通用请求逻辑并设置默认值, 支持回调函数、Promise、async/await 方式调用
 * @param url 请求链接
 * @param config 配置 (默认 "GET" "FORM")
 */
async function request(config: RequestConfig) {
    const {
        url,
        method = 'GET',
        params = {},
        data = {},
        contentType = 'FORM',
        headers = {},
        token = '',
        tokenType = 'Bearer',
        successCallback,
        failureCallback,
        errorCallback
    } = config || {};

    if (method.toLocaleLowerCase() === 'GET' && Object.keys(data).length !== 0) {
        console.warn('RequestError: "GET" 方法不允许携带 "data" 参数，请检查你的配置。');
    }
    if (!('Content-Type' in headers)) {
        if (contentType === 'FORM') headers['Content-Type'] = 'multipart/form-data';
        if (contentType === 'JSON') headers['Content-Type'] = 'application/json';
    }
    if (token && tokenType === 'Bearer') {
        headers['Authorization'] = `Bearer ${token}`;
    }

    return service({ url, method, params, data, headers }).then((res: AxiosResponse) => {
        if (res.data.code === 200 && successCallback) successCallback(res.data);
        if (res.data.code !== 200 && failureCallback) failureCallback(res.data, res.data.code, url);

        return Promise.resolve(res.data);
    }).catch((err: AxiosError) => {
        if (errorCallback) errorCallback(err);

        return Promise.reject(err);
    });
}

export default request;
