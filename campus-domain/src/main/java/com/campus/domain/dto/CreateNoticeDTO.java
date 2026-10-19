package com.campus.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 创建通知 DTO
 */
@Data
public class CreateNoticeDTO {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "发布者不能为空")
    private String publisher;

    @NotNull(message = "类型不能为空")
    private String type;

    private String priority = "中"; // 默认值

    private LocalDateTime effectiveTime; // 默认为当前时间

    private LocalDateTime expireTime; // 可选

    private Integer isTop = 0; // 默认不置顶

    private List<String> attachmentUrls; // 附件URL列表
}