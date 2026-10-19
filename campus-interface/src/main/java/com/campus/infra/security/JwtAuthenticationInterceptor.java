package com.campus.infra.security;

import cn.hutool.core.util.StrUtil;
import com.campus.common.constant.HttpStatus;
import com.campus.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 认证拦截器
 */
@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取 Token
        String token = request.getHeader("Authorization");

        // 🚨 监控点 1：打印前端传来的原始 Token
        System.out.println("\n========== [后端调试] 接收到请求拦截 ==========");
        System.out.println("请求URI: " + request.getRequestURI());
        System.out.println("收到的原始Token: [" + token + "]");

        if (StrUtil.isBlank(token)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "缺少认证令牌，请先登录");
        }

        // 移除 "Bearer " 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // ================= 开始：本地测试后门 =================
        if ("mock_test_token_12345".equals(token) || "mock_bind_token_999".equals(token)) {
            // 给这个测试 token 放行，并强行塞入一个测试用的 userId (比如 1004L)
            request.setAttribute("userId", 1004L);
            return true; 
        }
        // ================= 结束：本地测试后门 =================

        // 验证 Token (真实环境的校验逻辑)
        if (!JwtUtil.validateToken(token)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "认证令牌无效或已过期，请重新登录");
        }

        // 解析出 userId
        String userIdStr = JwtUtil.getUserIdFromToken(token);
        Long userId = Long.valueOf(userIdStr);

        // 将 userId 放入 request 属性中
        request.setAttribute("userId", userId);

        return true;
    }

    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //     // 从请求头获取 Token
    //     String token = request.getHeader("Authorization");

    //     if (StrUtil.isBlank(token)) {
    //         throw new BusinessException(HttpStatus.UNAUTHORIZED, "缺少认证令牌，请先登录");
    //     }

    //     // 移除 "Bearer " 前缀
    //     if (token.startsWith("Bearer ")) {
    //         token = token.substring(7);
    //     }

    //     // ✅ 修改1：直接用大写的类名 JwtUtil 调用静态方法
    //     if (!JwtUtil.validateToken(token)) {
    //         throw new BusinessException(HttpStatus.UNAUTHORIZED, "认证令牌无效或已过期，请重新登录");
    //     }

    //     // ✅ 修改2：直接用大写的类名 JwtUtil 调用静态方法
    //     String userIdStr = JwtUtil.getUserIdFromToken(token);
    //     Long userId = Long.valueOf(userIdStr);

    //     // 将 userId 放入 request 属性中，供 Controller 使用
    //     request.setAttribute("userId", userId);

    //     // 放行
    //     return true;
    // }
}