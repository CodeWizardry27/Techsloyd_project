package com.techsloyd.payments.controller;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Payment;
import com.techsloyd.payments.enumtype.PaymentMethod;
import com.techsloyd.payments.enumtype.PaymentStatus;
import com.techsloyd.payments.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 1️⃣ Get payment methods
    @GetMapping("/methods")
    public List<String> getMethods() {
        return List.of("cash", "card", "digital_wallet");
    }

    // 2️⃣ Process payment
    @PostMapping("/process")
    public Payment processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }

    // 3️⃣ Refund payment
    @PostMapping("/refund")
    public void refund(@RequestBody RefundRequest request) {
        paymentService.refundPayment(request);
    }

    // 4️⃣ Payment status
    @GetMapping("/{id}/status")
    public PaymentStatus status(@PathVariable UUID id) {
        return paymentService.getPaymentStatus(id);
    }

    // 5️⃣ Calculate fee
    @GetMapping("/fee")
    public BigDecimal fee(@RequestParam BigDecimal amount,
                          @RequestParam PaymentMethod method) {
        return paymentService.calculateFee(amount, method);
    }

    // 6️⃣ Verify payment
    @PostMapping("/verify")
    public boolean verify(@RequestBody VerifyPaymentRequest request) {
        return paymentService.verifyPayment(request.getTransactionId());
    }
}
