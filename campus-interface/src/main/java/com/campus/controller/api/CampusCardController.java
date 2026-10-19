package com.campus.controller.api;

import com.campus.app.service.CampusCardService;
import com.campus.common.result.R;
import com.campus.domain.dto.ConsumeDTO;
import com.campus.domain.dto.RechargeDTO;
import com.campus.domain.vo.CampusCardVO;
import com.campus.domain.vo.CardTransactionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 校园卡控制器
 */
@RestController
@RequestMapping("/api/card")
@Tag(name = "校园卡", description = "校园卡信息查询、充值、消费、挂失等")
public class CampusCardController {
    //测试
    @GetMapping("/ping")
    public String ping() {
        return "card api ok";
    }

    @Resource
    private CampusCardService campusCardService;

    @GetMapping("/info")
    @Operation(summary = "获取我的校园卡信息")
    public R<CampusCardVO> getCardInfo(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId) {
        CampusCardVO card = campusCardService.getCardInfoByUserId(userId);
        return R.ok(card);
    }

    @PostMapping("/recharge")
    @Operation(summary = "校园卡充值")
    public R<Void> recharge(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestBody RechargeDTO rechargeDTO) {
        // 注意：实际项目中应有支付流程，这里仅模拟余额变动
        // 需要验证充值卡ID是否属于当前用户
        CampusCardVO cardInfo = campusCardService.getCardInfoByUserId(userId);
        if (!cardInfo.getId().equals(rechargeDTO.getCardId())) {
            return R.error("无权操作此校园卡");
        }
        campusCardService.recharge(rechargeDTO);
        return R.ok("充值成功");
    }

    @PostMapping("/consume")
    @Operation(summary = "校园卡消费")
    public R<Void> consume(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestBody ConsumeDTO consumeDTO) {
        // 验证消费卡ID是否属于当前用户
        CampusCardVO cardInfo = campusCardService.getCardInfoByUserId(userId);
        if (!cardInfo.getId().equals(consumeDTO.getCardId())) {
            return R.error("无权操作此校园卡");
        }
        campusCardService.consume(consumeDTO);
        return R.ok("消费成功");
    }

    @PostMapping("/report-lost")
    @Operation(summary = "挂失校园卡")
    public R<Void> reportLost(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestParam Long cardId,
            @RequestParam(required = false) String remark) {
        CampusCardVO cardInfo = campusCardService.getCardInfoByUserId(userId);
        if (!cardInfo.getId().equals(cardId)) {
            return R.error("无权操作此校园卡");
        }
        campusCardService.reportLost(cardId, remark);
        return R.ok("挂失成功");
    }

    @PostMapping("/unreport-lost")
    @Operation(summary = "解挂校园卡")
    public R<Void> unreportLost(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestParam Long cardId) {
        CampusCardVO cardInfo = campusCardService.getCardInfoByUserId(userId);
        if (!cardInfo.getId().equals(cardId)) {
            return R.error("无权操作此校园卡");
        }
        campusCardService.unreportLost(cardId);
        return R.ok("解挂成功");
    }

    @GetMapping("/balance")
    @Operation(summary = "查询校园卡余额")
    public R<BigDecimal> getBalance(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId) {
        CampusCardVO cardInfo = campusCardService.getCardInfoByUserId(userId);
        BigDecimal balance = campusCardService.getBalance(cardInfo.getId());
        return R.ok(balance);
    }

    @GetMapping("/transactions")
    @Operation(summary = "获取校园卡交易记录")
    public R<List<CardTransactionVO>> getTransactions(
            @Parameter(description = "用户ID，通过拦截器注入") @RequestAttribute("userId") Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        CampusCardVO cardInfo = campusCardService.getCardInfoByUserId(userId);
        List<CardTransactionVO> transactions = campusCardService.getTransactionHistory(cardInfo.getId(), page, size);
        return R.ok(transactions);
    }
}