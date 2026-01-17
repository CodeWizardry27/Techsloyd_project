package com.techsloyd.payments.service.impl;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Receipt;
import com.techsloyd.payments.entity.ReceiptItem;
import com.techsloyd.payments.repository.ReceiptRepository;
import com.techsloyd.payments.service.ReceiptService;
import com.techsloyd.payments.util.PdfReceiptGenerator;
import com.techsloyd.payments.util.QrCodeGenerator;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final JavaMailSender mailSender;
    public ReceiptServiceImpl(
            ReceiptRepository receiptRepository,
            JavaMailSender mailSender
    ) {
        this.receiptRepository = receiptRepository;
        this.mailSender = mailSender;
    }

    // 1️⃣ Generate receipt
    @Transactional
    @Override
    public Receipt generateReceipt(ReceiptGenerateRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Receipt must have at least one item");
        }

        Receipt receipt = new Receipt();
        receipt.setTransactionId(request.getTransactionId());
        receipt.setReceiptNumber("RCPT-" + System.currentTimeMillis());
        receipt.setStoreName(request.getStoreName());
        receipt.setStoreAddress(request.getStoreAddress());
        receipt.setStorePhone(request.getStorePhone());
        receipt.setCashier(request.getCashier());
        receipt.setPaymentMethod(request.getPaymentMethod());

        BigDecimal subtotal = BigDecimal.ZERO;
        List<ReceiptItem> items = new ArrayList<>();

        for (ReceiptItemRequest itemReq : request.getItems()) {
            ReceiptItem item = new ReceiptItem();
            item.setItemName(itemReq.getItemName());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(itemReq.getPrice());

            BigDecimal itemTotal =
                    itemReq.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            item.setTotal(itemTotal);
            items.add(item);
            subtotal = subtotal.add(itemTotal);
        }

        receipt.setItems(items);
        receipt.setSubtotal(subtotal);
        receipt.setTax(request.getTax());

        BigDecimal total = subtotal.add(request.getTax());
        receipt.setTotal(total);

        receipt.setAmountPaid(request.getAmountPaid());
        receipt.setChangeAmount(request.getAmountPaid().subtract(total));

        receipt.setFooter("Thank you for shopping with us!");
        receipt.setLoyaltyPoints(0);

        return receiptRepository.save(receipt);
    }



    // 2️⃣ Get receipt
    @Override
    public Receipt getReceipt(String transactionId) {
        return receiptRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
    }

    // 6️⃣ Generate PDF (UPDATED)
    @Override
    public byte[] generatePdf(String transactionId) {

        Receipt receipt = getReceipt(transactionId);

        String receiptUrl = "https://your-domain.com/receipts/" + transactionId;

        byte[] qrCode = QrCodeGenerator.generate(receiptUrl, 200, 200);

        return PdfReceiptGenerator.generate(receipt, qrCode);
    }

    // 7️⃣ Generate HTML
    @Override
    public String generateHtml(String transactionId) {

        Receipt receipt = getReceipt(transactionId);

        return """
            <html>
              <body>
                <h2>%s</h2>
                <p>Receipt No: %s</p>
                <p>Total: %s</p>
                <p>Transaction ID: %s</p>
              </body>
            </html>
        """.formatted(
                receipt.getStoreName(),
                receipt.getReceiptNumber(),
                receipt.getTotal(),
                receipt.getTransactionId()
        );
    }

    // 3️⃣ Email receipt (stub)
    @Override
    public void emailReceipt(ReceiptEmailRequest request) {

        Receipt receipt = getReceipt(request.getTransactionId());

        byte[] pdf = generatePdf(receipt.getTransactionId());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(request.getEmail());
            helper.setSubject("Your Receipt - " + receipt.getReceiptNumber());
            helper.setText(
                    "Thank you for your purchase.\n\n" +
                            "Receipt No: " + receipt.getReceiptNumber() + "\n" +
                            "Total: " + receipt.getTotal(),
                    false
            );

            helper.addAttachment(
                    "receipt-" + receipt.getReceiptNumber() + ".pdf",
                    () -> new java.io.ByteArrayInputStream(pdf)
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    // 4️⃣ SMS receipt (stub)
    @Override
    public void smsReceipt(ReceiptSmsRequest request) {
        // integrate SMS later
    }

    // 5️⃣ Print receipt (stub)
    @Override
    public void printReceipt(ReceiptPrintRequest request) {
        // integrate printer later
    }
}
