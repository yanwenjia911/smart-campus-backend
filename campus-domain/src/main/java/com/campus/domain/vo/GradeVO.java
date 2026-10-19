package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 成绩视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeVO {

    private Long id;
    private String courseName;
    private String courseCode;
    private String teacherName;
    private BigDecimal credit;
    private BigDecimal regularScore;
    private BigDecimal finalScore;
    private BigDecimal totalScore;
    private BigDecimal gpa;
    private String gradeLevel;
    private String examType;
    private String semester;
}