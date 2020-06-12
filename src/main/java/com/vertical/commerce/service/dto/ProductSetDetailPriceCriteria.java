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
 * Criteria class for the {@link com.vertical.commerce.domain.ProductSetDetailPrice} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ProductSetDetailPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-set-detail-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductSetDetailPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter price;

    private IntegerFilter startCount;

    private IntegerFilter endCount;

    private IntegerFilter multiplyCount;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private InstantFilter modifiedDate;

    private LongFilter productSetDetailId;

    public ProductSetDetailPriceCriteria() {
    }

    public ProductSetDetailPriceCriteria(ProductSetDetailPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.startCount = other.startCount == null ? null : other.startCount.copy();
        this.endCount = other.endCount == null ? null : other.endCount.copy();
        this.multiplyCount = other.multiplyCount == null ? null : other.multiplyCount.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.productSetDetailId = other.productSetDetailId == null ? null : other.productSetDetailId.copy();
    }

    @Override
    public ProductSetDetailPriceCriteria copy() {
        return new ProductSetDetailPriceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public IntegerFilter getStartCount() {
        return startCount;
    }

    public void setStartCount(IntegerFilter startCount) {
        this.startCount = startCount;
    }

    public IntegerFilter getEndCount() {
        return endCount;
    }

    public void setEndCount(IntegerFilter endCount) {
        this.endCount = endCount;
    }

    public IntegerFilter getMultiplyCount() {
        return multiplyCount;
    }

    public void setMultiplyCount(IntegerFilter multiplyCount) {
        this.multiplyCount = multiplyCount;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public InstantFilter getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(InstantFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LongFilter getProductSetDetailId() {
        return productSetDetailId;
    }

    public void setProductSetDetailId(LongFilter productSetDetailId) {
        this.productSetDetailId = productSetDetailId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductSetDetailPriceCriteria that = (ProductSetDetailPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(startCount, that.startCount) &&
            Objects.equals(endCount, that.endCount) &&
            Objects.equals(multiplyCount, that.multiplyCount) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(productSetDetailId, that.productSetDetailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        startCount,
        endCount,
        multiplyCount,
        startDate,
        endDate,
        modifiedDate,
        productSetDetailId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSetDetailPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (startCount != null ? "startCount=" + startCount + ", " : "") +
                (endCount != null ? "endCount=" + endCount + ", " : "") +
                (multiplyCount != null ? "multiplyCount=" + multiplyCount + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (productSetDetailId != null ? "productSetDetailId=" + productSetDetailId + ", " : "") +
            "}";
    }

}
