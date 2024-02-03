package com.example.publictransportationquerysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

@Configuration
public class MybatisPlusConfig {
    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();

        pageInterceptor.setOverflow(false);
        pageInterceptor.setMaxLimit((long) 500);
        pageInterceptor.setDbType(DbType.MYSQL);

        interceptor.addInnerInterceptor(pageInterceptor);

        return interceptor;
    }
}
