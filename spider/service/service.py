#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests


SERVICE_HOST    = 'http://127.0.0.1:8080'
USERNAME = 'admin'
PASSWORD = '202cb962ac59075b964b07152d234b70'
LOGIN_API = '/api/user/login'
ADD_STATION_API = '/api/station'
ADD_ROUTE_API   = '/api/route'


def api(api: str):
    return SERVICE_HOST + api


def before_request(func):
    def wrapper(*args, **kw):
        if not Service.token:
            Service.login()
        return func(*args, **kw)
    return wrapper


class Service(object):
    token = ''

    @staticmethod
    def login():
        res = requests.post(api(LOGIN_API), {
            'username': USERNAME,
            'password': PASSWORD,
        })
        if res.status_code == 200:
            print('登录成功')
            Service.token = res.json()['data']['token']

    @staticmethod
    @before_request
    def add_station(data: object):
        res = requests.put(api(ADD_STATION_API), json=data, headers={
            'Authorization': f'Bearer {Service.token}'
        })
        print(res.json())

    @staticmethod
    @before_request
    def add_route(data: object):
        res = requests.put(api(ADD_ROUTE_API), json=data, headers={
            'Authorization': f'Bearer {Service.token}'
        })
        print(res.json())
