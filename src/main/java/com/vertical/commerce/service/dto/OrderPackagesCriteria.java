package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
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
 * Criteria class for the {@link com.vertical.commerce.domain.OrderPackages} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.OrderPackagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-packages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderPackagesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter expectedDeliveryDate;

    private InstantFilter orderPlacedOn;

    private InstantFilter orderDeliveredOn;

    private StringFilter comments;

    private StringFilter deliveryInstructions;

    private StringFilter internalComments;

    private BigDecimalFilter packageShippingFee;

    private BigDecimalFilter packageShippingFeeDiscount;

    private BigDecimalFilter packagePrice;

    private BigDecimalFilter packageSubTotal;

    private BigDecimalFilter packageTaxAmount;

    private BigDecimalFilter packageVoucherDiscount;

    private BigDecimalFilter packagePromotionDiscount;

    private InstantFilter pickingCompletedWhen;

    private InstantFilter customerReviewedOn;

    private IntegerFilter sellerRating;

    private IntegerFilter deliveryRating;

    private BooleanFilter reviewAsAnonymous;

    private BooleanFilter completedReview;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter orderLineListId;

    private LongFilter supplierId;

    private LongFilter deliveryMethodId;

    private LongFilter specialDealsId;

    private LongFilter orderId;

    public OrderPackagesCriteria() {
    }

    public OrderPackagesCriteria(OrderPackagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.expectedDeliveryDate = other.expectedDeliveryDate == null ? null : other.expectedDeliveryDate.copy();
        this.orderPlacedOn = other.orderPlacedOn == null ? null : other.orderPlacedOn.copy();
        this.orderDeliveredOn = other.orderDeliveredOn == null ? null : other.orderDeliveredOn.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.deliveryInstructions = other.deliveryInstructions == null ? null : other.deliveryInstructions.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.packageShippingFee = other.packageShippingFee == null ? null : other.packageShippingFee.copy();
        this.packageShippingFeeDiscount = other.packageShippingFeeDiscount == null ? null : other.packageShippingFeeDiscount.copy();
        this.packagePrice = other.packagePrice == null ? null : other.packagePrice.copy();
        this.packageSubTotal = other.packageSubTotal == null ? null : other.packageSubTotal.copy();
        this.packageTaxAmount = other.packageTaxAmount == null ? null : other.packageTaxAmount.copy();
        this.packageVoucherDiscount = other.packageVoucherDiscount == null ? null : other.packageVoucherDiscount.copy();
        this.packagePromotionDiscount = other.packagePromotionDiscount == null ? null : other.packagePromotionDiscount.copy();
        this.pickingCompletedWhen = other.pickingCompletedWhen == null ? null : other.pickingCompletedWhen.copy();
        this.customerReviewedOn = other.customerReviewedOn == null ? null : other.customerReviewedOn.copy();
        this.sellerRating = other.sellerRating == null ? null : other.sellerRating.copy();
        this.deliveryRating = other.deliveryRating == null ? null : other.deliveryRating.copy();
        this.reviewAsAnonymous = other.reviewAsAnonymous == null ? null : other.reviewAsAnonymous.copy();
        this.completedReview = other.completedReview == null ? null : other.completedReview.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.orderLineListId = other.orderLineListId == null ? null : other.orderLineListId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
        this.specialDealsId = other.specialDealsId == null ? null : other.specialDealsId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public OrderPackagesCriteria copy() {
        return new OrderPackagesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(InstantFilter expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public InstantFilter getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(InstantFilter orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    public InstantFilter getOrderDeliveredOn() {
        return orderDeliveredOn;
    }

    public void setOrderDeliveredOn(InstantFilter orderDeliveredOn) {
        this.orderDeliveredOn = orderDeliveredOn;
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

    public BigDecimalFilter getPackageShippingFee() {
        return packageShippingFee;
    }

    public void setPackageShippingFee(BigDecimalFilter packageShippingFee) {
        this.packageShippingFee = packageShippingFee;
    }

    public BigDecimalFilter getPackageShippingFeeDiscount() {
        return packageShippingFeeDiscount;
    }

    public void setPackageShippingFeeDiscount(BigDecimalFilter packageShippingFeeDiscount) {
        this.packageShippingFeeDiscount = packageShippingFeeDiscount;
    }

    public BigDecimalFilter getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimalFilter packagePrice) {
        this.packagePrice = packagePrice;
    }

    public BigDecimalFilter getPackageSubTotal() {
        return packageSubTotal;
    }

    public void setPackageSubTotal(BigDecimalFilter packageSubTotal) {
        this.packageSubTotal = packageSubTotal;
    }

    public BigDecimalFilter getPackageTaxAmount() {
        return packageTaxAmount;
    }

    public void setPackageTaxAmount(BigDecimalFilter packageTaxAmount) {
        this.packageTaxAmount = packageTaxAmount;
    }

    public BigDecimalFilter getPackageVoucherDiscount() {
        return packageVoucherDiscount;
    }

    public void setPackageVoucherDiscount(BigDecimalFilter packageVoucherDiscount) {
        this.packageVoucherDiscount = packageVoucherDiscount;
    }

    public BigDecimalFilter getPackagePromotionDiscount() {
        return packagePromotionDiscount;
    }

    public void setPackagePromotionDiscount(BigDecimalFilter packagePromotionDiscount) {
        this.packagePromotionDiscount = packagePromotionDiscount;
    }

    public InstantFilter getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(InstantFilter pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
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

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public LongFilter getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(LongFilter specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderPackagesCriteria that = (OrderPackagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate) &&
            Objects.equals(orderPlacedOn, that.orderPlacedOn) &&
            Objects.equals(orderDeliveredOn, that.orderDeliveredOn) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(deliveryInstructions, that.deliveryInstructions) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(packageShippingFee, that.packageShippingFee) &&
            Objects.equals(packageShippingFeeDiscount, that.packageShippingFeeDiscount) &&
            Objects.equals(packagePrice, that.packagePrice) &&
            Objects.equals(packageSubTotal, that.packageSubTotal) &&
            Objects.equals(packageTaxAmount, that.packageTaxAmount) &&
            Objects.equals(packageVoucherDiscount, that.packageVoucherDiscount) &&
            Objects.equals(packagePromotionDiscount, that.packagePromotionDiscount) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(customerReviewedOn, that.customerReviewedOn) &&
            Objects.equals(sellerRating, that.sellerRating) &&
            Objects.equals(deliveryRating, that.deliveryRating) &&
            Objects.equals(reviewAsAnonymous, that.reviewAsAnonymous) &&
            Objects.equals(completedReview, that.completedReview) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(orderLineListId, that.orderLineListId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId) &&
            Objects.equals(specialDealsId, that.specialDealsId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        expectedDeliveryDate,
        orderPlacedOn,
        orderDeliveredOn,
        comments,
        deliveryInstructions,
        internalComments,
        packageShippingFee,
        packageShippingFeeDiscount,
        packagePrice,
        packageSubTotal,
        packageTaxAmount,
        packageVoucherDiscount,
        packagePromotionDiscount,
        pickingCompletedWhen,
        customerReviewedOn,
        sellerRating,
        deliveryRating,
        reviewAsAnonymous,
        completedReview,
        lastEditedBy,
        lastEditedWhen,
        orderLineListId,
        supplierId,
        deliveryMethodId,
        specialDealsId,
        orderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderPackagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (expectedDeliveryDate != null ? "expectedDeliveryDate=" + expectedDeliveryDate + ", " : "") +
                (orderPlacedOn != null ? "orderPlacedOn=" + orderPlacedOn + ", " : "") +
                (orderDeliveredOn != null ? "orderDeliveredOn=" + orderDeliveredOn + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (deliveryInstructions != null ? "deliveryInstructions=" + deliveryInstructions + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (packageShippingFee != null ? "packageShippingFee=" + packageShippingFee + ", " : "") +
                (packageShippingFeeDiscount != null ? "packageShippingFeeDiscount=" + packageShippingFeeDiscount + ", " : "") +
                (packagePrice != null ? "packagePrice=" + packagePrice + ", " : "") +
                (packageSubTotal != null ? "packageSubTotal=" + packageSubTotal + ", " : "") +
                (packageTaxAmount != null ? "packageTaxAmount=" + packageTaxAmount + ", " : "") +
                (packageVoucherDiscount != null ? "packageVoucherDiscount=" + packageVoucherDiscount + ", " : "") +
                (packagePromotionDiscount != null ? "packagePromotionDiscount=" + packagePromotionDiscount + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (customerReviewedOn != null ? "customerReviewedOn=" + customerReviewedOn + ", " : "") +
                (sellerRating != null ? "sellerRating=" + sellerRating + ", " : "") +
                (deliveryRating != null ? "deliveryRating=" + deliveryRating + ", " : "") +
                (reviewAsAnonymous != null ? "reviewAsAnonymous=" + reviewAsAnonymous + ", " : "") +
                (completedReview != null ? "completedReview=" + completedReview + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (orderLineListId != null ? "orderLineListId=" + orderLineListId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
