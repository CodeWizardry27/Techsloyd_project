package com.techsloyd.payments.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cash_details")
public class CashDetails {

    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "amount_tendered")
    private BigDecimal amountTendered;

    @Column(name = "change_given")
    private BigDecimal changeGiven;

    @Column(name = "drawer_number")
    private String drawerNumber;

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(BigDecimal amountTendered) {
        this.amountTendered = amountTendered;
    }

    public BigDecimal getChangeGiven() {
        return changeGiven;
    }

    public void setChangeGiven(BigDecimal changeGiven) {
        this.changeGiven = changeGiven;
    }

    public String getDrawerNumber() {
        return drawerNumber;
    }

    public void setDrawerNumber(String drawerNumber) {
        this.drawerNumber = drawerNumber;
    }
}
