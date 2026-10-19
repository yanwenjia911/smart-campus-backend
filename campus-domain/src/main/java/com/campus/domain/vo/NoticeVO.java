package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知公告视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {

    private Long id;
    private String title;
    private String content;
    private String publisher;
    private String type;
    private String priority;
    private LocalDateTime publishTime;
    private LocalDateTime effectiveTime;
    private LocalDateTime expireTime;
    private Integer isTop;
    private Long viewCount;
    private List<String> attachmentUrls;
}