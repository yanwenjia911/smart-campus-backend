package com.campus.app.service;

import com.campus.domain.vo.CourseScheduleVO;
import com.campus.domain.vo.WeeklyScheduleVO;
import java.util.List;

/**
 * 课程表服务接口
 */
public interface CourseScheduleService {

    /**
     * 根据用户ID和学期获取课程列表
     * @param userId 用户ID
     * @param semester 学期
     * @return 课程列表
     */
    List<CourseScheduleVO> getScheduleByUserIdAndSemester(Long userId, String semester);

    /**
     * 获取本周的课程表（按天分组）
     * @param userId 用户ID
     * @param semester 学期
     * @return 一周课程表
     */
    WeeklyScheduleVO getWeeklySchedule(Long userId, String semester);
}