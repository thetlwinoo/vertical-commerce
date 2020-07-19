package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.DiscountDetails} entity.
 */
public class DiscountDetailsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Boolean isPercentage;

    @NotNull
    private Boolean isAllowCombinationDiscount;

    @NotNull
    private Boolean isFinalBillDiscount;

    private Integer startCount;

    private Integer endCount;

    private Integer multiplyCount;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer minVolumeWeight;

    private Integer maxVolumeWeight;

    @NotNull
    private Instant modifiedDate;


    private Long discountId;

    private String discountName;

    private Long stockItemId;

    private String stockItemName;

    private Long productCategoryId;

    private String productCategoryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public Boolean isIsAllowCombinationDiscount() {
        return isAllowCombinationDiscount;
    }

    public void setIsAllowCombinationDiscount(Boolean isAllowCombinationDiscount) {
        this.isAllowCombinationDiscount = isAllowCombinationDiscount;
    }

    public Boolean isIsFinalBillDiscount() {
        return isFinalBillDiscount;
    }

    public void setIsFinalBillDiscount(Boolean isFinalBillDiscount) {
        this.isFinalBillDiscount = isFinalBillDiscount;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Integer getMultiplyCount() {
        return multiplyCount;
    }

    public void setMultiplyCount(Integer multiplyCount) {
        this.multiplyCount = multiplyCount;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getMinVolumeWeight() {
        return minVolumeWeight;
    }

    public void setMinVolumeWeight(Integer minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
    }

    public Integer getMaxVolumeWeight() {
        return maxVolumeWeight;
    }

    public void setMaxVolumeWeight(Integer maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountsId) {
        this.discountId = discountsId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountsName) {
        this.discountName = discountsName;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemsName) {
        this.stockItemName = stockItemsName;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((DiscountDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountDetailsDTO{" +
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
            ", discountId=" + getDiscountId() +
            ", discountName='" + getDiscountName() + "'" +
            ", stockItemId=" + getStockItemId() +
            ", stockItemName='" + getStockItemName() + "'" +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            "}";
    }
}
