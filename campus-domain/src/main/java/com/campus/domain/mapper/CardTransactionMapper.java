package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.CardTransaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * 校园卡交易记录 Mapper 接口
 */
@Mapper
public interface CardTransactionMapper extends BaseMapper<CardTransaction> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}