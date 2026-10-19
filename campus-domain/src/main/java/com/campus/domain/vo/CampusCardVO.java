package com.campus.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 校园卡视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusCardVO {

    private Long id;
    private String cardNumber;
    private BigDecimal balance;
    private String status;
    private String cardType;
    private LocalDateTime activateTime;
    private LocalDateTime expireTime;
    private LocalDateTime reportedLostTime;
    private String lostRemark;
}