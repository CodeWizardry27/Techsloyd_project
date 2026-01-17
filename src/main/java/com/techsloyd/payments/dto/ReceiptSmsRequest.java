package com.techsloyd.payments.dto;

import jakarta.validation.constraints.NotBlank;

public class ReceiptSmsRequest {

    @NotBlank
    private String transactionId;

    @NotBlank
    private String phoneNumber;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
