package com.techsloyd.payments.repository;

import com.techsloyd.payments.entity.DigitalWalletDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DigitalWalletDetailsRepository extends JpaRepository<DigitalWalletDetails, UUID> {
}
