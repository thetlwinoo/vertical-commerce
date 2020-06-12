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

    private Instant dueDate;

    private Instant expectedDeliveryDate;

    private PaymentStatus paymentStatus;

    private String accountNumber;

    private BigDecimal subTotal;

    private BigDecimal taxAmount;

    private BigDecimal frieight;

    private BigDecimal totalDue;

    private String comments;

    private String deliveryInstructions;

    private String internalComments;

    private Instant pickingCompletedWhen;

    @NotNull
    private OrderStatus status;

    private Instant customerReviewedOn;

    private Integer sellerRating;

    @Lob
    private String sellerReview;

    private Integer deliveryRating;

    @Lob
    private String deliveryReview;

    private Boolean reviewAsAnonymous;

    private Boolean completedReview;

    @Lob
    private String orderLineString;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long customerId;

    private Long shipToAddressId;

    private Long billToAddressId;

    private Long shipMethodId;

    private String shipMethodName;

    private Long currencyRateId;

    private Long paymentMethodId;

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

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getFrieight() {
        return frieight;
    }

    public void setFrieight(BigDecimal frieight) {
        this.frieight = frieight;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public void setCustomerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerReview() {
        return sellerReview;
    }

    public void setSellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
    }

    public Integer getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryReview() {
        return deliveryReview;
    }

    public void setDeliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public Boolean isReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public void setReviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public Boolean isCompletedReview() {
        return completedReview;
    }

    public void setCompletedReview(Boolean completedReview) {
        this.completedReview = completedReview;
    }

    public String getOrderLineString() {
        return orderLineString;
    }

    public void setOrderLineString(String orderLineString) {
        this.orderLineString = orderLineString;
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

    public Long getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(Long shipMethodId) {
        this.shipMethodId = shipMethodId;
    }

    public String getShipMethodName() {
        return shipMethodName;
    }

    public void setShipMethodName(String shipMethodName) {
        this.shipMethodName = shipMethodName;
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
            ", dueDate='" + getDueDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", subTotal=" + getSubTotal() +
            ", taxAmount=" + getTaxAmount() +
            ", frieight=" + getFrieight() +
            ", totalDue=" + getTotalDue() +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", sellerRating=" + getSellerRating() +
            ", sellerReview='" + getSellerReview() + "'" +
            ", deliveryRating=" + getDeliveryRating() +
            ", deliveryReview='" + getDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            ", orderLineString='" + getOrderLineString() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", customerId=" + getCustomerId() +
            ", shipToAddressId=" + getShipToAddressId() +
            ", billToAddressId=" + getBillToAddressId() +
            ", shipMethodId=" + getShipMethodId() +
            ", shipMethodName='" + getShipMethodName() + "'" +
            ", currencyRateId=" + getCurrencyRateId() +
            ", paymentMethodId=" + getPaymentMethodId() +
            ", specialDealsId=" + getSpecialDealsId() +
            "}";
    }
}
