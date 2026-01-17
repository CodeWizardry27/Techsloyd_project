package com.techsloyd.payments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptGenerateRequest {

    @NotBlank
    private String transactionId;

    @NotBlank
    private String storeName;

    private String storeAddress;
    private String storePhone;
    private String cashier;

    @NotNull
    private List<ReceiptItemRequest> items;

    private BigDecimal tax;
    private String paymentMethod;
    private BigDecimal amountPaid;

    /* getters & setters */

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStoreAddress() { return storeAddress; }
    public void setStoreAddress(String storeAddress) { this.storeAddress = storeAddress; }

    public String getStorePhone() { return storePhone; }
    public void setStorePhone(String storePhone) { this.storePhone = storePhone; }

    public String getCashier() { return cashier; }
    public void setCashier(String cashier) { this.cashier = cashier; }

    public List<ReceiptItemRequest> getItems() { return items; }
    public void setItems(List<ReceiptItemRequest> items) { this.items = items; }

    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }
}
