package com.techsloyd.payments.dto;

import jakarta.validation.constraints.NotBlank;

public class ReceiptPrintRequest {

    @NotBlank
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
