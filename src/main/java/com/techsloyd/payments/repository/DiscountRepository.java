package com.techsloyd.payments.repository;

import com.techsloyd.payments.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, String> {
    Optional<Discount> findByCodeIgnoreCase(String code);
}
