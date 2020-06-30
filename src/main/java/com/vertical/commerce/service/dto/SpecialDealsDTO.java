package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.SpecialDeals} entity.
 */
public class SpecialDealsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String dealDescription;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    private BigDecimal discountAmount;

    private BigDecimal discountPercentage;

    private String discountCode;

    private BigDecimal unitPrice;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long buyingGroupId;

    private String buyingGroupName;

    private Long customerCategoryId;

    private String customerCategoryName;

    private Long customerId;

    private String customerName;

    private Long productCategoryId;

    private String productCategoryName;

    private Long stockItemId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getBuyingGroupId() {
        return buyingGroupId;
    }

    public void setBuyingGroupId(Long buyingGroupsId) {
        this.buyingGroupId = buyingGroupsId;
    }

    public String getBuyingGroupName() {
        return buyingGroupName;
    }

    public void setBuyingGroupName(String buyingGroupsName) {
        this.buyingGroupName = buyingGroupsName;
    }

    public Long getCustomerCategoryId() {
        return customerCategoryId;
    }

    public void setCustomerCategoryId(Long customerCategoriesId) {
        this.customerCategoryId = customerCategoriesId;
    }

    public String getCustomerCategoryName() {
        return customerCategoryName;
    }

    public void setCustomerCategoryName(String customerCategoriesName) {
        this.customerCategoryName = customerCategoriesName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customersName) {
        this.customerName = customersName;
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

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecialDealsDTO)) {
            return false;
        }

        return id != null && id.equals(((SpecialDealsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecialDealsDTO{" +
            "id=" + getId() +
            ", dealDescription='" + getDealDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", discountCode='" + getDiscountCode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", buyingGroupId=" + getBuyingGroupId() +
            ", buyingGroupName='" + getBuyingGroupName() + "'" +
            ", customerCategoryId=" + getCustomerCategoryId() +
            ", customerCategoryName='" + getCustomerCategoryName() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            ", stockItemId=" + getStockItemId() +
            "}";
    }
}
