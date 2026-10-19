package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩实体
 */
@Data
@TableName("grade")
public class Grade {

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
     * 课程代码
     */
    private String courseCode;

    /**
     * 任课教师
     */
    private String teacherName;

    /**
     * 学分
     */
    private BigDecimal credit;

    /**
     * 平时成绩
     */
    private BigDecimal regularScore;

    /**
     * 期末成绩
     */
    private BigDecimal finalScore;

    /**
     * 总成绩
     */
    private BigDecimal totalScore;

    /**
     * 绩点
     */
    private BigDecimal gpa;

    /**
     * 成绩等级（如 A+, B-, F 等）
     */
    private String gradeLevel;

    /**
     * 考试性质（如 正考、补考、重修）
     */
    private String examType;

    /**
     * 成绩录入时间
     */
    private LocalDateTime recordTime;

    // 可根据需要添加其他字段，如是否通过、备注等
}