package com.example.publictransportationquerysystem;

import java.sql.Types;
import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create(
            "jdbc:mysql://localhost:3306/leave_approval_management_system?useSSL=false&characterEncoding=UTF-8",
            "root",
            "LmzwTvA1p5Bds3DODi$b2bfe2b68ef2esdf9b86dd354e00d3c3c7f533ce18fe8a6f33f7c3af52396b1bb"
        )
            .globalConfig(builder -> {
                builder.author("zy") // 设置作者
                    .enableSwagger() // 开启 swagger 模式
                    // .fileOverride() // 覆盖已生成文件
                    .outputDir("D://test"); // 指定输出目录
            })
            .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                if (typeCode == Types.SMALLINT) {
                    // 自定义类型转换
                    return DbColumnType.INTEGER;
                }
                return typeRegistry.getColumnType(metaInfo);

            }))
            .packageConfig(builder -> {
                builder.parent("com.example.leave_approval_management_system") // 设置父包名
                    .moduleName(null) // 设置父包模块名
                    .pathInfo(Collections.singletonMap(OutputFile.xml, "D://test")); // 设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
                builder.addInclude("user") // 设置需要生成的表名
                    .addInclude("student")
                    .addInclude("teacher")
                    .addInclude("leave")
                    .addInclude("admin")
                    .addTablePrefix("t_", "c_"); // 设置过滤表前缀
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }
}
