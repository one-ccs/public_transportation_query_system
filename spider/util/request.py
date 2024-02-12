#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests


class Request(object):
    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0'
    timeout = 3

    def __init__(self, headers={}):
        self.headers = headers

    def __make_headers(self):
        self.headers = {
            'User-Agent': self.user_agent,
        }

    def post(self, url, data=None, params=None, json=None):
        self.__make_headers()
        return requests.post(url, data, params, json, headers=self.headers, timeout=self.timeout)

    def get(self, url, params=None):
        self.__make_headers()
        return requests.get(url, params, headers=self.headers, timeout=self.timeout)
