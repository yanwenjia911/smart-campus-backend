package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程表视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseScheduleVO {

    private Long id;
    private String courseName;
    private String teacherName;
    private String location;
    private Integer weekDay;      // 1-7 代表周一到周日
    private Integer startClass;   // 1-12 代表第几节课开始
    private Integer period;       // 持续节数
    private String weekRange;     // 周次范围
    private String type;
}