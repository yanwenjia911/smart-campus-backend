package com.campus.controller.api;

import com.campus.app.service.NoticeService;
import com.campus.common.result.R;
import com.campus.domain.dto.CreateNoticeDTO;
import com.campus.domain.vo.NoticeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知公告控制器
 */
@RestController
@RequestMapping("/api/notice")
@Tag(name = "通知公告", description = "发布、查询通知公告")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping("/publish")
    @Operation(summary = "发布通知")
    public R<Long> createNotice(
            @Parameter(description = "发布者ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestBody CreateNoticeDTO createDTO) {
        // 注意：此处应有权限校验，确保调用者是管理员或有发布权限的用户
        Long noticeId = noticeService.createNotice(createDTO, userId);
        return R.ok(noticeId, "通知发布成功");
    }

    @GetMapping("/{noticeId}")
    @Operation(summary = "获取通知详情")
    public R<NoticeVO> getNoticeById(@PathVariable Long noticeId) {
        NoticeVO notice = noticeService.getNoticeById(noticeId);
        return R.ok(notice);
    }

    @GetMapping("/list")
    @Operation(summary = "获取通知列表")
    public R<List<NoticeVO>> getNotices(
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<NoticeVO> notices = noticeService.getNotices(type, page, size);
        return R.ok(notices);
    }

    @GetMapping("/top")
    @Operation(summary = "获取置顶通知")
    public R<List<NoticeVO>> getTopNotices() {
        List<NoticeVO> topNotices = noticeService.getTopNotices();
        return R.ok(topNotices);
    }

    @GetMapping("/latest")
    @Operation(summary = "获取最新通知（首页展示）")
    public R<List<NoticeVO>> getLatestNotices(@RequestParam(defaultValue = "5") int limit) {
        List<NoticeVO> latestNotices = noticeService.getLatestNotices(limit);
        return R.ok(latestNotices);
    }
}