package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A DiscountDetails.
 */
@Entity
@Table(name = "discount_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiscountDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "is_percentage", nullable = false)
    private Boolean isPercentage;

    @NotNull
    @Column(name = "is_allow_combination_discount", nullable = false)
    private Boolean isAllowCombinationDiscount;

    @NotNull
    @Column(name = "is_final_bill_discount", nullable = false)
    private Boolean isFinalBillDiscount;

    @Column(name = "start_count")
    private Integer startCount;

    @Column(name = "end_count")
    private Integer endCount;

    @Column(name = "multiply_count")
    private Integer multiplyCount;

    @Column(name = "min_amount", precision = 21, scale = 2)
    private BigDecimal minAmount;

    @Column(name = "max_amount", precision = 21, scale = 2)
    private BigDecimal maxAmount;

    @Column(name = "min_volume_weight")
    private Integer minVolumeWeight;

    @Column(name = "max_volume_weight")
    private Integer maxVolumeWeight;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "discountDetails", allowSetters = true)
    private Discounts discount;

    @ManyToOne
    @JsonIgnoreProperties(value = "discountDetails", allowSetters = true)
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "discountDetails", allowSetters = true)
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DiscountDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DiscountDetails amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isIsPercentage() {
        return isPercentage;
    }

    public DiscountDetails isPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
        return this;
    }

    public void setIsPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public Boolean isIsAllowCombinationDiscount() {
        return isAllowCombinationDiscount;
    }

    public DiscountDetails isAllowCombinationDiscount(Boolean isAllowCombinationDiscount) {
        this.isAllowCombinationDiscount = isAllowCombinationDiscount;
        return this;
    }

    public void setIsAllowCombinationDiscount(Boolean isAllowCombinationDiscount) {
        this.isAllowCombinationDiscount = isAllowCombinationDiscount;
    }

    public Boolean isIsFinalBillDiscount() {
        return isFinalBillDiscount;
    }

    public DiscountDetails isFinalBillDiscount(Boolean isFinalBillDiscount) {
        this.isFinalBillDiscount = isFinalBillDiscount;
        return this;
    }

    public void setIsFinalBillDiscount(Boolean isFinalBillDiscount) {
        this.isFinalBillDiscount = isFinalBillDiscount;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public DiscountDetails startCount(Integer startCount) {
        this.startCount = startCount;
        return this;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public DiscountDetails endCount(Integer endCount) {
        this.endCount = endCount;
        return this;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Integer getMultiplyCount() {
        return multiplyCount;
    }

    public DiscountDetails multiplyCount(Integer multiplyCount) {
        this.multiplyCount = multiplyCount;
        return this;
    }

    public void setMultiplyCount(Integer multiplyCount) {
        this.multiplyCount = multiplyCount;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public DiscountDetails minAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public DiscountDetails maxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
        return this;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getMinVolumeWeight() {
        return minVolumeWeight;
    }

    public DiscountDetails minVolumeWeight(Integer minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
        return this;
    }

    public void setMinVolumeWeight(Integer minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
    }

    public Integer getMaxVolumeWeight() {
        return maxVolumeWeight;
    }

    public DiscountDetails maxVolumeWeight(Integer maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
        return this;
    }

    public void setMaxVolumeWeight(Integer maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public DiscountDetails modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Discounts getDiscount() {
        return discount;
    }

    public DiscountDetails discount(Discounts discounts) {
        this.discount = discounts;
        return this;
    }

    public void setDiscount(Discounts discounts) {
        this.discount = discounts;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public DiscountDetails stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public DiscountDetails productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountDetails)) {
            return false;
        }
        return id != null && id.equals(((DiscountDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            ", isPercentage='" + isIsPercentage() + "'" +
            ", isAllowCombinationDiscount='" + isIsAllowCombinationDiscount() + "'" +
            ", isFinalBillDiscount='" + isIsFinalBillDiscount() + "'" +
            ", startCount=" + getStartCount() +
            ", endCount=" + getEndCount() +
            ", multiplyCount=" + getMultiplyCount() +
            ", minAmount=" + getMinAmount() +
            ", maxAmount=" + getMaxAmount() +
            ", minVolumeWeight=" + getMinVolumeWeight() +
            ", maxVolumeWeight=" + getMaxVolumeWeight() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
