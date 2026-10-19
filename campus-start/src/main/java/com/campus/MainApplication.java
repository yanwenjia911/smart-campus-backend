/**
 * 智慧校园平台启动类 (Smart Campus Platform Application Entry Point).
 *
 * 该类是 Spring Boot 应用的主入口。它利用 @SpringBootApplication 注解，
 * 启用了自动配置、组件扫描和 Spring MVC 等核心功能。
 * 
 * 项目采用分层架构和多模块设计：
 * - campus-common: 通用工具与常量
 * - campus-domain: 领域模型 (Entity, DTO, VO)
 * - campus-infrastructure: 基础设施配置 (MyBatis, Redis, Security)
 * - campus-application: 业务逻辑层 (Service)
 * - campus-interface: 表现层 (Controller)
 * - campus-start: 启动模块 (本类所在)
 * 
 * Spring Boot 会自动扫描依赖模块中的 @Component, @Configuration, @Service, @Controller 等注解。
 */

package com.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.campus")

public class MainApplication { // 移除了 extends SpringBootServletInitializer

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("\n" +
                "============================================================\n" +
                "校园服务平台已启动！\n" +
                "访问地址：http://localhost:8080\n" +
                "Swagger文档：http://localhost:8080/swagger-ui.html\n" +
                "============================================================\n");
    }
}