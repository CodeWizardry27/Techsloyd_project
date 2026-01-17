package com.techsloyd.payments.service.impl;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Discount;
import com.techsloyd.payments.enumtype.DiscountType;
import com.techsloyd.payments.repository.DiscountRepository;
import com.techsloyd.payments.service.DiscountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    public DiscountServiceImpl(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Discount> getAll() {
        return repository.findAll();
    }

    @Override
    public Discount create(DiscountCreateRequest request) {

        Discount discount = new Discount();
        discount.setName(request.getName());
        discount.setCode(request.getCode().toUpperCase());
        discount.setType(request.getType());
        discount.setValue(request.getValue());
        discount.setMinPurchaseAmount(request.getMinPurchaseAmount());
        discount.setMaxDiscountAmount(request.getMaxDiscountAmount());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setUsageLimit(request.getUsageLimit());
        discount.setApplicableProducts(request.getApplicableProducts());
        discount.setApplicableCategories(request.getApplicableCategories());
        discount.setActive(true);
        discount.setStackable(request.isStackable());

        return repository.save(discount);
    }

    @Override
    public DiscountApplyResponse apply(DiscountApplyRequest request) {

        Discount discount = fetchAndValidate(
                request.getCode(),
                request.getOrderAmount()
        );

        BigDecimal discountAmount = calculate(
                discount,
                request.getOrderAmount()
        );

        BigDecimal finalAmount =
                request.getOrderAmount().subtract(discountAmount);

        discount.setUsageCount(discount.getUsageCount() + 1);
        repository.save(discount);

        return new DiscountApplyResponse(
                true,
                discountAmount,
                finalAmount,
                "Discount applied successfully"
        );
    }

    @Override
    public void validate(DiscountValidateRequest request) {
        fetchAndValidate(request.getCode(), request.getOrderAmount());
    }

    /* ===================== INTERNAL METHODS ===================== */

    private Discount fetchAndValidate(String code, BigDecimal orderAmount) {

        Discount discount = repository.findByCodeIgnoreCase(code)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid discount code"));

        if (!discount.getActive()) {
            throw new IllegalStateException("Discount is inactive");
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(discount.getStartDate())
                || today.isAfter(discount.getEndDate())) {
            throw new IllegalStateException("Discount expired");
        }

        if (discount.getUsageLimit() != null
                && discount.getUsageCount() >= discount.getUsageLimit()) {
            throw new IllegalStateException("Discount usage limit reached");
        }

        if (discount.getMinPurchaseAmount() != null
                && orderAmount.compareTo(
                discount.getMinPurchaseAmount()) < 0) {
            throw new IllegalStateException("Minimum purchase not met");
        }

        return discount;
    }

    private BigDecimal calculate(Discount discount, BigDecimal orderAmount) {

        BigDecimal discountAmount = BigDecimal.ZERO;

        if (discount.getType() == DiscountType.PERCENTAGE) {
            discountAmount = orderAmount
                    .multiply(discount.getValue())
                    .divide(BigDecimal.valueOf(100));
        }

        if (discount.getType() == DiscountType.FIXED_AMOUNT) {
            discountAmount = discount.getValue();
        }

        if (discount.getMaxDiscountAmount() != null
                && discountAmount.compareTo(
                discount.getMaxDiscountAmount()) > 0) {
            discountAmount = discount.getMaxDiscountAmount();
        }

        return discountAmount.max(BigDecimal.ZERO);
    }
}
