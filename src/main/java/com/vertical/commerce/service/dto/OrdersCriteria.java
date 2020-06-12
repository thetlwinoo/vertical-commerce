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

    private InstantFilter dueDate;

    private InstantFilter expectedDeliveryDate;

    private PaymentStatusFilter paymentStatus;

    private StringFilter accountNumber;

    private BigDecimalFilter subTotal;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter frieight;

    private BigDecimalFilter totalDue;

    private StringFilter comments;

    private StringFilter deliveryInstructions;

    private StringFilter internalComments;

    private InstantFilter pickingCompletedWhen;

    private OrderStatusFilter status;

    private InstantFilter customerReviewedOn;

    private IntegerFilter sellerRating;

    private IntegerFilter deliveryRating;

    private BooleanFilter reviewAsAnonymous;

    private BooleanFilter completedReview;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter orderLineListId;

    private LongFilter customerId;

    private LongFilter shipToAddressId;

    private LongFilter billToAddressId;

    private LongFilter shipMethodId;

    private LongFilter currencyRateId;

    private LongFilter paymentMethodId;

    private LongFilter orderTrackingId;

    private LongFilter specialDealsId;

    public OrdersCriteria() {
    }

    public OrdersCriteria(OrdersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.expectedDeliveryDate = other.expectedDeliveryDate == null ? null : other.expectedDeliveryDate.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.subTotal = other.subTotal == null ? null : other.subTotal.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.frieight = other.frieight == null ? null : other.frieight.copy();
        this.totalDue = other.totalDue == null ? null : other.totalDue.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.deliveryInstructions = other.deliveryInstructions == null ? null : other.deliveryInstructions.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.pickingCompletedWhen = other.pickingCompletedWhen == null ? null : other.pickingCompletedWhen.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.customerReviewedOn = other.customerReviewedOn == null ? null : other.customerReviewedOn.copy();
        this.sellerRating = other.sellerRating == null ? null : other.sellerRating.copy();
        this.deliveryRating = other.deliveryRating == null ? null : other.deliveryRating.copy();
        this.reviewAsAnonymous = other.reviewAsAnonymous == null ? null : other.reviewAsAnonymous.copy();
        this.completedReview = other.completedReview == null ? null : other.completedReview.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.orderLineListId = other.orderLineListId == null ? null : other.orderLineListId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.shipToAddressId = other.shipToAddressId == null ? null : other.shipToAddressId.copy();
        this.billToAddressId = other.billToAddressId == null ? null : other.billToAddressId.copy();
        this.shipMethodId = other.shipMethodId == null ? null : other.shipMethodId.copy();
        this.currencyRateId = other.currencyRateId == null ? null : other.currencyRateId.copy();
        this.paymentMethodId = other.paymentMethodId == null ? null : other.paymentMethodId.copy();
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

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
    }

    public InstantFilter getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(InstantFilter expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public PaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimalFilter getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimalFilter subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getFrieight() {
        return frieight;
    }

    public void setFrieight(BigDecimalFilter frieight) {
        this.frieight = frieight;
    }

    public BigDecimalFilter getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimalFilter totalDue) {
        this.totalDue = totalDue;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(StringFilter deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public InstantFilter getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(InstantFilter pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderStatusFilter getStatus() {
        return status;
    }

    public void setStatus(OrderStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public void setCustomerReviewedOn(InstantFilter customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public IntegerFilter getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(IntegerFilter sellerRating) {
        this.sellerRating = sellerRating;
    }

    public IntegerFilter getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(IntegerFilter deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public BooleanFilter getReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public void setReviewAsAnonymous(BooleanFilter reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public BooleanFilter getCompletedReview() {
        return completedReview;
    }

    public void setCompletedReview(BooleanFilter completedReview) {
        this.completedReview = completedReview;
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

    public LongFilter getOrderLineListId() {
        return orderLineListId;
    }

    public void setOrderLineListId(LongFilter orderLineListId) {
        this.orderLineListId = orderLineListId;
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

    public LongFilter getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(LongFilter shipMethodId) {
        this.shipMethodId = shipMethodId;
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
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(subTotal, that.subTotal) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(frieight, that.frieight) &&
            Objects.equals(totalDue, that.totalDue) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(deliveryInstructions, that.deliveryInstructions) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(status, that.status) &&
            Objects.equals(customerReviewedOn, that.customerReviewedOn) &&
            Objects.equals(sellerRating, that.sellerRating) &&
            Objects.equals(deliveryRating, that.deliveryRating) &&
            Objects.equals(reviewAsAnonymous, that.reviewAsAnonymous) &&
            Objects.equals(completedReview, that.completedReview) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(orderLineListId, that.orderLineListId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(shipToAddressId, that.shipToAddressId) &&
            Objects.equals(billToAddressId, that.billToAddressId) &&
            Objects.equals(shipMethodId, that.shipMethodId) &&
            Objects.equals(currencyRateId, that.currencyRateId) &&
            Objects.equals(paymentMethodId, that.paymentMethodId) &&
            Objects.equals(orderTrackingId, that.orderTrackingId) &&
            Objects.equals(specialDealsId, that.specialDealsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderDate,
        dueDate,
        expectedDeliveryDate,
        paymentStatus,
        accountNumber,
        subTotal,
        taxAmount,
        frieight,
        totalDue,
        comments,
        deliveryInstructions,
        internalComments,
        pickingCompletedWhen,
        status,
        customerReviewedOn,
        sellerRating,
        deliveryRating,
        reviewAsAnonymous,
        completedReview,
        lastEditedBy,
        lastEditedWhen,
        orderLineListId,
        customerId,
        shipToAddressId,
        billToAddressId,
        shipMethodId,
        currencyRateId,
        paymentMethodId,
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
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (expectedDeliveryDate != null ? "expectedDeliveryDate=" + expectedDeliveryDate + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (subTotal != null ? "subTotal=" + subTotal + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (frieight != null ? "frieight=" + frieight + ", " : "") +
                (totalDue != null ? "totalDue=" + totalDue + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (deliveryInstructions != null ? "deliveryInstructions=" + deliveryInstructions + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (customerReviewedOn != null ? "customerReviewedOn=" + customerReviewedOn + ", " : "") +
                (sellerRating != null ? "sellerRating=" + sellerRating + ", " : "") +
                (deliveryRating != null ? "deliveryRating=" + deliveryRating + ", " : "") +
                (reviewAsAnonymous != null ? "reviewAsAnonymous=" + reviewAsAnonymous + ", " : "") +
                (completedReview != null ? "completedReview=" + completedReview + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (orderLineListId != null ? "orderLineListId=" + orderLineListId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (shipToAddressId != null ? "shipToAddressId=" + shipToAddressId + ", " : "") +
                (billToAddressId != null ? "billToAddressId=" + billToAddressId + ", " : "") +
                (shipMethodId != null ? "shipMethodId=" + shipMethodId + ", " : "") +
                (currencyRateId != null ? "currencyRateId=" + currencyRateId + ", " : "") +
                (paymentMethodId != null ? "paymentMethodId=" + paymentMethodId + ", " : "") +
                (orderTrackingId != null ? "orderTrackingId=" + orderTrackingId + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
            "}";
    }

}
