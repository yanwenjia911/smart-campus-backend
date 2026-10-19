package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.Grade;
import org.apache.ibatis.annotations.Mapper;

/**
 * 成绩 Mapper 接口
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}