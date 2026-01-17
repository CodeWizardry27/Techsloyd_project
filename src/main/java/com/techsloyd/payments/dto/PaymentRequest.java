package com.techsloyd.payments.dto;

import com.techsloyd.payments.enumtype.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest {

    private UUID orderId;
    private UUID customerId;
    private BigDecimal amount;
    private PaymentMethod method;

    private CardDetailsRequest cardDetails;
    private CashDetailsRequest cashDetails;
    private DigitalWalletRequest digitalWalletDetails;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public CardDetailsRequest getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetailsRequest cardDetails) {
        this.cardDetails = cardDetails;
    }

    public CashDetailsRequest getCashDetails() {
        return cashDetails;
    }

    public void setCashDetails(CashDetailsRequest cashDetails) {
        this.cashDetails = cashDetails;
    }

    public DigitalWalletRequest getDigitalWalletDetails() {
        return digitalWalletDetails;
    }

    public void setDigitalWalletDetails(DigitalWalletRequest digitalWalletDetails) {
        this.digitalWalletDetails = digitalWalletDetails;
    }
}
