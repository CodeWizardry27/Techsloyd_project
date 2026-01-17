package com.techsloyd.payments.controller;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Receipt;
import com.techsloyd.payments.service.ReceiptService;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // 1️⃣ Generate receipt
    @PostMapping("/generate")
    public Receipt generate(@Valid @RequestBody ReceiptGenerateRequest request) {
        return receiptService.generateReceipt(request);
    }

    // 2️⃣ Get receipt data
    @GetMapping("/{transactionId}")
    public Receipt get(@PathVariable String transactionId) {
        return receiptService.getReceipt(transactionId);
    }

    // 3️⃣ Get HTML receipt
    @GetMapping(value = "/{transactionId}/html", produces = MediaType.TEXT_HTML_VALUE)
    public String html(@PathVariable String transactionId) {
        return receiptService.generateHtml(transactionId);
    }

    // 4️⃣ Get PDF receipt
    @GetMapping(value = "/{transactionId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] pdf(@PathVariable String transactionId) {
        return receiptService.generatePdf(transactionId);
    }

    // 5️⃣ Email receipt
    @PostMapping("/email")
    public void email(@Valid @RequestBody ReceiptEmailRequest request) {
        receiptService.emailReceipt(request);
    }

    // 6️⃣ SMS receipt
    @PostMapping("/sms")
    public void sms(@Valid @RequestBody ReceiptSmsRequest request) {
        receiptService.smsReceipt(request);
    }

    // 7️⃣ Print receipt
    @PostMapping("/print")
    public void print(@Valid @RequestBody ReceiptPrintRequest request) {
        receiptService.printReceipt(request);
    }
}
