package com.techsloyd.payments.dto;

import java.math.BigDecimal;

public class CashDetailsRequest {

    private BigDecimal amountTendered;
    private String drawerNumber;

    public BigDecimal getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(BigDecimal amountTendered) {
        this.amountTendered = amountTendered;
    }

    public String getDrawerNumber() {
        return drawerNumber;
    }

    public void setDrawerNumber(String drawerNumber) {
        this.drawerNumber = drawerNumber;
    }
}
