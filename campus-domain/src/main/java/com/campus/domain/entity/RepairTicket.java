package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报修工单实体
 */
@Data
@TableName("repair_ticket")
public class RepairTicket {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 提交报修的用户ID
     */
    private Long userId;

    /**
     * 报修人姓名
     */
    private String reporterName;

    /**
     * 报修人联系方式（电话）
     */
    private String contactPhone;

    /**
     * 报修地点（宿舍楼号、教室号等）
     */
    private String repairLocation;

    /**
     * 报修标题
     */
    private String title;

    /**
     * 报修详细描述
     */
    private String description;

    /**
     * 附件图片URL（多个URL用逗号分隔）
     */
    private String attachmentUrls;

    /**
     * 报修类别（如：水电、家具、网络、其他）
     */
    private String category;

    /**
     * 工单状态（SUBMITTED, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED）
     */
    private String status;

    /**
     * 指派给的维修人员ID
     */
    private Long assignedStaffId;

    /**
     * 指派的维修人员姓名
     */
    private String assignedStaffName;

    /**
     * 完成时间
     */
    private LocalDateTime completedTime;

    /**
     * 提交时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 可根据需要添加评分、反馈意见等字段
}