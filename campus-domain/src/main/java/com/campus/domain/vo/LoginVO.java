package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功返回 VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    /**
     * JWT Token
     */
    private String token;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户角色
     */
    private String role;
}