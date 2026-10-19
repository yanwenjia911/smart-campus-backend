package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper 接口
 */
@Mapper // 标记为 MyBatis Mapper
public interface UserMapper extends BaseMapper<User> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
    // 如需自定义 SQL，可在此处添加 @Select, @Update 等注解方法
}