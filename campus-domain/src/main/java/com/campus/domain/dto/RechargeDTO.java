package com.campus.domain.dto;

import jakarta.validation.constraints.DecimalMin;
/* import jakarta.validation.constraints.Min; */
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 校园卡充值DTO
 */
@Data
public class RechargeDTO {

    @NotNull(message = "校园卡ID不能为空")
    private Long cardId;

    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0.01", message = "充值金额必须大于0")
    private BigDecimal amount; // ⭐ 改这里：Double → BigDecimal
}