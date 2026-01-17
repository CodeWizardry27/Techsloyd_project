package com.techsloyd.payments.service;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Discount;

import java.util.List;

public interface DiscountService {

    List<Discount> getAll();

    Discount create(DiscountCreateRequest request);

    DiscountApplyResponse apply(DiscountApplyRequest request);

    void validate(DiscountValidateRequest request);
}
