package com.example.publictransportationquerysystem.util;

import java.sql.Types;
import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyBatisPlusGenerator {

    private static final String url = "jdbc:mysql://localhost:3306/public_transportation_query_system?useSSL=false&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "LmzwTvA1p5Bds3DODi$b2bfe2b68ef2esdf9b86dd354e00d3c3c7f533ce18fe8a6f33f7c3af52396b1bb";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, username, password)
            .globalConfig(builder -> builder
                .enableSpringdoc()
                .author("author")
                .outputDir("D://test")
            )
            .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                if (typeCode == Types.SMALLINT) {
                    // 自定义类型转换
                    return DbColumnType.INTEGER;
                }
                return typeRegistry.getColumnType(metaInfo);
            }))
            .packageConfig(builder -> builder
                // 设置父包名
                .parent("com.example.publictransportationquerysystem")
                // 设置父包模块名
                .moduleName(null)
                // 设置 mapperXml 生成路径
                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://test"))
            )
            .strategyConfig(builder -> builder
                // 设置需要生成的表名
                .addInclude("account")
                .addInclude("lost")
                .addInclude("notice")
                .addInclude("ad")
                .addInclude("station")
                .addInclude("route_station")
                .addInclude("route")
                // 设置过滤表前缀
                .addTablePrefix("t_", "c_")
            )
            // 使用 Freemarker 引擎模板 (需安装 spring-boot-starter-freemarker 依赖)
            // 默认 Velocity 引擎模板
            .templateEngine(new FreemarkerTemplateEngine())
            .execute();
    }
}
