import { api } from '.';


export function apiUploadAvatar(base64: string, successCallback?: Function, failureCallback?: Function) {
    return api({
		url: '/api/file/upload/avatar',
        method: 'POST',
        data: {
            base64,
        },
        successCallback,
        failureCallback,
    });
}
