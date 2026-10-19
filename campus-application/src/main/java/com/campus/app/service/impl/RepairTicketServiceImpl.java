package com.campus.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.app.service.RepairTicketService;
import com.campus.app.service.UserService; // 假设需要查询用户信息
import com.campus.common.exception.BusinessException;
import com.campus.domain.dto.CreateRepairTicketDTO;
import com.campus.domain.entity.RepairTicket;
import com.campus.domain.entity.User;
import com.campus.domain.mapper.RepairTicketMapper;
import com.campus.domain.vo.RepairTicketVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RepairTicketServiceImpl extends ServiceImpl<RepairTicketMapper, RepairTicket> implements RepairTicketService {

    @Autowired
    private UserService userService; // 用于查询用户姓名等信息

    @Override
    public Long submitTicket(Long userId, CreateRepairTicketDTO createDTO) {
        User user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException("提交用户不存在");
        }

        RepairTicket ticket = new RepairTicket();
        ticket.setUserId(userId);
        ticket.setReporterName(user.getNickname()); // 从用户表获取
        ticket.setContactPhone(user.getStudentId()); // 暂用学号作为联系方式，实际项目中应有独立字段
        ticket.setRepairLocation(createDTO.getRepairLocation());
        ticket.setTitle(createDTO.getTitle());
        ticket.setDescription(createDTO.getDescription());
        if (CollUtil.isNotEmpty(createDTO.getAttachmentUrls())) {
            ticket.setAttachmentUrls(String.join(",", createDTO.getAttachmentUrls())); // 拼接为字符串存储
        }
        ticket.setCategory(createDTO.getCategory());
        ticket.setStatus("SUBMITTED"); // 默认状态为已提交
        ticket.setCreateTime(LocalDateTime.now());
        ticket.setUpdateTime(LocalDateTime.now());

        this.save(ticket);
        return ticket.getId();
    }

    @Override
    public RepairTicketVO getTicketById(Long ticketId) {
        RepairTicket ticket = this.getById(ticketId);
        if (ticket == null) {
            throw new BusinessException("工单不存在");
        }
        return convertToVO(ticket);
    }

    @Override
    public List<RepairTicketVO> getTicketsByUserId(Long userId, String status) {
        LambdaQueryWrapper<RepairTicket> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RepairTicket::getUserId, userId);
        if (StrUtil.isNotBlank(status)) {
            queryWrapper.eq(RepairTicket::getStatus, status);
        }
        queryWrapper.orderByDesc(RepairTicket::getCreateTime); // 按创建时间倒序

        List<RepairTicket> list = this.list(queryWrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<RepairTicketVO> getPendingTickets() {
        LambdaQueryWrapper<RepairTicket> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(RepairTicket::getStatus, "SUBMITTED", "ASSIGNED", "IN_PROGRESS"); // 查询待处理的工单
        queryWrapper.orderByAsc(RepairTicket::getCreateTime); // 按创建时间升序（优先处理）

        List<RepairTicket> list = this.list(queryWrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public void updateTicketStatus(Long ticketId, String newStatus, Long staffId) {
        RepairTicket ticket = this.getById(ticketId);
        if (ticket == null) {
            throw new BusinessException("工单不存在");
        }

        // 简单的状态流转校验（可根据业务规则细化）
        String currentStatus = ticket.getStatus();
        if ("COMPLETED".equals(currentStatus) || "CANCELLED".equals(currentStatus)) {
            throw new BusinessException("无法修改已完成或已取消的工单状态");
        }

        ticket.setStatus(newStatus);
        ticket.setUpdateTime(LocalDateTime.now());

        if (staffId != null) {
            // 如果指派了维修人员，更新相关信息
            // 此处假设有一个 StaffService 来查询员工信息，这里简化处理
            ticket.setAssignedStaffId(staffId);
            // ticket.setAssignedStaffName(staffService.getStaffNameById(staffId));
             ticket.setAssignedStaffName("维修员-" + staffId); // 示例
        }

        if ("COMPLETED".equals(newStatus)) {
            ticket.setCompletedTime(LocalDateTime.now());
        }

        this.updateById(ticket);
    }

    /**
     * Entity -> VO
     */
    private RepairTicketVO convertToVO(RepairTicket entity) {
        RepairTicketVO vo = new RepairTicketVO();
        BeanUtils.copyProperties(entity, vo);
        // 处理 attachmentUrls 字符串为 List
        if (StrUtil.isNotBlank(entity.getAttachmentUrls())) {
            vo.setAttachmentUrls(List.of(entity.getAttachmentUrls().split(",")));
        }
        return vo;
    }
}