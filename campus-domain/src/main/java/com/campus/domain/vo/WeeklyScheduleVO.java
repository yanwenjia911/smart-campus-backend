package com.campus.domain.vo;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 一周课程表视图对象（按天分组）
 */
@Data
public class WeeklyScheduleVO {

    /**
     * key: weekDay (1-7), value: 该天的课程列表
     */
    private Map<Integer, List<CourseScheduleVO>> weeklyCourses;
}