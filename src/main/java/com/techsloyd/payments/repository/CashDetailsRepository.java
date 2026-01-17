package com.techsloyd.payments.repository;

import com.techsloyd.payments.entity.CashDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CashDetailsRepository extends JpaRepository<CashDetails, UUID> {
}
