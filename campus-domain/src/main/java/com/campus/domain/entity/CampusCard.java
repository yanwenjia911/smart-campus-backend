package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 校园卡实体
 */
@Data
@TableName("campus_card")
public class CampusCard {

    @TableId(type = IdType.ASSIGN_ID) // 使用雪花算法生成ID
    private Long id;

    /**
     * 对应的用户ID
     */
    private Long userId;

    /**
     * 校园卡号（唯一）
     */
    private String cardNumber;

    /**
     * 卡内余额
     */
    private BigDecimal balance;

    /**
     * 卡状态（ACTIVE, LOST, DISABLED, EXPIRED）
     */
    private String status;

    /**
     * 卡类型（STUDENT, TEACHER, STAFF）
     */
    private String cardType;

    /**
     * 激活时间
     */
    private LocalDateTime activateTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 挂失时间
     */
    private LocalDateTime reportedLostTime;

    /**
     * 挂失备注
     */
    private String lostRemark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 可根据需要添加照片、部门等信息
}