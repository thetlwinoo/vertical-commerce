package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Lob;
import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.domain.enumeration.OrderStatus;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Orders} entity.
 */
public class OrdersDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant orderDate;

    private BigDecimal subTotal;

    private BigDecimal totalTaxAmount;

    private BigDecimal totalShippingFee;

    private BigDecimal totalShippingFeeDiscount;

    private BigDecimal totalVoucherDiscount;

    private BigDecimal totalPromtionDiscount;

    private BigDecimal totalDue;

    private PaymentStatus paymentStatus;

    private String customerPurchaseOrderNumber;

    @NotNull
    private OrderStatus status;

    @Lob
    private String orderDetails;

    private Boolean isUnderSupplyBackOrdered;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long customerId;

    private String customerName;

    private Long shipToAddressId;

    private Long billToAddressId;

    private Long currencyRateId;

    private Long paymentMethodId;

    private String paymentMethodName;

    private Long salePersonId;

    private String salePersonFullName;

    private Long specialDealsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
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

    public BigDecimal getTotalVoucherDiscount() {
        return totalVoucherDiscount;
    }

    public void setTotalVoucherDiscount(BigDecimal totalVoucherDiscount) {
        this.totalVoucherDiscount = totalVoucherDiscount;
    }

    public BigDecimal getTotalPromtionDiscount() {
        return totalPromtionDiscount;
    }

    public void setTotalPromtionDiscount(BigDecimal totalPromtionDiscount) {
        this.totalPromtionDiscount = totalPromtionDiscount;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerPurchaseOrderNumber() {
        return customerPurchaseOrderNumber;
    }

    public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Boolean isIsUnderSupplyBackOrdered() {
        return isUnderSupplyBackOrdered;
    }

    public void setIsUnderSupplyBackOrdered(Boolean isUnderSupplyBackOrdered) {
        this.isUnderSupplyBackOrdered = isUnderSupplyBackOrdered;
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

    public Long getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(Long addressesId) {
        this.shipToAddressId = addressesId;
    }

    public Long getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(Long addressesId) {
        this.billToAddressId = addressesId;
    }

    public Long getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(Long currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodsId) {
        this.paymentMethodId = paymentMethodsId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodsName) {
        this.paymentMethodName = paymentMethodsName;
    }

    public Long getSalePersonId() {
        return salePersonId;
    }

    public void setSalePersonId(Long peopleId) {
        this.salePersonId = peopleId;
    }

    public String getSalePersonFullName() {
        return salePersonFullName;
    }

    public void setSalePersonFullName(String peopleFullName) {
        this.salePersonFullName = peopleFullName;
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
        if (!(o instanceof OrdersDTO)) {
            return false;
        }

        return id != null && id.equals(((OrdersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", subTotal=" + getSubTotal() +
            ", totalTaxAmount=" + getTotalTaxAmount() +
            ", totalShippingFee=" + getTotalShippingFee() +
            ", totalShippingFeeDiscount=" + getTotalShippingFeeDiscount() +
            ", totalVoucherDiscount=" + getTotalVoucherDiscount() +
            ", totalPromtionDiscount=" + getTotalPromtionDiscount() +
            ", totalDue=" + getTotalDue() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", customerPurchaseOrderNumber='" + getCustomerPurchaseOrderNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", orderDetails='" + getOrderDetails() + "'" +
            ", isUnderSupplyBackOrdered='" + isIsUnderSupplyBackOrdered() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", shipToAddressId=" + getShipToAddressId() +
            ", billToAddressId=" + getBillToAddressId() +
            ", currencyRateId=" + getCurrencyRateId() +
            ", paymentMethodId=" + getPaymentMethodId() +
            ", paymentMethodName='" + getPaymentMethodName() + "'" +
            ", salePersonId=" + getSalePersonId() +
            ", salePersonFullName='" + getSalePersonFullName() + "'" +
            ", specialDealsId=" + getSpecialDealsId() +
            "}";
    }
}
