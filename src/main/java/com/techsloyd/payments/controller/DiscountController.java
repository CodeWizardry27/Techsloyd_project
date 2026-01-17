package com.techsloyd.payments.controller;

import com.techsloyd.payments.dto.*;
import com.techsloyd.payments.entity.Discount;
import com.techsloyd.payments.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService service;

    public DiscountController(DiscountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Discount> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Discount create(@Valid @RequestBody DiscountCreateRequest request) {
        return service.create(request);
    }

    @PostMapping("/apply")
    public DiscountApplyResponse apply(@Valid @RequestBody DiscountApplyRequest request) {
        return service.apply(request);
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validate(@Valid @RequestBody DiscountValidateRequest request) {
        service.validate(request);
    }
}
