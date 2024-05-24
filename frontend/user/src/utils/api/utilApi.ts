import { api } from '.';
import type { PageQuery } from '../interface';

export function apiUtilSearch(query: PageQuery, successCallback?: Function, failureCallback?: Function) {
    return api({
        url: '/api/util/search',
        params: {
            ...query,
        },
        successCallback,
        failureCallback,
    });
}

export function apiUtilRoutePlanning(query: {}, successCallback?: Function, failureCallback?: Function) {
    return api({
        url: '/api/util/routePlanning',
        params: {
            ...query,
        },
        successCallback,
        failureCallback,
    });
}
