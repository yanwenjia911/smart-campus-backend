package com.campus.infra.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置类
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 逻辑删除配置（已通过application.yml配置，此处仅作示例）
     */
    // @Bean
    // public GlobalConfig globalConfig() {
    //     GlobalConfig globalConfig = new GlobalConfig();
    //     GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
    //     dbConfig.setLogicDeleteField("deleted");
    //     dbConfig.setLogicDeleteValue("1");
    //     dbConfig.setLogicNotDeleteValue("0");
    //     globalConfig.setDbConfig(dbConfig);
    //     return globalConfig;
    // }
}