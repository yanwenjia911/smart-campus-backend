package com.campus.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.app.service.CampusCardService;
import com.campus.common.exception.BusinessException;
import com.campus.domain.dto.ConsumeDTO;
import com.campus.domain.dto.RechargeDTO;
import com.campus.domain.entity.CampusCard;
import com.campus.domain.entity.CardTransaction;
import com.campus.domain.mapper.CampusCardMapper;
import com.campus.domain.mapper.CardTransactionMapper;
import com.campus.domain.vo.CampusCardVO;
import com.campus.domain.vo.CardTransactionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 校园卡服务实现（含完整 CRUD，mock 数据从数据库读取）
 */
@Service
@Transactional
public class CampusCardServiceImpl
        extends ServiceImpl<CampusCardMapper, CampusCard>
        implements CampusCardService {

    @Autowired
    private CardTransactionMapper cardTransactionMapper;

    @Override
    public CampusCardVO getCardInfoByUserId(Long userId) {
        LambdaQueryWrapper<CampusCard> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CampusCard::getUserId, userId).last("LIMIT 1");
        CampusCard card = this.getOne(wrapper);
        if (card == null) {
            throw new BusinessException("校园卡信息不存在，请联系管理员");
        }
        return convertToVO(card);
    }

    @Override
    public void recharge(RechargeDTO rechargeDTO) {
        CampusCard card = this.getById(rechargeDTO.getCardId());
        if (card == null) {
            throw new BusinessException("校园卡不存在");
        }
        if ("LOST".equals(card.getStatus())) {
            throw new BusinessException("校园卡已挂失，无法充值");
        }
        if ("DISABLED".equals(card.getStatus())) {
            throw new BusinessException("校园卡已禁用");
        }

        BigDecimal newBalance = card.getBalance().add(rechargeDTO.getAmount());
        card.setBalance(newBalance);
        card.setUpdateTime(LocalDateTime.now());
        this.updateById(card);

        // 记录交易流水
        CardTransaction tx = new CardTransaction();
        tx.setCardId(card.getId());
        tx.setTransactionType("RECHARGE");
        tx.setAmount(rechargeDTO.getAmount());
        tx.setBalanceAfter(newBalance);
        tx.setDescription("在线充值");
        tx.setLocation("微信支付");
        tx.setTransactionTime(LocalDateTime.now());
        tx.setCreateTime(LocalDateTime.now());
        cardTransactionMapper.insert(tx);
    }

    @Override
    public void consume(ConsumeDTO consumeDTO) {
        CampusCard card = this.getById(consumeDTO.getCardId());
        if (card == null) {
            throw new BusinessException("校园卡不存在");
        }
        if (!"ACTIVE".equals(card.getStatus())) {
            throw new BusinessException("校园卡状态异常，无法消费（当前状态：" + card.getStatus() + "）");
        }
        if (card.getBalance().compareTo(consumeDTO.getAmount()) < 0) {
            throw new BusinessException("余额不足，当前余额：" + card.getBalance());
        }

        BigDecimal newBalance = card.getBalance().subtract(consumeDTO.getAmount());
        card.setBalance(newBalance);
        card.setUpdateTime(LocalDateTime.now());
        this.updateById(card);

        // 记录交易流水
        CardTransaction tx = new CardTransaction();
        tx.setCardId(card.getId());
        tx.setTransactionType("CONSUME");
        tx.setAmount(consumeDTO.getAmount());
        tx.setBalanceAfter(newBalance);
        tx.setDescription(consumeDTO.getDescription() != null ? consumeDTO.getDescription() : "刷卡消费");
        tx.setLocation(consumeDTO.getLocation());
        tx.setTransactionTime(LocalDateTime.now());
        tx.setCreateTime(LocalDateTime.now());
        cardTransactionMapper.insert(tx);
    }

    @Override
    public void reportLost(Long cardId, String remark) {
        CampusCard card = this.getById(cardId);
        if (card == null) {
            throw new BusinessException("校园卡不存在");
        }
        if ("LOST".equals(card.getStatus())) {
            throw new BusinessException("校园卡已处于挂失状态");
        }
        card.setStatus("LOST");
        card.setReportedLostTime(LocalDateTime.now());
        card.setLostRemark(remark);
        card.setUpdateTime(LocalDateTime.now());
        this.updateById(card);
    }

    @Override
    public void unreportLost(Long cardId) {
        CampusCard card = this.getById(cardId);
        if (card == null) {
            throw new BusinessException("校园卡不存在");
        }
        if (!"LOST".equals(card.getStatus())) {
            throw new BusinessException("校园卡未处于挂失状态");
        }
        card.setStatus("ACTIVE");
        card.setReportedLostTime(null);
        card.setLostRemark(null);
        card.setUpdateTime(LocalDateTime.now());
        this.updateById(card);
    }

    @Override
    public BigDecimal getBalance(Long cardId) {
        CampusCard card = this.getById(cardId);
        if (card == null) {
            throw new BusinessException("校园卡不存在");
        }
        return card.getBalance();
    }

    @Override
    public List<CardTransactionVO> getTransactionHistory(Long cardId, int page, int size) {
        LambdaQueryWrapper<CardTransaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CardTransaction::getCardId, cardId)
               .orderByDesc(CardTransaction::getTransactionTime);

        Page<CardTransaction> pageObj = new Page<>(page, size);
        cardTransactionMapper.selectPage(pageObj, wrapper);

        return pageObj.getRecords().stream()
                .map(this::convertTxToVO)
                .collect(Collectors.toList());
    }

    // ==================== 私有工具方法 ====================

    private CampusCardVO convertToVO(CampusCard entity) {
        CampusCardVO vo = new CampusCardVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    private CardTransactionVO convertTxToVO(CardTransaction entity) {
        CardTransactionVO vo = new CardTransactionVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}