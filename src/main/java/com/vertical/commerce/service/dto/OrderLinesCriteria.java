package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.vertical.commerce.domain.enumeration.OrderLineStatus;
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
 * Criteria class for the {@link com.vertical.commerce.domain.OrderLines} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.OrderLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderLinesCriteria implements Serializable, Criteria {
    /**
     * Class for filtering OrderLineStatus
     */
    public static class OrderLineStatusFilter extends Filter<OrderLineStatus> {

        public OrderLineStatusFilter() {
        }

        public OrderLineStatusFilter(OrderLineStatusFilter filter) {
            super(filter);
        }

        @Override
        public OrderLineStatusFilter copy() {
            return new OrderLineStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantity;

    private StringFilter description;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter unitPriceDiscount;

    private BigDecimalFilter lineTotal;

    private BigDecimalFilter taxRate;

    private IntegerFilter pickedQuantity;

    private InstantFilter pickingCompletedWhen;

    private OrderLineStatusFilter status;

    private StringFilter thumbnailUrl;

    private IntegerFilter lineRating;

    private InstantFilter customerReviewedOn;

    private InstantFilter supplierResponseOn;

    private IntegerFilter likeCount;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter supplierId;

    private LongFilter stockItemId;

    private LongFilter packageTypeId;

    private LongFilter reviewImageId;

    private LongFilter orderId;

    public OrderLinesCriteria() {
    }

    public OrderLinesCriteria(OrderLinesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.unitPriceDiscount = other.unitPriceDiscount == null ? null : other.unitPriceDiscount.copy();
        this.lineTotal = other.lineTotal == null ? null : other.lineTotal.copy();
        this.taxRate = other.taxRate == null ? null : other.taxRate.copy();
        this.pickedQuantity = other.pickedQuantity == null ? null : other.pickedQuantity.copy();
        this.pickingCompletedWhen = other.pickingCompletedWhen == null ? null : other.pickingCompletedWhen.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.lineRating = other.lineRating == null ? null : other.lineRating.copy();
        this.customerReviewedOn = other.customerReviewedOn == null ? null : other.customerReviewedOn.copy();
        this.supplierResponseOn = other.supplierResponseOn == null ? null : other.supplierResponseOn.copy();
        this.likeCount = other.likeCount == null ? null : other.likeCount.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.packageTypeId = other.packageTypeId == null ? null : other.packageTypeId.copy();
        this.reviewImageId = other.reviewImageId == null ? null : other.reviewImageId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public OrderLinesCriteria copy() {
        return new OrderLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimalFilter getUnitPriceDiscount() {
        return unitPriceDiscount;
    }

    public void setUnitPriceDiscount(BigDecimalFilter unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
    }

    public BigDecimalFilter getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimalFilter lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimalFilter getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimalFilter taxRate) {
        this.taxRate = taxRate;
    }

    public IntegerFilter getPickedQuantity() {
        return pickedQuantity;
    }

    public void setPickedQuantity(IntegerFilter pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public InstantFilter getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(InstantFilter pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderLineStatusFilter getStatus() {
        return status;
    }

    public void setStatus(OrderLineStatusFilter status) {
        this.status = status;
    }

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public IntegerFilter getLineRating() {
        return lineRating;
    }

    public void setLineRating(IntegerFilter lineRating) {
        this.lineRating = lineRating;
    }

    public InstantFilter getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public void setCustomerReviewedOn(InstantFilter customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public InstantFilter getSupplierResponseOn() {
        return supplierResponseOn;
    }

    public void setSupplierResponseOn(InstantFilter supplierResponseOn) {
        this.supplierResponseOn = supplierResponseOn;
    }

    public IntegerFilter getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(IntegerFilter likeCount) {
        this.likeCount = likeCount;
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

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(LongFilter packageTypeId) {
        this.packageTypeId = packageTypeId;
    }

    public LongFilter getReviewImageId() {
        return reviewImageId;
    }

    public void setReviewImageId(LongFilter reviewImageId) {
        this.reviewImageId = reviewImageId;
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
        final OrderLinesCriteria that = (OrderLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(description, that.description) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(unitPriceDiscount, that.unitPriceDiscount) &&
            Objects.equals(lineTotal, that.lineTotal) &&
            Objects.equals(taxRate, that.taxRate) &&
            Objects.equals(pickedQuantity, that.pickedQuantity) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(status, that.status) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(lineRating, that.lineRating) &&
            Objects.equals(customerReviewedOn, that.customerReviewedOn) &&
            Objects.equals(supplierResponseOn, that.supplierResponseOn) &&
            Objects.equals(likeCount, that.likeCount) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(packageTypeId, that.packageTypeId) &&
            Objects.equals(reviewImageId, that.reviewImageId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantity,
        description,
        unitPrice,
        unitPriceDiscount,
        lineTotal,
        taxRate,
        pickedQuantity,
        pickingCompletedWhen,
        status,
        thumbnailUrl,
        lineRating,
        customerReviewedOn,
        supplierResponseOn,
        likeCount,
        lastEditedBy,
        lastEditedWhen,
        supplierId,
        stockItemId,
        packageTypeId,
        reviewImageId,
        orderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (unitPriceDiscount != null ? "unitPriceDiscount=" + unitPriceDiscount + ", " : "") +
                (lineTotal != null ? "lineTotal=" + lineTotal + ", " : "") +
                (taxRate != null ? "taxRate=" + taxRate + ", " : "") +
                (pickedQuantity != null ? "pickedQuantity=" + pickedQuantity + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (lineRating != null ? "lineRating=" + lineRating + ", " : "") +
                (customerReviewedOn != null ? "customerReviewedOn=" + customerReviewedOn + ", " : "") +
                (supplierResponseOn != null ? "supplierResponseOn=" + supplierResponseOn + ", " : "") +
                (likeCount != null ? "likeCount=" + likeCount + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (packageTypeId != null ? "packageTypeId=" + packageTypeId + ", " : "") +
                (reviewImageId != null ? "reviewImageId=" + reviewImageId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
