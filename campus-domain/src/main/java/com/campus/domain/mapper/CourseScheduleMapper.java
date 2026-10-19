package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.CourseSchedule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程表 Mapper 接口
 */
@Mapper
public interface CourseScheduleMapper extends BaseMapper<CourseSchedule> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}