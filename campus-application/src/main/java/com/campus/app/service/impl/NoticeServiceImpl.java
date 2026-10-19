package com.campus.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.app.service.NoticeService;
import com.campus.common.exception.BusinessException;
import com.campus.domain.dto.CreateNoticeDTO;
import com.campus.domain.entity.Notice;
import com.campus.domain.mapper.NoticeMapper;
import com.campus.domain.vo.NoticeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public Long createNotice(CreateNoticeDTO createDTO, Long userId) {
        Objects.requireNonNull(createDTO, "createDTO must not be null");

        Notice notice = new Notice();
        BeanUtils.copyProperties(createDTO, notice);

        if (createDTO.getEffectiveTime() == null) {
            notice.setEffectiveTime(LocalDateTime.now());
        }
        notice.setPublishTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        notice.setViewCount(0L);
        notice.setCreatorId(userId);

        if (CollUtil.isNotEmpty(createDTO.getAttachmentUrls())) {
            notice.setAttachmentUrls(String.join(",", createDTO.getAttachmentUrls()));
        }

        this.save(notice);
        return notice.getId();
    }

    @Override
    public NoticeVO getNoticeById(Long noticeId) {
        Notice notice = this.getById(noticeId);
        if (notice == null) {
            throw new BusinessException("通知不存在");
        }

        // 更新浏览量
        incrementViewCount(noticeId);
        return convertToVO(notice);
    }

    @Override
    public List<NoticeVO> getNotices(String type, int page, int size) {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(type), Notice::getType, type)
                .and(wrapper -> wrapper.le(Notice::getEffectiveTime, LocalDateTime.now())
                        .ge(Notice::getExpireTime, LocalDateTime.now()).or()
                        .apply("expire_time IS NULL"))
                .orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getPublishTime);

        IPage<Notice> iPage = new Page<>(page, size);
        this.page(iPage, queryWrapper);

        return iPage.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<NoticeVO> getTopNotices() {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getIsTop, 1)
                .le(Notice::getEffectiveTime, LocalDateTime.now())
                .and(wrapper -> wrapper.ge(Notice::getExpireTime, LocalDateTime.now()).or()
                        .apply("expire_time IS NULL"))
                .orderByDesc(Notice::getPublishTime);

        List<Notice> list = this.list(queryWrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<NoticeVO> getLatestNotices(int limit) {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(Notice::getEffectiveTime, LocalDateTime.now())
                .and(wrapper -> wrapper.ge(Notice::getExpireTime, LocalDateTime.now()).or()
                        .apply("expire_time IS NULL"))
                .orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getPublishTime)
                .last("LIMIT " + limit);

        List<Notice> list = this.list(queryWrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public void incrementViewCount(Long noticeId) {
    Notice existing = this.getById(noticeId);
    if (existing == null) {
        throw new BusinessException("通知不存在");
    }

    Notice notice = new Notice();
    notice.setId(noticeId);

    // 使用 Objects.requireNonNullElse 确保不会为 null
    Long current = Objects.requireNonNullElse(existing.getViewCount(), 0L);
    notice.setViewCount(current + 1);

    this.update(notice, new LambdaQueryWrapper<Notice>().eq(Notice::getId, noticeId));
    }

    /**
     * Entity -> VO
     */
    private NoticeVO convertToVO(Notice entity) {
        NoticeVO vo = new NoticeVO();
        BeanUtils.copyProperties(entity, vo);
        if (StrUtil.isNotBlank(entity.getAttachmentUrls())) {
            vo.setAttachmentUrls(List.of(entity.getAttachmentUrls().split(",")));
        }
        return vo;
    }
}