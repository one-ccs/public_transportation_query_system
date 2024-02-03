package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.User;
import com.example.publictransportationquerysystem.mapper.UserMapper;
import com.example.publictransportationquerysystem.service.IUserService;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
