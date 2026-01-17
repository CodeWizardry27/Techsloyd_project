package com.techsloyd.payments.dto;

import java.math.BigDecimal;

public class DiscountApplyResponse {

    private boolean applied;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String message;

    public DiscountApplyResponse(
            boolean applied,
            BigDecimal discountAmount,
            BigDecimal finalAmount,
            String message) {
        this.applied = applied;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.message = message;
    }
}
