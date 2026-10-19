package com.campus.app.service;

import com.campus.domain.dto.ConsumeDTO;
import com.campus.domain.dto.RechargeDTO;
import com.campus.domain.vo.CampusCardVO;
import com.campus.domain.vo.CardTransactionVO;
import java.math.BigDecimal;
import java.util.List;

/**
 * 校园卡服务接口
 */
public interface CampusCardService {

    /**
     * 获取用户校园卡信息
     * @param userId 用户ID
     * @return 校园卡信息
     */
    CampusCardVO getCardInfoByUserId(Long userId);

    /**
     * 校园卡充值
     * @param rechargeDTO 充值信息
     */
    void recharge(RechargeDTO rechargeDTO);

    /**
     * 校园卡消费
     * @param consumeDTO 消费信息
     */
    void consume(ConsumeDTO consumeDTO);

    /**
     * 挂失校园卡
     * @param cardId 校园卡ID
     * @param remark 挂失备注
     */
    void reportLost(Long cardId, String remark);

    /**
     * 解挂校园卡
     * @param cardId 校园卡ID
     */
    void unreportLost(Long cardId);

    /**
     * 获取校园卡余额
     * @param cardId 校园卡ID
     * @return 余额
     */
    BigDecimal getBalance(Long cardId);

    /**
     * 获取校园卡交易记录
     * @param cardId 校园卡ID
     * @param page 页码
     * @param size 页大小
     * @return 交易记录列表
     */
    List<CardTransactionVO> getTransactionHistory(Long cardId, int page, int size);
}