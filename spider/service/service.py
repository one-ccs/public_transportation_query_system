#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests


SERVICE_HOST    = 'http://127.0.0.1:8080'
USERNAME = 'admin'
PASSWORD = '202cb962ac59075b964b07152d234b70'
LOGIN_API = '/api/user/login'
ADD_STATION_API = '/api/station'
ADD_ROUTE_API   = '/api/route'

NEARBY_API = 'https://web.chelaile.net.cn/api/bus/stop!encryptedNearlines.action'


def api(api: str):
    return SERVICE_HOST + api


def before_request(func):
    def wrapper(*args, **kw):
        if not Service.token:
            Service.login()
        if 'Authorization' not in Service.headers:
            Service.headers.update({
                'Authorization': f'Bearer {Service.token}'
            })
        return func(*args, **kw)
    return wrapper


class Service(object):
    headers = {
        'accept': '*/*',
        'accept-language': 'zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6',
        'sec-fetch-dest': 'empty',
        'sec-fetch-mode': 'cors',
        'sec-fetch-site': 'same-origin',
        'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1 Edg/124.0.0.0',
    }
    token = ''

    @staticmethod
    def login():
        res = requests.post(api(LOGIN_API), {
            'username': USERNAME,
            'password': PASSWORD,
        })
        if res.status_code == 200:
            Service.token = res.json()['data']['token']
            print('登录成功')

    @staticmethod
    def request_nearby(longitude: float, latitude: float):
        """发送附件站点查询"""
        res = requests.get(NEARBY_API, params={
            'cityState': 2,
            'cryptoSign': '1afce70334f2a474c998cd35dc8b97b3',
            's': 'h5',
            'v': '3.3.19',
            'src': 'web_chongqinggongjiao_pc',
            'userId': 'browser_1707226534422',
            'h5Id': 'browser_1707226534422',
            'unionId': None,
            'sign': 1,
            'cityId': '003',
            'geo_lat': latitude,
            'geo_lng': longitude,
            'lat': latitude,
            'lng': longitude,
            'gpstype': 'wgs',
        }, headers=Service.headers)

        return res

    @staticmethod
    @before_request
    def add_station(data: object):
        res = requests.put(api(ADD_STATION_API), json=data, headers=Service.headers)
        print(res.json())

    @staticmethod
    @before_request
    def add_route(data: object):
        res = requests.put(api(ADD_ROUTE_API), json=data, headers=Service.headers)
        print(res.json())
