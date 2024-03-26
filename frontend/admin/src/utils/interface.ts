/* --------- po --------- */

export interface User {
    id?: number;
    username?: string;
    password?: string;
    email?: string;
    status?: number;
    registerDatetime?: string;
};

export interface Role {
    id?: number;
    name?: string;
    nameZh?: string;
};

export interface Route {
    id?: number;
    no?: string;
    status?: number;
    firstTime?: string;
    lastTime?: string;
};

export interface Station {
    id?: number;
    latitude?: number;
    longitude?: number;
    sitename?: string;
    status?: number;
};

export interface Notice {
    id?: number;
    userId?: number;
    title?: string;
    content?: string;
    status?: number;
    releaseDatetime?: string;
};

export interface Lost {
    id?: number;
    status?: number;
    describe?: string;
    imgUrl?: string;
    address?: string;
    pickDatetime?: string;
    claimDatetime?: string;
};

export interface Ad {
    id?: number;
    type?: number;
    title?: string;
    describe?: string;
    imgUrl?: string;
    jumpUrl?: string;
    startDatetime?: string;
    endDatetime?: string;
};

/* --------- dto --------- */

/* --------- vo --------- */

export interface ResponseData {
    code: number;
    message: string;
    data: any;
};

export interface PageQuery {
    pageIndex: number;
    pageSize: number;
    query?: string;
};

export interface TimeRangePageQuery extends PageQuery {
    startDatetime: string;
    endDatetime: string;
};

export interface UserLogin {
    username: string;
    email?: string;
    password: string;
    captcha?: string;
    remember: boolean;
};

export interface UserVo extends User {
    roles?: Role[];
    passwordCheck: string;
    passwordModified?: boolean;
}
