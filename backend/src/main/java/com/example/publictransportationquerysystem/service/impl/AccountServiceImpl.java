package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.po.Account;
import com.example.publictransportationquerysystem.mapper.AccountMapper;
import com.example.publictransportationquerysystem.service.IAccountService;


@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}
