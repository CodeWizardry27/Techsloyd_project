package com.techsloyd.payments.service;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Receipt;

public interface ReceiptService {

    Receipt generateReceipt(ReceiptGenerateRequest request);

    Receipt getReceipt(String transactionId);

    byte[] generatePdf(String transactionId);

    String generateHtml(String transactionId);

    void emailReceipt(ReceiptEmailRequest request);

    void smsReceipt(ReceiptSmsRequest request);

    void printReceipt(ReceiptPrintRequest request);
}
