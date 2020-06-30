package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.domain.enumeration.OrderStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.Orders} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.OrdersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdersCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PaymentStatus
     */
    public static class PaymentStatusFilter extends Filter<PaymentStatus> {

        public PaymentStatusFilter() {
        }

        public PaymentStatusFilter(PaymentStatusFilter filter) {
            super(filter);
        }

        @Override
        public PaymentStatusFilter copy() {
            return new PaymentStatusFilter(this);
        }

    }
    /**
     * Class for filtering OrderStatus
     */
    public static class OrderStatusFilter extends Filter<OrderStatus> {

        public OrderStatusFilter() {
        }

        public OrderStatusFilter(OrderStatusFilter filter) {
            super(filter);
        }

        @Override
        public OrderStatusFilter copy() {
            return new OrderStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter orderDate;

    private BigDecimalFilter subTotal;

    private BigDecimalFilter totalTaxAmount;

    private BigDecimalFilter totalShippingFee;

    private BigDecimalFilter totalShippingFeeDiscount;

    private BigDecimalFilter totalVoucherDiscount;

    private BigDecimalFilter totalPromtionDiscount;

    private BigDecimalFilter totalDue;

    private PaymentStatusFilter paymentStatus;

    private StringFilter customerPurchaseOrderNumber;

    private OrderStatusFilter status;

    private BooleanFilter isUnderSupplyBackOrdered;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter orderPackageListId;

    private LongFilter customerId;

    private LongFilter shipToAddressId;

    private LongFilter billToAddressId;

    private LongFilter currencyRateId;

    private LongFilter paymentMethodId;

    private LongFilter salePersonId;

    private LongFilter orderTrackingId;

    private LongFilter specialDealsId;

    public OrdersCriteria() {
    }

    public OrdersCriteria(OrdersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.subTotal = other.subTotal == null ? null : other.subTotal.copy();
        this.totalTaxAmount = other.totalTaxAmount == null ? null : other.totalTaxAmount.copy();
        this.totalShippingFee = other.totalShippingFee == null ? null : other.totalShippingFee.copy();
        this.totalShippingFeeDiscount = other.totalShippingFeeDiscount == null ? null : other.totalShippingFeeDiscount.copy();
        this.totalVoucherDiscount = other.totalVoucherDiscount == null ? null : other.totalVoucherDiscount.copy();
        this.totalPromtionDiscount = other.totalPromtionDiscount == null ? null : other.totalPromtionDiscount.copy();
        this.totalDue = other.totalDue == null ? null : other.totalDue.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.customerPurchaseOrderNumber = other.customerPurchaseOrderNumber == null ? null : other.customerPurchaseOrderNumber.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.isUnderSupplyBackOrdered = other.isUnderSupplyBackOrdered == null ? null : other.isUnderSupplyBackOrdered.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.orderPackageListId = other.orderPackageListId == null ? null : other.orderPackageListId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.shipToAddressId = other.shipToAddressId == null ? null : other.shipToAddressId.copy();
        this.billToAddressId = other.billToAddressId == null ? null : other.billToAddressId.copy();
        this.currencyRateId = other.currencyRateId == null ? null : other.currencyRateId.copy();
        this.paymentMethodId = other.paymentMethodId == null ? null : other.paymentMethodId.copy();
        this.salePersonId = other.salePersonId == null ? null : other.salePersonId.copy();
        this.orderTrackingId = other.orderTrackingId == null ? null : other.orderTrackingId.copy();
        this.specialDealsId = other.specialDealsId == null ? null : other.specialDealsId.copy();
    }

    @Override
    public OrdersCriteria copy() {
        return new OrdersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(InstantFilter orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimalFilter getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimalFilter subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimalFilter getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimalFilter totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public BigDecimalFilter getTotalShippingFee() {
        return totalShippingFee;
    }

    public void setTotalShippingFee(BigDecimalFilter totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
    }

    public BigDecimalFilter getTotalShippingFeeDiscount() {
        return totalShippingFeeDiscount;
    }

    public void setTotalShippingFeeDiscount(BigDecimalFilter totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
    }

    public BigDecimalFilter getTotalVoucherDiscount() {
        return totalVoucherDiscount;
    }

    public void setTotalVoucherDiscount(BigDecimalFilter totalVoucherDiscount) {
        this.totalVoucherDiscount = totalVoucherDiscount;
    }

    public BigDecimalFilter getTotalPromtionDiscount() {
        return totalPromtionDiscount;
    }

    public void setTotalPromtionDiscount(BigDecimalFilter totalPromtionDiscount) {
        this.totalPromtionDiscount = totalPromtionDiscount;
    }

    public BigDecimalFilter getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimalFilter totalDue) {
        this.totalDue = totalDue;
    }

    public PaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public StringFilter getCustomerPurchaseOrderNumber() {
        return customerPurchaseOrderNumber;
    }

    public void setCustomerPurchaseOrderNumber(StringFilter customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    }

    public OrderStatusFilter getStatus() {
        return status;
    }

    public void setStatus(OrderStatusFilter status) {
        this.status = status;
    }

    public BooleanFilter getIsUnderSupplyBackOrdered() {
        return isUnderSupplyBackOrdered;
    }

    public void setIsUnderSupplyBackOrdered(BooleanFilter isUnderSupplyBackOrdered) {
        this.isUnderSupplyBackOrdered = isUnderSupplyBackOrdered;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getOrderPackageListId() {
        return orderPackageListId;
    }

    public void setOrderPackageListId(LongFilter orderPackageListId) {
        this.orderPackageListId = orderPackageListId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(LongFilter shipToAddressId) {
        this.shipToAddressId = shipToAddressId;
    }

    public LongFilter getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(LongFilter billToAddressId) {
        this.billToAddressId = billToAddressId;
    }

    public LongFilter getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(LongFilter currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public LongFilter getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(LongFilter paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public LongFilter getSalePersonId() {
        return salePersonId;
    }

    public void setSalePersonId(LongFilter salePersonId) {
        this.salePersonId = salePersonId;
    }

    public LongFilter getOrderTrackingId() {
        return orderTrackingId;
    }

    public void setOrderTrackingId(LongFilter orderTrackingId) {
        this.orderTrackingId = orderTrackingId;
    }

    public LongFilter getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(LongFilter specialDealsId) {
        this.specialDealsId = specialDealsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdersCriteria that = (OrdersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(subTotal, that.subTotal) &&
            Objects.equals(totalTaxAmount, that.totalTaxAmount) &&
            Objects.equals(totalShippingFee, that.totalShippingFee) &&
            Objects.equals(totalShippingFeeDiscount, that.totalShippingFeeDiscount) &&
            Objects.equals(totalVoucherDiscount, that.totalVoucherDiscount) &&
            Objects.equals(totalPromtionDiscount, that.totalPromtionDiscount) &&
            Objects.equals(totalDue, that.totalDue) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(customerPurchaseOrderNumber, that.customerPurchaseOrderNumber) &&
            Objects.equals(status, that.status) &&
            Objects.equals(isUnderSupplyBackOrdered, that.isUnderSupplyBackOrdered) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(orderPackageListId, that.orderPackageListId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(shipToAddressId, that.shipToAddressId) &&
            Objects.equals(billToAddressId, that.billToAddressId) &&
            Objects.equals(currencyRateId, that.currencyRateId) &&
            Objects.equals(paymentMethodId, that.paymentMethodId) &&
            Objects.equals(salePersonId, that.salePersonId) &&
            Objects.equals(orderTrackingId, that.orderTrackingId) &&
            Objects.equals(specialDealsId, that.specialDealsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderDate,
        subTotal,
        totalTaxAmount,
        totalShippingFee,
        totalShippingFeeDiscount,
        totalVoucherDiscount,
        totalPromtionDiscount,
        totalDue,
        paymentStatus,
        customerPurchaseOrderNumber,
        status,
        isUnderSupplyBackOrdered,
        lastEditedBy,
        lastEditedWhen,
        orderPackageListId,
        customerId,
        shipToAddressId,
        billToAddressId,
        currencyRateId,
        paymentMethodId,
        salePersonId,
        orderTrackingId,
        specialDealsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (subTotal != null ? "subTotal=" + subTotal + ", " : "") +
                (totalTaxAmount != null ? "totalTaxAmount=" + totalTaxAmount + ", " : "") +
                (totalShippingFee != null ? "totalShippingFee=" + totalShippingFee + ", " : "") +
                (totalShippingFeeDiscount != null ? "totalShippingFeeDiscount=" + totalShippingFeeDiscount + ", " : "") +
                (totalVoucherDiscount != null ? "totalVoucherDiscount=" + totalVoucherDiscount + ", " : "") +
                (totalPromtionDiscount != null ? "totalPromtionDiscount=" + totalPromtionDiscount + ", " : "") +
                (totalDue != null ? "totalDue=" + totalDue + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (customerPurchaseOrderNumber != null ? "customerPurchaseOrderNumber=" + customerPurchaseOrderNumber + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (isUnderSupplyBackOrdered != null ? "isUnderSupplyBackOrdered=" + isUnderSupplyBackOrdered + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (orderPackageListId != null ? "orderPackageListId=" + orderPackageListId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (shipToAddressId != null ? "shipToAddressId=" + shipToAddressId + ", " : "") +
                (billToAddressId != null ? "billToAddressId=" + billToAddressId + ", " : "") +
                (currencyRateId != null ? "currencyRateId=" + currencyRateId + ", " : "") +
                (paymentMethodId != null ? "paymentMethodId=" + paymentMethodId + ", " : "") +
                (salePersonId != null ? "salePersonId=" + salePersonId + ", " : "") +
                (orderTrackingId != null ? "orderTrackingId=" + orderTrackingId + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
            "}";
    }

}
