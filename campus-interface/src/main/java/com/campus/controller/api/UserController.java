package com.campus.controller.api;

import com.campus.app.service.UserService;
import com.campus.common.result.R;
import com.campus.domain.dto.LoginDTO;
import com.campus.domain.vo.LoginVO;
import com.campus.domain.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户中心控制器
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户中心", description = "用户登录、信息查询等接口")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录/绑定")
    public R<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.login(loginDTO);
        return R.ok(loginVO);
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    public R<UserInfoVO> getInfo(@RequestAttribute("userId") Long userId) { // 通过拦截器解析 JWT 后放入 request attribute
        UserInfoVO userInfoVO = userService.getUserInfo(userId);
        return R.ok(userInfoVO);
    }
}