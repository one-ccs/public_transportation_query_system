#!/usr/bin/env python
# -*- coding: utf-8 -*-
from json import dump


def save_data(data_path, obj):
    with open(data_path, '+a', encoding='utf-8') as f:
        dump(obj, f, indent=4)
