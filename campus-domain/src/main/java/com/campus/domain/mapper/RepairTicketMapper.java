package com.campus.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.domain.entity.RepairTicket;
import org.apache.ibatis.annotations.Mapper;

/**
 * 报修工单 Mapper 接口
 */
@Mapper
public interface RepairTicketMapper extends BaseMapper<RepairTicket> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}