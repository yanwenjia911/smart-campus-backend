package com.campus.controller.api;

import com.campus.app.service.RepairTicketService;
import com.campus.common.result.R;
import com.campus.domain.dto.CreateRepairTicketDTO;
import com.campus.domain.vo.RepairTicketVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报修工单控制器
 */
@RestController
@RequestMapping("/api/repair")
@Tag(name = "在线报修", description = "提交、查询、管理报修工单")
public class RepairTicketController {

    @Resource
    private RepairTicketService repairTicketService;

    @PostMapping("/submit")
    @Operation(summary = "提交报修申请")
    public R<Long> submitTicket(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestBody CreateRepairTicketDTO createDTO) {
        Long ticketId = repairTicketService.submitTicket(userId, createDTO);
        return R.ok(ticketId, "报修提交成功");
    }

    @GetMapping("/{ticketId}")
    @Operation(summary = "查询工单详情")
    public R<RepairTicketVO> getTicketById(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @PathVariable Long ticketId) {
        // 注意：这里可能需要增加权限校验，确保当前用户只能看自己的工单
        // 在 Service 实现中可通过 userId 限制查询范围
        RepairTicketVO ticket = repairTicketService.getTicketById(ticketId);
        if (!ticket.getUserId().equals(userId)) {
            return R.error("无权访问此工单");
        }
        return R.ok(ticket);
    }

    @GetMapping("/my-tickets")
    @Operation(summary = "查询我的报修工单列表")
    public R<List<RepairTicketVO>> getMyTickets(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestParam(required = false) String status) {
        List<RepairTicketVO> tickets = repairTicketService.getTicketsByUserId(userId, status);
        return R.ok(tickets);
    }

    @GetMapping("/pending")
    @Operation(summary = "查询待处理的报修工单（管理员/维修员专用）")
    public R<List<RepairTicketVO>> getPendingTickets(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId) {
        // 注意：此处应有权限校验，确保调用者是管理员或维修员
        // 这里仅作演示，实际应通过 @PreAuthorize 或类似方式控制
        List<RepairTicketVO> tickets = repairTicketService.getPendingTickets();
        return R.ok(tickets);
    }

    @PutMapping("/status/{ticketId}")
    @Operation(summary = "更新工单状态（管理员/维修员专用）")
    public R<Void> updateTicketStatus(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @PathVariable Long ticketId,
            @RequestParam String newStatus,
            @RequestParam(required = false) Long staffId) {
        // 注意：此处应有权限校验
        repairTicketService.updateTicketStatus(ticketId, newStatus, staffId);
        return R.ok("工单状态更新成功");
    }
}