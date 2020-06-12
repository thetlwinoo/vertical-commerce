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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.Reviews} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ReviewsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReviewsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter customerName;

    private StringFilter emailAddress;

    private InstantFilter customerReviewedOn;

    private IntegerFilter sellerRating;

    private IntegerFilter deliveryRating;

    private BooleanFilter reviewAsAnonymous;

    private BooleanFilter completedReview;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter reviewLineListId;

    private LongFilter orderId;

    public ReviewsCriteria() {
    }

    public ReviewsCriteria(ReviewsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.customerName = other.customerName == null ? null : other.customerName.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.customerReviewedOn = other.customerReviewedOn == null ? null : other.customerReviewedOn.copy();
        this.sellerRating = other.sellerRating == null ? null : other.sellerRating.copy();
        this.deliveryRating = other.deliveryRating == null ? null : other.deliveryRating.copy();
        this.reviewAsAnonymous = other.reviewAsAnonymous == null ? null : other.reviewAsAnonymous.copy();
        this.completedReview = other.completedReview == null ? null : other.completedReview.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.reviewLineListId = other.reviewLineListId == null ? null : other.reviewLineListId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public ReviewsCriteria copy() {
        return new ReviewsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
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

    public LongFilter getReviewLineListId() {
        return reviewLineListId;
    }

    public void setReviewLineListId(LongFilter reviewLineListId) {
        this.reviewLineListId = reviewLineListId;
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
        final ReviewsCriteria that = (ReviewsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(customerReviewedOn, that.customerReviewedOn) &&
            Objects.equals(sellerRating, that.sellerRating) &&
            Objects.equals(deliveryRating, that.deliveryRating) &&
            Objects.equals(reviewAsAnonymous, that.reviewAsAnonymous) &&
            Objects.equals(completedReview, that.completedReview) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(reviewLineListId, that.reviewLineListId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        customerName,
        emailAddress,
        customerReviewedOn,
        sellerRating,
        deliveryRating,
        reviewAsAnonymous,
        completedReview,
        lastEditedBy,
        lastEditedWhen,
        reviewLineListId,
        orderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (customerName != null ? "customerName=" + customerName + ", " : "") +
                (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
                (customerReviewedOn != null ? "customerReviewedOn=" + customerReviewedOn + ", " : "") +
                (sellerRating != null ? "sellerRating=" + sellerRating + ", " : "") +
                (deliveryRating != null ? "deliveryRating=" + deliveryRating + ", " : "") +
                (reviewAsAnonymous != null ? "reviewAsAnonymous=" + reviewAsAnonymous + ", " : "") +
                (completedReview != null ? "completedReview=" + completedReview + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (reviewLineListId != null ? "reviewLineListId=" + reviewLineListId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
