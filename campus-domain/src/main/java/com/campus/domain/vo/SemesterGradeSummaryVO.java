package com.campus.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 学期成绩汇总视图对象
 */
@Data
public class SemesterGradeSummaryVO {

    /**
     * 学期
     */
    private String semester;

    /**
     * 总学分
     */
    private BigDecimal totalCredits;

    /**
     * 平均绩点
     */
    private BigDecimal averageGpa;

    /**
     * 该学期的所有成绩详情
     */
    private List<GradeVO> grades;
}