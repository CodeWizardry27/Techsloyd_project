package com.techsloyd.payments.controller;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Transaction;
import com.techsloyd.payments.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    // ✅ ONLY service injection (correct)
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 1️⃣ GET /api/transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // 2️⃣ GET /api/transactions/{id}
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    // 3️⃣ POST /api/transactions
    @PostMapping
    public Transaction createTransaction(
            @RequestBody CreateTransactionRequest request
    ) {
        return transactionService.createTransaction(request);
    }

    // 4️⃣ POST /api/transactions/{id}/void
    @PostMapping("/{id}/void")
    public Transaction voidTransaction(
            @PathVariable UUID id,
            @RequestBody VoidTransactionRequest request
    ) {
        return transactionService.voidTransaction(id, request);
    }

    // 5️⃣ GET /api/transactions/search
    @GetMapping("/search")
    public List<Transaction> searchTransactions(
            TransactionSearchRequest request
    ) {
        return transactionService.searchTransactions(request);
    }

    // 6️⃣ GET /api/transactions/summary
    @GetMapping("/summary")
    public TransactionSummaryResponse getSummary(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {
        return transactionService.getSummary(fromDate, toDate);
    }

    // 7️⃣ GET /api/transactions/closeout
    @GetMapping("/closeout")
    public CloseoutReportResponse getDailyCloseout(
            @RequestParam LocalDate date
    ) {
        return transactionService.getDailyCloseout(date);
    }
}
