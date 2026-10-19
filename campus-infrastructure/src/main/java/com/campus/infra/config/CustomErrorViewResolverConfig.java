package com.campus.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

@Configuration
public class CustomErrorViewResolverConfig {

    // 自定义 Bean 名称，避免与默认冲突
    @Bean(name = "customConventionErrorViewResolver")
    @ConditionalOnMissingBean(name = "customConventionErrorViewResolver")
    public ViewResolver customConventionErrorViewResolver() {
        return new BeanNameViewResolver();
    }
}