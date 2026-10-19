package com.campus.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录 DTO
 */
@Data
public class LoginDTO {

    /**
     * 小程序 wx.login() 获取的 code
     */
    @NotBlank(message = "登录凭证不能为空")
    private String code;

    /**
     * 用户绑定时提供的学号
     */
    private String studentId;

    /**
     * 用户绑定时提供的姓名（用于首次绑定验证）
     */
    private String name;
}