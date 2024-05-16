import { api } from '.';


function apiFileUpload(type: string, base64: string, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: `/api/file/upload/${type}`,
        method: 'POST',
        data: {
            base64,
        },
        successCallback,
        failureCallback,
    });
}

/**
 * 上传头像
 * @param base64 图片 base64 字符串
 * @param successCallback
 * @param failureCallback
 * @returns
 */
export function apiUploadAvatar(base64: string, successCallback?: Function, failureCallback?: Function) {
    return apiFileUpload('avatar', base64, successCallback, failureCallback);
}

/**
 * 上传失物招领图片
 * @param base64 图片 base64 字符串
 * @param successCallback
 * @param failureCallback
 * @returns
 */
export function apiUploadLost(base64: string, successCallback?: Function, failureCallback?: Function) {
    return apiFileUpload('lost', base64, successCallback, failureCallback);
}
