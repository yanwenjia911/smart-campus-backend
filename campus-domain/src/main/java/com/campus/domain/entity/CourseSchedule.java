package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//import java.time.LocalDate;

/**
 * 课程表实体
 */
@Data
@TableName("course_schedule")
public class CourseSchedule {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的用户ID（学生）
     */
    private Long userId;

    /**
     * 学期（如：2023-2024-1）
     */
    private String semester;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 上课地点
     */
    private String location;

    /**
     * 星期几上课（1-7）
     */
    private Integer weekDay;

    /**
     * 第几节课开始（1-12）
     */
    private Integer startClass;

    /**
     * 课程持续节数（如 2 表示两节连上）
     */
    private Integer period;

    /**
     * 周次（如 1-16周）
     */
    private String weekRange;

    /**
     * 课程类型（如 必修、选修）
     */
    private String type;

    // 可根据需要添加其他字段，如课程代码、学分等
}