package com.techsloyd.payments.service;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Payment;
import com.techsloyd.payments.enumtype.PaymentMethod;
import com.techsloyd.payments.enumtype.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {

    Payment processPayment(PaymentRequest request);

    void refundPayment(RefundRequest request);

    PaymentStatus getPaymentStatus(UUID paymentId);

    BigDecimal calculateFee(BigDecimal amount, PaymentMethod method);

    boolean verifyPayment(String transactionId);
}
