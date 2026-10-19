package com.campus.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.app.service.CourseScheduleService;
import com.campus.common.exception.BusinessException;
import com.campus.domain.entity.CourseSchedule;
import com.campus.domain.mapper.CourseScheduleMapper;
import com.campus.domain.vo.CourseScheduleVO;
import com.campus.domain.vo.WeeklyScheduleVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程表服务实现
 */
@Service
public class CourseScheduleServiceImpl
        extends ServiceImpl<CourseScheduleMapper, CourseSchedule>
        implements CourseScheduleService {

    @Override
    public List<CourseScheduleVO> getScheduleByUserIdAndSemester(Long userId, String semester) {
        LambdaQueryWrapper<CourseSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseSchedule::getUserId, userId)
               .eq(CourseSchedule::getSemester, semester)
               .orderByAsc(CourseSchedule::getWeekDay)
               .orderByAsc(CourseSchedule::getStartClass);

        List<CourseSchedule> list = this.list(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public WeeklyScheduleVO getWeeklySchedule(Long userId, String semester) {
        List<CourseScheduleVO> allCourses = getScheduleByUserIdAndSemester(userId, semester);

        if (allCourses.isEmpty()) {
            // 返回空周课表，前端友好展示
            WeeklyScheduleVO emptyVO = new WeeklyScheduleVO();
            Map<Integer, List<CourseScheduleVO>> emptyMap = new LinkedHashMap<>();
            for (int i = 1; i <= 7; i++) {
                emptyMap.put(i, Collections.emptyList());
            }
            emptyVO.setWeeklyCourses(emptyMap);
            return emptyVO;
        }

        // 按星期几分组（1-7）
        Map<Integer, List<CourseScheduleVO>> dailyMap = allCourses.stream()
                .collect(Collectors.groupingBy(
                        CourseScheduleVO::getWeekDay,
                        LinkedHashMap::new,   // 保持顺序
                        Collectors.toList()
                ));

        // 确保 1-7 的 key 都存在（即使某天没课）
        for (int day = 1; day <= 7; day++) {
            dailyMap.putIfAbsent(day, Collections.emptyList());
        }
        // 排序 key
        Map<Integer, List<CourseScheduleVO>> sortedMap = new LinkedHashMap<>();
        new TreeMap<>(dailyMap).forEach(sortedMap::put);

        WeeklyScheduleVO vo = new WeeklyScheduleVO();
        vo.setWeeklyCourses(sortedMap);
        return vo;
    }

    /**
     * Entity -> VO
     */
    private CourseScheduleVO convertToVO(CourseSchedule entity) {
        return new CourseScheduleVO(
                entity.getId(),
                entity.getCourseName(),
                entity.getTeacherName(),
                entity.getLocation(),
                entity.getWeekDay(),
                entity.getStartClass(),
                entity.getPeriod(),
                entity.getWeekRange(),
                entity.getType()
        );
    }
}

