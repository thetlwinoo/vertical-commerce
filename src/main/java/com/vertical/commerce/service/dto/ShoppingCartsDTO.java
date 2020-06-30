package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ShoppingCarts} entity.
 */
public class ShoppingCartsDTO implements Serializable {
    
    private Long id;

    private BigDecimal totalPrice;

    private BigDecimal subTotalPrice;

    private BigDecimal totalShippingFee;

    private BigDecimal totalShippingFeeDiscount;

    private BigDecimal promotionTotal;

    private BigDecimal voucherTotal;

    @Lob
    private String packageDetails;

    @Lob
    private String cartString;

    @Lob
    private String dealString;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long cartUserId;

    private String cartUserFullName;

    private Long customerId;

    private String customerName;

    private Long specialDealsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public BigDecimal getTotalShippingFee() {
        return totalShippingFee;
    }

    public void setTotalShippingFee(BigDecimal totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
    }

    public BigDecimal getTotalShippingFeeDiscount() {
        return totalShippingFeeDiscount;
    }

    public void setTotalShippingFeeDiscount(BigDecimal totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
    }

    public BigDecimal getPromotionTotal() {
        return promotionTotal;
    }

    public void setPromotionTotal(BigDecimal promotionTotal) {
        this.promotionTotal = promotionTotal;
    }

    public BigDecimal getVoucherTotal() {
        return voucherTotal;
    }

    public void setVoucherTotal(BigDecimal voucherTotal) {
        this.voucherTotal = voucherTotal;
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
    }

    public String getCartString() {
        return cartString;
    }

    public void setCartString(String cartString) {
        this.cartString = cartString;
    }

    public String getDealString() {
        return dealString;
    }

    public void setDealString(String dealString) {
        this.dealString = dealString;
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

    public Long getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(Long peopleId) {
        this.cartUserId = peopleId;
    }

    public String getCartUserFullName() {
        return cartUserFullName;
    }

    public void setCartUserFullName(String peopleFullName) {
        this.cartUserFullName = peopleFullName;
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

    public Long getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(Long specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCartsDTO)) {
            return false;
        }

        return id != null && id.equals(((ShoppingCartsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingCartsDTO{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", subTotalPrice=" + getSubTotalPrice() +
            ", totalShippingFee=" + getTotalShippingFee() +
            ", totalShippingFeeDiscount=" + getTotalShippingFeeDiscount() +
            ", promotionTotal=" + getPromotionTotal() +
            ", voucherTotal=" + getVoucherTotal() +
            ", packageDetails='" + getPackageDetails() + "'" +
            ", cartString='" + getCartString() + "'" +
            ", dealString='" + getDealString() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", cartUserId=" + getCartUserId() +
            ", cartUserFullName='" + getCartUserFullName() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", specialDealsId=" + getSpecialDealsId() +
            "}";
    }
}
