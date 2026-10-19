package com.campus.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.app.service.GradeService;
import com.campus.common.exception.BusinessException;
import com.campus.domain.entity.Grade;
import com.campus.domain.mapper.GradeMapper;
import com.campus.domain.vo.GradeVO;
import com.campus.domain.vo.SemesterGradeSummaryVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public List<GradeVO> getGradesByUserIdAndSemester(Long userId, String semester) {
        LambdaQueryWrapper<Grade> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Grade::getUserId, userId)
                .eq(Grade::getSemester, semester);

        List<Grade> list = this.list(queryWrapper);

        if (CollUtil.isEmpty(list)) {
            throw new BusinessException("未找到该学期的成绩信息");
        }

        return list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SemesterGradeSummaryVO> getAllSemesterSummaries(Long userId) {
        LambdaQueryWrapper<Grade> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Grade::getUserId, userId);

        List<Grade> allGrades = this.list(queryWrapper);

        if (CollUtil.isEmpty(allGrades)) {
            return Collections.emptyList();
        }

        // 按学期分组
        Map<String, List<Grade>> groupedBySemester = allGrades.stream()
                .collect(Collectors.groupingBy(Grade::getSemester));

        List<SemesterGradeSummaryVO> summaries = new ArrayList<>();
        for (Map.Entry<String, List<Grade>> entry : groupedBySemester.entrySet()) {
            String semester = entry.getKey();
            List<Grade> gradesInSemester = entry.getValue();

            // 计算该学期的总学分和平均绩点
            BigDecimal totalCredits = BigDecimal.ZERO;
            BigDecimal weightedGpaSum = BigDecimal.ZERO;
            for (Grade grade : gradesInSemester) {
                if (grade.getCredit() != null && grade.getGpa() != null) {
                    totalCredits = totalCredits.add(grade.getCredit());
                    weightedGpaSum = weightedGpaSum.add(grade.getCredit().multiply(grade.getGpa()));
                }
            }
            BigDecimal averageGpa = BigDecimal.ZERO;
            if (totalCredits.compareTo(BigDecimal.ZERO) > 0) {
                averageGpa = weightedGpaSum.divide(totalCredits, 2, RoundingMode.HALF_UP);
            }

            // 转换为 VO
            SemesterGradeSummaryVO summary = new SemesterGradeSummaryVO();
            summary.setSemester(semester);
            summary.setTotalCredits(totalCredits);
            summary.setAverageGpa(averageGpa);
            summary.setGrades(gradesInSemester.stream().map(this::convertToVO).collect(Collectors.toList()));

            summaries.add(summary);
        }

        return summaries;
    }

    @Override
    public GradeVO getGradeDetail(Long gradeId) {
        Grade grade = this.getById(gradeId);
        if (grade == null || grade.getUserId() == null) { // 确保能查到
            throw new BusinessException("成绩记录不存在或无权访问");
        }
        return convertToVO(grade);
    }

    /**
     * Entity -> VO
     */
    private GradeVO convertToVO(Grade entity) {
        GradeVO vo = new GradeVO();
        vo.setId(entity.getId());
        vo.setCourseName(entity.getCourseName());
        vo.setCourseCode(entity.getCourseCode());
        vo.setTeacherName(entity.getTeacherName());
        vo.setCredit(entity.getCredit());
        vo.setRegularScore(entity.getRegularScore());
        vo.setFinalScore(entity.getFinalScore());
        vo.setTotalScore(entity.getTotalScore());
        vo.setGpa(entity.getGpa());
        vo.setGradeLevel(entity.getGradeLevel());
        vo.setExamType(entity.getExamType());
        vo.setSemester(entity.getSemester());
        return vo;
    }
}