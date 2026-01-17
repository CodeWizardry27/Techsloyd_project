package com.techsloyd.payments.service;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(UUID id);

    Transaction createTransaction(CreateTransactionRequest request);

    Transaction voidTransaction(UUID transactionId, VoidTransactionRequest request);

    List<Transaction> searchTransactions(TransactionSearchRequest request);

    TransactionSummaryResponse getSummary(LocalDate fromDate, LocalDate toDate);

    CloseoutReportResponse getDailyCloseout(LocalDate date);
}
