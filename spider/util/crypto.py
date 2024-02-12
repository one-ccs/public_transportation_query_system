#!/usr/bin/env python
# -*- coding: utf-8 -*-
from Crypto.Cipher import AES
from json import loads, dumps
import base64


def aes_encrypt(obj : object, secret_key : str) -> str:
    """AES 算法 ECB 模式加密"""
    text = dumps(obj)
    cipher = AES.new(secret_key.encode('utf8'), AES.MODE_ECB)
    pad = lambda s: s + (AES.block_size - len(s) % AES.block_size) * chr(AES.block_size - len(s) % AES.block_size)
    text = pad(text)
    cipher_text = cipher.encrypt(text.encode('utf8'))
    return base64.b64encode(cipher_text)

def aes_decrypt(text : str, secret_key : str) -> object:
    """AES 算法 ECB 模式解密"""
    cipher = AES.new(secret_key.encode('utf8'), AES.MODE_ECB)
    text = base64.b64decode(text)
    plaintext = cipher.decrypt(text)
    unpad = lambda s: s[0:-s[-1]]
    cipher_text = unpad(plaintext).decode('utf8')
    return loads(cipher_text)
