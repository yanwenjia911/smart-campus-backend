package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知 Mapper 接口
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}