#!/usr/bin/env python
# -*- coding: utf-8 -*-
from util.crypto import aes_decrypt
from json import dump, load, loads

from service import Service

SECRET_KEY = '422556651C7F7B2B5C266EED06068230'
ENCRYPTED_FILE = 'spider/data/encrypted.txt'
DECRYPTED_FILE = 'spider/data/decrypted.json'
PARSED_FILE = 'spider/data/parsed.json'

def request(longitude: float, latitude: float):
    res = Service.request_nearby(longitude, latitude)
    print(res.text)

def decrypt():
    """解码"""
    with open(ENCRYPTED_FILE, 'r', encoding='utf-8') as ef:
        encrypted = ef.read().replace('**YGKJ', '').replace('YGKJ##', '')
        encrypted = loads(encrypted)
        encrypted = encrypted['jsonr']['data']['encryptResult']
        decrypted = aes_decrypt(encrypted, SECRET_KEY)

        with open(DECRYPTED_FILE, 'w', encoding='utf-8') as df:
            dump(decrypted, df, indent=4, ensure_ascii=False)

def parse_near_lines():
    """解析解码后的 json 提取有效数据"""
    with open(DECRYPTED_FILE, 'r', encoding='utf-8') as df:
        data = load(df)

        _data = [{
            'siteName': station['sn'],
            'sId': station['sId'],
            'latitude': station['lat'],
            'longitude': station['lng'],
            'distance': 124,
            'subwayLines': [],
            'lines': [{
                'lineId': line['line']['lineId'],
                'lineNo': line['line']['lineNo'],
                'name': line['line']['name'],
                'price': line['line']['price'],
                'startSiteName': line['line']['startSn'],
                'endSiteName': line['line']['endSn'],
                'firstTime': line['line']['firstTime'],
                'lastTime': line['line']['lastTime'],
                'stationsNum': line['line']['stationsNum'],
            } for line in station['lines']],
        } for station in data['nearLines']]

        with open(PARSED_FILE, 'w', encoding='utf-8') as f:
            dump(_data, f, indent=4, ensure_ascii=False)

def add_station():
    """添加站点"""
    with open(PARSED_FILE, 'r', encoding='utf-8') as df:
        data = load(df)

        for site in data:
            _data = {
                'sitename': site['siteName'],
                'longitude': site['longitude'],
                'latitude': site['latitude'],
            }
            Service.add_station(_data)

def add_route():
    """添加线路"""
    with open(PARSED_FILE, 'r', encoding='utf-8') as df:
        data = load(df)

        _list_dict = {}
        for site in data:
            for route in site['lines']:
                _data = {
                    'no': route['lineNo'],
                    'firstTime': route['firstTime'],
                    'lastTime': route['lastTime'],
                }
                if _data['no'] not in _list_dict:
                    _list_dict[_data['no']] = _data
        _list_dict = [value for value in _list_dict.values()]

        for route in _list_dict:
            Service.add_route(route)


# request(106.521978, 29.380991)
# decrypt()
parse_near_lines()
add_station()
add_route()
