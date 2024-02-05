package com.example.publictransportationquerysystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.publictransportationquerysystem.entity.po.Account;

public interface IAccountService extends IService<Account>, UserDetailsService {

}
