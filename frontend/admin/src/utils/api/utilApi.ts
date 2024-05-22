import { api } from ".";

export function apiStatistics(successCallback?: Function, failureCallback?: Function) {
    return api({
        url: '/api/util/statistics',
        successCallback,
        failureCallback,
    });
}
