//CourseScheduleController

package com.campus.controller.api;

import com.campus.app.service.CourseScheduleService;
import com.campus.common.result.R;
import com.campus.domain.vo.CourseScheduleVO;
import com.campus.domain.vo.WeeklyScheduleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程表控制器
 */
@RestController
@RequestMapping("/api/course")
@Tag(name = "课程表", description = "查询用户课表信息")
public class CourseScheduleController {

    @Resource
    private CourseScheduleService courseScheduleService;

    @GetMapping("/semester/{semester}")
    @Operation(summary = "根据学期查询课表")
    public R<List<CourseScheduleVO>> getScheduleBySemester(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @PathVariable String semester) {
        List<CourseScheduleVO> schedule = courseScheduleService.getScheduleByUserIdAndSemester(userId, semester);
        return R.ok(schedule);
    }

    @GetMapping("/weekly/{semester}")
    @Operation(summary = "获取本周课表（按天分组）")
    public R<WeeklyScheduleVO> getWeeklySchedule(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @PathVariable String semester) {
        WeeklyScheduleVO weeklySchedule = courseScheduleService.getWeeklySchedule(userId, semester);
        return R.ok(weeklySchedule);
    }
}