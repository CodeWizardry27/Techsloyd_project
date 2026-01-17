package com.techsloyd.payments.util;

import com.techsloyd.payments.entity.Receipt;
import com.techsloyd.payments.entity.ReceiptItem;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfReceiptGenerator {

    public static byte[] generate(Receipt receipt, byte[] qrCodeImage) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Title
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            document.add(new Paragraph(receipt.getStoreName(), titleFont));
            document.add(new Paragraph(receipt.getStoreAddress()));
            document.add(new Paragraph("Phone: " + receipt.getStorePhone()));
            document.add(Chunk.NEWLINE);

            // Receipt metadata
            document.add(new Paragraph("Receipt No: " + receipt.getReceiptNumber()));
            document.add(new Paragraph("Transaction ID: " + receipt.getTransactionId()));
            document.add(new Paragraph("Cashier: " + receipt.getCashier()));
            document.add(new Paragraph("Date: " + receipt.getTimestamp()));
            document.add(Chunk.NEWLINE);

            // Items
            document.add(new Paragraph("Items:"));
            for (ReceiptItem item : receipt.getItems()) {
                document.add(new Paragraph(
                        item.getItemName() +
                                " x" + item.getQuantity() +
                                " @ " + item.getPrice() +
                                " = " + item.getTotal()
                ));
            }

            document.add(Chunk.NEWLINE);

            // Totals
            document.add(new Paragraph("Subtotal: " + receipt.getSubtotal()));
            document.add(new Paragraph("Tax: " + receipt.getTax()));
            document.add(new Paragraph("Total: " + receipt.getTotal()));
            document.add(new Paragraph("Paid: " + receipt.getAmountPaid()));
            document.add(new Paragraph("Change: " + receipt.getChangeAmount()));

            document.add(Chunk.NEWLINE);

            // QR Code
            if (qrCodeImage != null) {
                Image qrImage = Image.getInstance(qrCodeImage);
                qrImage.scaleToFit(120, 120);
                document.add(qrImage);
            }

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(receipt.getFooter()));

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF receipt", e);
        }
    }
}
