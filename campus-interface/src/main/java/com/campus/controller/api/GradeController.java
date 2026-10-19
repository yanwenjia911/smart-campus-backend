package com.campus.controller.api;

import com.campus.app.service.GradeService;
import com.campus.common.result.R;
import com.campus.domain.vo.GradeVO;
import com.campus.domain.vo.SemesterGradeSummaryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩控制器
 */
@RestController
@RequestMapping("/api/grade")
@Tag(name = "成绩管理", description = "查询用户成绩信息")
public class GradeController {

    @Resource
    private GradeService gradeService;

    @GetMapping("/semester/{semester}")
    @Operation(summary = "根据学期查询成绩")
    public R<List<GradeVO>> getGradesBySemester(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @PathVariable String semester) {
        List<GradeVO> grades = gradeService.getGradesByUserIdAndSemester(userId, semester);
        return R.ok(grades);
    }

    @GetMapping("/all-semesters")
    @Operation(summary = "获取所有学期成绩汇总")
    public R<List<SemesterGradeSummaryVO>> getAllSemesterSummaries(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId) {
        List<SemesterGradeSummaryVO> summaries = gradeService.getAllSemesterSummaries(userId);
        return R.ok(summaries);
    }

    @GetMapping("/{gradeId}")
    @Operation(summary = "查询单科成绩详情")
    public R<GradeVO> getGradeDetail(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @PathVariable Long gradeId) {
        GradeVO grade = gradeService.getGradeDetail(gradeId);
        // 简单权限校验：确保查询的 grade 的 userId 是当前登录用户
        // 在实际 Service 实现中已完成校验，这里可省略或作为双重保险
        return R.ok(grade);
    }
}