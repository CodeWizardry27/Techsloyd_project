package com.techsloyd.payments.service.impl;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Payment;
import com.techsloyd.payments.enumtype.PaymentMethod;
import com.techsloyd.payments.enumtype.PaymentStatus;
import com.techsloyd.payments.repository.PaymentRepository;
import com.techsloyd.payments.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment processPayment(PaymentRequest request) {

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setOrderId(request.getOrderId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());

        BigDecimal fee = calculateFee(request.getAmount(), request.getMethod());
        payment.setProcessingFee(fee);

        payment.setStatus(PaymentStatus.completed);
        payment.setTransactionId("txn_" + UUID.randomUUID());
        payment.setProcessedAt(Instant.now());

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void refundPayment(RefundRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.refunded);
        paymentRepository.save(payment);
    }

    @Override
    public PaymentStatus getPaymentStatus(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"))
                .getStatus();
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount, PaymentMethod method) {
        return switch (method) {
            case card -> amount.multiply(BigDecimal.valueOf(0.02));
            case digital_wallet -> amount.multiply(BigDecimal.valueOf(0.015));
            case cash -> BigDecimal.ZERO;
        };
    }

    @Override
    public boolean verifyPayment(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId).isPresent();
    }
}
