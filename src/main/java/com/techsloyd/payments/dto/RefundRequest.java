package com.techsloyd.payments.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class RefundRequest {

    private UUID paymentId;
    private BigDecimal amount;
    private String reason;

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
