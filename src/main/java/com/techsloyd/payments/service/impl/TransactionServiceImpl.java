package com.techsloyd.payments.service.impl;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Transaction;
import com.techsloyd.payments.entity.TransactionItem;
import com.techsloyd.payments.enumtype.TransactionStatus;
import com.techsloyd.payments.repository.TransactionRepository;
import com.techsloyd.payments.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // 1️⃣ Get all transactions
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 2️⃣ Get transaction by ID
    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    // 3️⃣ Create transaction
    @Override
    public Transaction createTransaction(CreateTransactionRequest request) {

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setReceiptNumber("RCPT-" + System.currentTimeMillis());
        transaction.setCustomerId(request.getCustomerId());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setCashier(request.getCashier());
        transaction.setStoreId(request.getStoreId());
        transaction.setRegisterId(request.getRegisterId());

        transaction.setSubtotal(request.getSubtotal());
        transaction.setTaxAmount(request.getTaxAmount());
        transaction.setDiscountAmount(request.getDiscountAmount());
        transaction.setTotalAmount(request.getTotalAmount());

        transaction.setStatus(TransactionStatus.completed);

        // Map items
        List<TransactionItem> items = new ArrayList<>();
        request.getItems().forEach(itemRequest -> {
            TransactionItem item = new TransactionItem();
            item.setId(UUID.randomUUID());
            item.setTransaction(transaction);
            item.setProductId(itemRequest.getProductId());
            item.setProductName(itemRequest.getProductName());
            item.setVariantId(itemRequest.getVariantId());
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());
            item.setSubtotal(itemRequest.getSubtotal());
            item.setTaxAmount(itemRequest.getTaxAmount());
            item.setDiscountAmount(itemRequest.getDiscountAmount());
            item.setTotal(itemRequest.getTotal());
            items.add(item);
        });

        transaction.setItems(items);

        return transactionRepository.save(transaction);
    }

    // 4️⃣ Void transaction
    @Override
    public Transaction voidTransaction(UUID transactionId, VoidTransactionRequest request) {

        Transaction transaction = getTransactionById(transactionId);

        if (transaction.getStatus() != TransactionStatus.completed) {
            throw new RuntimeException("Only completed transactions can be voided");
        }

        transaction.setStatus(TransactionStatus.VOID);
        transaction.setVoidedAt(Instant.now());
        transaction.setVoidReason(request.getReason());

        return transactionRepository.save(transaction);
    }

    // 5️⃣ Search transactions
    @Override
    public List<Transaction> searchTransactions(TransactionSearchRequest request) {

        Instant from = request.getFromDate() != null
                ? request.getFromDate().atStartOfDay(ZoneOffset.UTC).toInstant()
                : null;

        Instant to = request.getToDate() != null
                ? request.getToDate().plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant()
                : null;

        TransactionStatus status = request.getStatus() != null
                ? TransactionStatus.valueOf(request.getStatus())
                : null;

        return transactionRepository.searchTransactions(
                request.getCustomerId(),
                request.getCashier(),
                status,
                from,
                to
        );
    }

    // 6️⃣ Summary stats
    @Override
    public TransactionSummaryResponse getSummary(LocalDate fromDate, LocalDate toDate) {

        Instant from = fromDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant to = toDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        Object[] result = transactionRepository.getSummary(from, to);

        TransactionSummaryResponse response = new TransactionSummaryResponse();
        response.setTotalTransactions((Long) result[0]);
        response.setTotalRevenue((BigDecimal) result[1]);
        response.setTotalTax((BigDecimal) result[2]);
        response.setTotalDiscount((BigDecimal) result[3]);

        return response;
    }

    // 7️⃣ Daily closeout report
    @Override
    public CloseoutReportResponse getDailyCloseout(LocalDate date) {

        List<Object[]> rows = transactionRepository.getDailyCloseout(date);

        BigDecimal cash = BigDecimal.ZERO;
        BigDecimal card = BigDecimal.ZERO;
        BigDecimal wallet = BigDecimal.ZERO;

        for (Object[] row : rows) {
            String method = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];

            if ("cash".equalsIgnoreCase(method)) cash = amount;
            if ("card".equalsIgnoreCase(method)) card = amount;
            if ("digital_wallet".equalsIgnoreCase(method)) wallet = amount;
        }

        CloseoutReportResponse response = new CloseoutReportResponse();
        response.setDate(date);
        response.setCashTotal(cash);
        response.setCardTotal(card);
        response.setWalletTotal(wallet);
        response.setGrandTotal(cash.add(card).add(wallet));

        return response;
    }
}
