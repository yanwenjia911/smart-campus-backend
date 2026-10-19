package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@TableName("sys_user") // 假设表名为 sys_user
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信 openid，唯一标识小程序用户
     */
    private String openid;

    /**
     * 学号/工号
     */
    private String studentId;

    /**
     * 用户名（如学生姓名）
     */
    private String nickname;

    /**
     * 角色：STUDENT, TEACHER, ADMIN
     */
    private String role;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 可根据需要添加其他字段，如头像、邮箱等
}