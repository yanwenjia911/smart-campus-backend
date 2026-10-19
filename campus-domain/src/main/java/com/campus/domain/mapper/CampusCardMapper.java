package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.CampusCard;
import org.apache.ibatis.annotations.Mapper;

/**
 * 校园卡 Mapper 接口
 */
@Mapper
public interface CampusCardMapper extends BaseMapper<CampusCard> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}