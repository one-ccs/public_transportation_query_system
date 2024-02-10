package com.example.public_transportation_query_system.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.public_transportation_query_system.entity.po.User;

public interface IUserService extends IService<User>, UserDetailsService {

}
