package com.techsloyd.payments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CloseoutReportResponse {

    private LocalDate date;
    private BigDecimal cashTotal;
    private BigDecimal cardTotal;
    private BigDecimal walletTotal;
    private BigDecimal grandTotal;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getCashTotal() {
        return cashTotal;
    }

    public void setCashTotal(BigDecimal cashTotal) {
        this.cashTotal = cashTotal;
    }

    public BigDecimal getCardTotal() {
        return cardTotal;
    }

    public void setCardTotal(BigDecimal cardTotal) {
        this.cardTotal = cardTotal;
    }

    public BigDecimal getWalletTotal() {
        return walletTotal;
    }

    public void setWalletTotal(BigDecimal walletTotal) {
        this.walletTotal = walletTotal;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }
}
