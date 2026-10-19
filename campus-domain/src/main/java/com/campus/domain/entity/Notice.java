package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知公告实体
 */
@Data
@TableName("notice")
public class Notice {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 发布者（如：教务处、后勤部）
     */
    private String publisher;

    /**
     * 通知类型（如：系统公告、教务通知、活动通知、失物招领）
     */
    private String type;

    /**
     * 重要级别（如：高、中、低）
     */
    private String priority;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 是否置顶（1-是，0-否）
     */
    private Integer isTop;

    /**
     * 点击量
     */
    private Long viewCount;

    /**
     * 附件URL（多个URL用逗号分隔）
     */
    private String attachmentUrls;

    /**
     * 创建者ID
     */
    private Long creatorId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 可根据需要添加是否发布状态等字段
}