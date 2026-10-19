package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息返回 VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {

    private String nickname;
    private String studentId;
    private String role;
    // 可扩展：头像URL、学院、专业等
}