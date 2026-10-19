package com.campus.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 校园卡消费 DTO
 */
@Data
public class ConsumeDTO {

    @NotNull(message = "校园卡ID不能为空")
    private Long cardId;

    @DecimalMin(value = "0.01", message = "消费金额不能小于0.01元")
    private BigDecimal amount;

    @NotBlank(message = "消费地点不能为空")
    private String location;

    private String description = "校园卡消费";
}