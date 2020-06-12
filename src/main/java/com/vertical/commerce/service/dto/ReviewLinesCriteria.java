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
 * Criteria class for the {@link com.vertical.commerce.domain.ReviewLines} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ReviewLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /review-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReviewLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter stockItemRating;

    private InstantFilter customerReviewedOn;

    private BooleanFilter completedReviewLine;

    private InstantFilter supplierResponseOn;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter reviewImageId;

    private LongFilter stockItemId;

    private LongFilter orderLineId;

    private LongFilter reviewId;

    public ReviewLinesCriteria() {
    }

    public ReviewLinesCriteria(ReviewLinesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockItemRating = other.stockItemRating == null ? null : other.stockItemRating.copy();
        this.customerReviewedOn = other.customerReviewedOn == null ? null : other.customerReviewedOn.copy();
        this.completedReviewLine = other.completedReviewLine == null ? null : other.completedReviewLine.copy();
        this.supplierResponseOn = other.supplierResponseOn == null ? null : other.supplierResponseOn.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.reviewImageId = other.reviewImageId == null ? null : other.reviewImageId.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.orderLineId = other.orderLineId == null ? null : other.orderLineId.copy();
        this.reviewId = other.reviewId == null ? null : other.reviewId.copy();
    }

    @Override
    public ReviewLinesCriteria copy() {
        return new ReviewLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getStockItemRating() {
        return stockItemRating;
    }

    public void setStockItemRating(IntegerFilter stockItemRating) {
        this.stockItemRating = stockItemRating;
    }

    public InstantFilter getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public void setCustomerReviewedOn(InstantFilter customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public BooleanFilter getCompletedReviewLine() {
        return completedReviewLine;
    }

    public void setCompletedReviewLine(BooleanFilter completedReviewLine) {
        this.completedReviewLine = completedReviewLine;
    }

    public InstantFilter getSupplierResponseOn() {
        return supplierResponseOn;
    }

    public void setSupplierResponseOn(InstantFilter supplierResponseOn) {
        this.supplierResponseOn = supplierResponseOn;
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

    public LongFilter getReviewImageId() {
        return reviewImageId;
    }

    public void setReviewImageId(LongFilter reviewImageId) {
        this.reviewImageId = reviewImageId;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(LongFilter orderLineId) {
        this.orderLineId = orderLineId;
    }

    public LongFilter getReviewId() {
        return reviewId;
    }

    public void setReviewId(LongFilter reviewId) {
        this.reviewId = reviewId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReviewLinesCriteria that = (ReviewLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockItemRating, that.stockItemRating) &&
            Objects.equals(customerReviewedOn, that.customerReviewedOn) &&
            Objects.equals(completedReviewLine, that.completedReviewLine) &&
            Objects.equals(supplierResponseOn, that.supplierResponseOn) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(reviewImageId, that.reviewImageId) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(orderLineId, that.orderLineId) &&
            Objects.equals(reviewId, that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockItemRating,
        customerReviewedOn,
        completedReviewLine,
        supplierResponseOn,
        lastEditedBy,
        lastEditedWhen,
        reviewImageId,
        stockItemId,
        orderLineId,
        reviewId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockItemRating != null ? "stockItemRating=" + stockItemRating + ", " : "") +
                (customerReviewedOn != null ? "customerReviewedOn=" + customerReviewedOn + ", " : "") +
                (completedReviewLine != null ? "completedReviewLine=" + completedReviewLine + ", " : "") +
                (supplierResponseOn != null ? "supplierResponseOn=" + supplierResponseOn + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (reviewImageId != null ? "reviewImageId=" + reviewImageId + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (orderLineId != null ? "orderLineId=" + orderLineId + ", " : "") +
                (reviewId != null ? "reviewId=" + reviewId + ", " : "") +
            "}";
    }

}
