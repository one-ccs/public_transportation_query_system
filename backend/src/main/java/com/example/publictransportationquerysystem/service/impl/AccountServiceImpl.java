package com.example.publictransportationquerysystem.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.po.Account;
import com.example.publictransportationquerysystem.mapper.AccountMapper;
import com.example.publictransportationquerysystem.service.IAccountService;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.getUserByNameOrEmail(username);
        if (account == null) throw new UsernameNotFoundException("不存在该用户");

        return User
            .withUsername(username)
            .password(account.getPassword())
            .roles(account.getRole())
            .build();
    }

    public Account getUserByNameOrEmail(String text) {
        return this.query()
            .eq("username", text).or()
            .eq("email", text)
            .one();
    }
}
