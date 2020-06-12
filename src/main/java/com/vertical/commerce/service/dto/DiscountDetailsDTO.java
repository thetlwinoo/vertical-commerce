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
    private BigDecimal amount;

    @NotNull
    private Boolean isPercentage;

    @NotNull
    private Boolean isAllowCombinationDiscount;

    @NotNull
    private Boolean isFinalBillDiscount;

    @NotNull
    private String name;

    @NotNull
    private Integer startCount;

    private Integer endCount;

    @NotNull
    private Integer multiplyCount;

    @NotNull
    private Instant modifiedDate;


    private Long discountId;

    private String discountName;

    private Long productId;

    private String productName;

    private Long productCategoryId;

    private String productCategoryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productsName) {
        this.productName = productsName;
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
            ", amount=" + getAmount() +
            ", isPercentage='" + isIsPercentage() + "'" +
            ", isAllowCombinationDiscount='" + isIsAllowCombinationDiscount() + "'" +
            ", isFinalBillDiscount='" + isIsFinalBillDiscount() + "'" +
            ", name='" + getName() + "'" +
            ", startCount=" + getStartCount() +
            ", endCount=" + getEndCount() +
            ", multiplyCount=" + getMultiplyCount() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", discountId=" + getDiscountId() +
            ", discountName='" + getDiscountName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            "}";
    }
}
