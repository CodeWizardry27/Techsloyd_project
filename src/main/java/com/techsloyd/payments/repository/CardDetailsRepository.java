package com.techsloyd.payments.repository;

import com.techsloyd.payments.entity.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardDetailsRepository extends JpaRepository<CardDetails, UUID> {
}
