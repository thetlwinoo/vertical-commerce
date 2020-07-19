package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.OrderPackages} entity.
 */
public class OrderPackagesDTO implements Serializable {
    
    private Long id;

    private Instant expectedDeliveryDate;

    private Instant orderPlacedOn;

    private Instant orderDeliveredOn;

    private String comments;

    private String deliveryInstructions;

    private String internalComments;

    private BigDecimal packageShippingFee;

    private BigDecimal packageShippingFeeDiscount;

    private BigDecimal packagePrice;

    private BigDecimal packageSubTotal;

    private BigDecimal packageTaxAmount;

    private BigDecimal packageVoucherDiscount;

    private BigDecimal packagePromotionDiscount;

    private Instant pickingCompletedWhen;

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
    private String orderPackageDetails;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long supplierId;

    private String supplierName;

    private Long deliveryMethodId;

    private String deliveryMethodName;

    private Long specialDealsId;

    private Long orderId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Instant getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(Instant orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    public Instant getOrderDeliveredOn() {
        return orderDeliveredOn;
    }

    public void setOrderDeliveredOn(Instant orderDeliveredOn) {
        this.orderDeliveredOn = orderDeliveredOn;
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

    public BigDecimal getPackageShippingFee() {
        return packageShippingFee;
    }

    public void setPackageShippingFee(BigDecimal packageShippingFee) {
        this.packageShippingFee = packageShippingFee;
    }

    public BigDecimal getPackageShippingFeeDiscount() {
        return packageShippingFeeDiscount;
    }

    public void setPackageShippingFeeDiscount(BigDecimal packageShippingFeeDiscount) {
        this.packageShippingFeeDiscount = packageShippingFeeDiscount;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public BigDecimal getPackageSubTotal() {
        return packageSubTotal;
    }

    public void setPackageSubTotal(BigDecimal packageSubTotal) {
        this.packageSubTotal = packageSubTotal;
    }

    public BigDecimal getPackageTaxAmount() {
        return packageTaxAmount;
    }

    public void setPackageTaxAmount(BigDecimal packageTaxAmount) {
        this.packageTaxAmount = packageTaxAmount;
    }

    public BigDecimal getPackageVoucherDiscount() {
        return packageVoucherDiscount;
    }

    public void setPackageVoucherDiscount(BigDecimal packageVoucherDiscount) {
        this.packageVoucherDiscount = packageVoucherDiscount;
    }

    public BigDecimal getPackagePromotionDiscount() {
        return packagePromotionDiscount;
    }

    public void setPackagePromotionDiscount(BigDecimal packagePromotionDiscount) {
        this.packagePromotionDiscount = packagePromotionDiscount;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
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

    public String getOrderPackageDetails() {
        return orderPackageDetails;
    }

    public void setOrderPackageDetails(String orderPackageDetails) {
        this.orderPackageDetails = orderPackageDetails;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodsId) {
        this.deliveryMethodId = deliveryMethodsId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodsName) {
        this.deliveryMethodName = deliveryMethodsName;
    }

    public Long getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(Long specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long ordersId) {
        this.orderId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPackagesDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderPackagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderPackagesDTO{" +
            "id=" + getId() +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", orderPlacedOn='" + getOrderPlacedOn() + "'" +
            ", orderDeliveredOn='" + getOrderDeliveredOn() + "'" +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", packageShippingFee=" + getPackageShippingFee() +
            ", packageShippingFeeDiscount=" + getPackageShippingFeeDiscount() +
            ", packagePrice=" + getPackagePrice() +
            ", packageSubTotal=" + getPackageSubTotal() +
            ", packageTaxAmount=" + getPackageTaxAmount() +
            ", packageVoucherDiscount=" + getPackageVoucherDiscount() +
            ", packagePromotionDiscount=" + getPackagePromotionDiscount() +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", sellerRating=" + getSellerRating() +
            ", sellerReview='" + getSellerReview() + "'" +
            ", deliveryRating=" + getDeliveryRating() +
            ", deliveryReview='" + getDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            ", orderPackageDetails='" + getOrderPackageDetails() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", deliveryMethodId=" + getDeliveryMethodId() +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            ", specialDealsId=" + getSpecialDealsId() +
            ", orderId=" + getOrderId() +
            "}";
    }
}
