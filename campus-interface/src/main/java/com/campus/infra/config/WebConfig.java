package com.campus.infra.config;

import com.campus.infra.security.JwtAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，指定拦截路径
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/api/**") // 拦截所有 /api/ 下的请求
                // 对齐前端实际请求的路径
                .excludePathPatterns("/api/auth/login", "/api/auth/bind") 
                .excludePathPatterns("/api/notice", "/api/notice/**") // 通知公告无需登录
                .excludePathPatterns("/api/card/ping")// 测试接口
                .excludePathPatterns("/api/public/**"); // 其他公开接口
    }
}