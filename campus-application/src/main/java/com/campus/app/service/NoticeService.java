package com.campus.app.service;

import com.campus.domain.dto.CreateNoticeDTO;
import com.campus.domain.vo.NoticeVO;
import java.util.List;

/**
 * 通知服务接口
 */
public interface NoticeService {

    /**
     * 发布新通知
     * @param createDTO 通知信息
     * @param userId 发布者ID
     * @return 通知ID
     */
    Long createNotice(CreateNoticeDTO createDTO, Long userId);

    /**
     * 根据ID获取通知详情
     * @param noticeId 通知ID
     * @return 通知详情
     */
    NoticeVO getNoticeById(Long noticeId);

    /**
     * 获取通知列表（支持分页、筛选）
     * @param type 类型筛选（可选）
     * @param page 页码
     * @param size 页大小
     * @return 通知列表
     */
    List<NoticeVO> getNotices(String type, int page, int size);

    /**
     * 获取最新的置顶通知
     * @return 最新置顶通知列表
     */
    List<NoticeVO> getTopNotices();

    /**
     * 获取最新的通知（不分页，用于首页展示）
     * @param limit 数量限制
     * @return 通知列表
     */
    List<NoticeVO> getLatestNotices(int limit);

    /**
     * 更新通知点击量
     * @param noticeId 通知ID
     */
    void incrementViewCount(Long noticeId);
}