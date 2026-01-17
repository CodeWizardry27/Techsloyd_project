package com.techsloyd.payments.repository;

import com.techsloyd.payments.entity.Transaction;
import com.techsloyd.payments.enumtype.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // 🔹 Used by: GET /api/transactions/{id}
    // (JpaRepository already provides findById)

    // 🔹 Used by: Receipt generation, audit
    Transaction findByReceiptNumber(String receiptNumber);

    // 🔹 Used by: GET /api/transactions/search
    @Query("""
        SELECT t FROM Transaction t
        WHERE (:customerId IS NULL OR t.customerId = :customerId)
          AND (:cashier IS NULL OR t.cashier = :cashier)
          AND (:status IS NULL OR t.status = :status)
          AND (:fromDate IS NULL OR t.createdAt >= :fromDate)
          AND (:toDate IS NULL OR t.createdAt <= :toDate)
    """)
    List<Transaction> searchTransactions(
            @Param("customerId") UUID customerId,
            @Param("cashier") String cashier,
            @Param("status") TransactionStatus status,
            @Param("fromDate") Instant fromDate,
            @Param("toDate") Instant toDate
    );

    // 🔹 Used by: GET /api/transactions/summary
    @Query("""
        SELECT COUNT(t), 
               COALESCE(SUM(t.totalAmount), 0),
               COALESCE(SUM(t.taxAmount), 0),
               COALESCE(SUM(t.discountAmount), 0)
        FROM Transaction t
        WHERE t.createdAt BETWEEN :from AND :to
          AND t.status = 'completed'
    """)
    Object[] getSummary(
            @Param("from") Instant from,
            @Param("to") Instant to
    );

    // 🔹 Used by: GET /api/transactions/closeout
    @Query("""
        SELECT t.paymentMethod, COALESCE(SUM(t.totalAmount), 0)
        FROM Transaction t
        WHERE DATE(t.createdAt) = :date
          AND t.status = 'completed'
        GROUP BY t.paymentMethod
    """)
    List<Object[]> getDailyCloseout(@Param("date") LocalDate date);
}
