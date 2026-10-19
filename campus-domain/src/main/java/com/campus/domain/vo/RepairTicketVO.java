package com.campus.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 报修工单VO
 */
@Data
public class RepairTicketVO {

    /**
     * 工单ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 报修人姓名
     */
    private String reporterName;

    /**
     * 联系方式
     */
    private String contactPhone;

    /**
     * 报修标题
     */
    private String title;

    /**
     * 报修描述
     */
    private String description;

    /**
     * 报修地点
     */
    private String repairLocation;

    /**
     * 附件URL列表 ⭐必须是List
     */
    private List<String> attachmentUrls;

    /**
     * 报修分类
     */
    private String category;

    /**
     * 状态
     */
    private String status;

    /**
     * 维修人员ID
     */
    private Long staffId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}