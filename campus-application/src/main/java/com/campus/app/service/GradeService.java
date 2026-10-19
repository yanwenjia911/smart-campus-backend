package com.campus.app.service;

import com.campus.domain.vo.GradeVO;
import com.campus.domain.vo.SemesterGradeSummaryVO;
import java.util.List;

/**
 * 成绩服务接口
 */
public interface GradeService {

    /**
     * 根据用户ID和学期获取成绩列表
     * @param userId 用户ID
     * @param semester 学期
     * @return 成绩列表
     */
    List<GradeVO> getGradesByUserIdAndSemester(Long userId, String semester);

    /**
     * 获取所有学期的成绩汇总（包含每学期的平均绩点、总学分等）
     * @param userId 用户ID
     * @return 所有学期成绩汇总
     */
    List<SemesterGradeSummaryVO> getAllSemesterSummaries(Long userId);

    /**
     * 获取单科成绩详情
     * @param gradeId 成绩ID
     * @return 成绩详情
     */
    GradeVO getGradeDetail(Long gradeId);
}