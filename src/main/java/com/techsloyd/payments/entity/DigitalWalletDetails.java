package com.techsloyd.payments.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "digital_wallet_details")
public class DigitalWalletDetails {

    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private String provider;

    @Column(name = "wallet_transaction_id")
    private String walletTransactionId;

    // getters & setters
}
