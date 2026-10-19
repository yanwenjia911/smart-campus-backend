package com.campus.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 创建报修工单 DTO
 */
@Data
public class CreateRepairTicketDTO {

    @NotBlank(message = "报修地点不能为空")
    private String repairLocation;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotNull(message = "报修类别不能为空")
    private String category;

    private List<String> attachmentUrls; // 图片URL列表
}