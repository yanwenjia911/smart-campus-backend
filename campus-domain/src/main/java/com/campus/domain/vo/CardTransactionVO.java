package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 校园卡交易记录视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardTransactionVO {

    private Long id;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String description;
    private String location;
    private LocalDateTime transactionTime;
}