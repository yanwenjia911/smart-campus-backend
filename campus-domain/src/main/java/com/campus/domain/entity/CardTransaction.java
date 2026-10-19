package com.campus.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 校园卡交易记录实体
 */
@Data
@TableName("card_transaction")
public class CardTransaction {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的校园卡ID
     */
    private Long cardId;

    /**
     * 交易类型（CONSUME, RECHARGE, TRANSFER_IN, TRANSFER_OUT, FINE）
     */
    private String transactionType;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易后余额
     */
    private BigDecimal balanceAfter;

    /**
     * 交易描述（如食堂消费、充值、罚款）
     */
    private String description;

    /**
     * 交易地点/商户
     */
    private String location;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}