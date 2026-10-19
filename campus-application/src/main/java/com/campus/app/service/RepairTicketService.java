package com.campus.app.service;

import com.campus.domain.dto.CreateRepairTicketDTO;
import com.campus.domain.vo.RepairTicketVO;
import java.util.List;

/**
 * 报修工单服务接口
 */
public interface RepairTicketService {

    /**
     * 提交新的报修工单
     * @param userId 用户ID
     * @param createDTO 报修信息
     * @return 新建的工单ID
     */
    Long submitTicket(Long userId, CreateRepairTicketDTO createDTO);

    /**
     * 根据ID获取工单详情
     * @param ticketId 工单ID
     * @return 工单详情
     */
    RepairTicketVO getTicketById(Long ticketId);

    /**
     * 获取当前用户的报修工单列表
     * @param userId 用户ID
     * @param status 状态筛选（可选）
     * @return 工单列表
     */
    List<RepairTicketVO> getTicketsByUserId(Long userId, String status);

    /**
     * 获取所有待处理的工单列表（管理员/维修员查看）
     * @return 待处理工单列表
     */
    List<RepairTicketVO> getPendingTickets();

    /**
     * 更新工单状态（例如分配、完成等）
     * @param ticketId 工单ID
     * @param newStatus 新状态
     * @param staffId 维修人员ID（分配时使用）
     */
    void updateTicketStatus(Long ticketId, String newStatus, Long staffId);
}