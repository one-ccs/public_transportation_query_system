package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}