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

    private StringFilter description;

    private IntegerFilter quantity;

    private BigDecimalFilter taxRate;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter unitPriceDiscount;

    private IntegerFilter pickedQuantity;

    private InstantFilter pickingCompletedWhen;

    private OrderLineStatusFilter status;

    private IntegerFilter lineRating;

    private StringFilter stockItemPhoto;

    private StringFilter reviewPhoto;

    private InstantFilter customerReviewedOn;

    private InstantFilter supplierResponseOn;

    private IntegerFilter likeCount;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter stockItemId;

    private LongFilter packageTypeId;

    private LongFilter supplierId;

    private LongFilter orderPackageId;

    public OrderLinesCriteria() {
    }

    public OrderLinesCriteria(OrderLinesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.taxRate = other.taxRate == null ? null : other.taxRate.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.unitPriceDiscount = other.unitPriceDiscount == null ? null : other.unitPriceDiscount.copy();
        this.pickedQuantity = other.pickedQuantity == null ? null : other.pickedQuantity.copy();
        this.pickingCompletedWhen = other.pickingCompletedWhen == null ? null : other.pickingCompletedWhen.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lineRating = other.lineRating == null ? null : other.lineRating.copy();
        this.stockItemPhoto = other.stockItemPhoto == null ? null : other.stockItemPhoto.copy();
        this.reviewPhoto = other.reviewPhoto == null ? null : other.reviewPhoto.copy();
        this.customerReviewedOn = other.customerReviewedOn == null ? null : other.customerReviewedOn.copy();
        this.supplierResponseOn = other.supplierResponseOn == null ? null : other.supplierResponseOn.copy();
        this.likeCount = other.likeCount == null ? null : other.likeCount.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.packageTypeId = other.packageTypeId == null ? null : other.packageTypeId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.orderPackageId = other.orderPackageId == null ? null : other.orderPackageId.copy();
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimalFilter taxRate) {
        this.taxRate = taxRate;
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

    public IntegerFilter getLineRating() {
        return lineRating;
    }

    public void setLineRating(IntegerFilter lineRating) {
        this.lineRating = lineRating;
    }

    public StringFilter getStockItemPhoto() {
        return stockItemPhoto;
    }

    public void setStockItemPhoto(StringFilter stockItemPhoto) {
        this.stockItemPhoto = stockItemPhoto;
    }

    public StringFilter getReviewPhoto() {
        return reviewPhoto;
    }

    public void setReviewPhoto(StringFilter reviewPhoto) {
        this.reviewPhoto = reviewPhoto;
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

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getOrderPackageId() {
        return orderPackageId;
    }

    public void setOrderPackageId(LongFilter orderPackageId) {
        this.orderPackageId = orderPackageId;
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
            Objects.equals(description, that.description) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(taxRate, that.taxRate) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(unitPriceDiscount, that.unitPriceDiscount) &&
            Objects.equals(pickedQuantity, that.pickedQuantity) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lineRating, that.lineRating) &&
            Objects.equals(stockItemPhoto, that.stockItemPhoto) &&
            Objects.equals(reviewPhoto, that.reviewPhoto) &&
            Objects.equals(customerReviewedOn, that.customerReviewedOn) &&
            Objects.equals(supplierResponseOn, that.supplierResponseOn) &&
            Objects.equals(likeCount, that.likeCount) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(packageTypeId, that.packageTypeId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(orderPackageId, that.orderPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        quantity,
        taxRate,
        unitPrice,
        unitPriceDiscount,
        pickedQuantity,
        pickingCompletedWhen,
        status,
        lineRating,
        stockItemPhoto,
        reviewPhoto,
        customerReviewedOn,
        supplierResponseOn,
        likeCount,
        lastEditedBy,
        lastEditedWhen,
        stockItemId,
        packageTypeId,
        supplierId,
        orderPackageId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (taxRate != null ? "taxRate=" + taxRate + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (unitPriceDiscount != null ? "unitPriceDiscount=" + unitPriceDiscount + ", " : "") +
                (pickedQuantity != null ? "pickedQuantity=" + pickedQuantity + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lineRating != null ? "lineRating=" + lineRating + ", " : "") +
                (stockItemPhoto != null ? "stockItemPhoto=" + stockItemPhoto + ", " : "") +
                (reviewPhoto != null ? "reviewPhoto=" + reviewPhoto + ", " : "") +
                (customerReviewedOn != null ? "customerReviewedOn=" + customerReviewedOn + ", " : "") +
                (supplierResponseOn != null ? "supplierResponseOn=" + supplierResponseOn + ", " : "") +
                (likeCount != null ? "likeCount=" + likeCount + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (packageTypeId != null ? "packageTypeId=" + packageTypeId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (orderPackageId != null ? "orderPackageId=" + orderPackageId + ", " : "") +
            "}";
    }

}
