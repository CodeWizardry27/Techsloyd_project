package com.techsloyd.payments.entity;

import com.techsloyd.payments.enumtype.DiscountType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "discounts",
        indexes = {
                @Index(name = "idx_discount_code", columnList = "code", unique = true),
                @Index(name = "idx_discount_active", columnList = "is_active")
        }
)
@Access(AccessType.FIELD)
public class Discount {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType type;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @Column(precision = 10, scale = 2)
    private BigDecimal minPurchaseAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private Integer usageLimit;

    @Column(nullable = false)
    private Integer usageCount = 0;

    /* ---------- ELEMENT COLLECTIONS ---------- */

    @ElementCollection
    @CollectionTable(
            name = "discount_products",
            joinColumns = @JoinColumn(name = "discount_id")
    )
    @Column(name = "product")
    private List<String> applicableProducts;

    @ElementCollection
    @CollectionTable(
            name = "discount_categories",
            joinColumns = @JoinColumn(name = "discount_id")
    )
    @Column(name = "category")
    private List<String> applicableCategories;

    /* ---------- BOOLEAN FLAGS (CRITICAL FIX) ---------- */

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "is_stackable", nullable = false)
    private boolean stackable;

    /* ---------- GETTERS & SETTERS ---------- */

    public UUID getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public DiscountType getType() { return type; }
    public void setType(DiscountType type) { this.type = type; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public BigDecimal getMinPurchaseAmount() { return minPurchaseAmount; }
    public void setMinPurchaseAmount(BigDecimal minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public BigDecimal getMaxDiscountAmount() { return maxDiscountAmount; }
    public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getUsageLimit() { return usageLimit; }
    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsageCount() { return usageCount; }
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public List<String> getApplicableProducts() {
        return applicableProducts;
    }

    public void setApplicableProducts(List<String> applicableProducts) {
        this.applicableProducts = applicableProducts;
    }

    public List<String> getApplicableCategories() {
        return applicableCategories;
    }

    public void setApplicableCategories(List<String> applicableCategories) {
        this.applicableCategories = applicableCategories;
    }

    public boolean getActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean getStackable() { return stackable; }
    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }
}
