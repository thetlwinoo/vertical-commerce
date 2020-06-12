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
 * Criteria class for the {@link com.vertical.commerce.domain.DiscountDetails} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.DiscountDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /discount-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DiscountDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter amount;

    private BooleanFilter isPercentage;

    private BooleanFilter isAllowCombinationDiscount;

    private BooleanFilter isFinalBillDiscount;

    private StringFilter name;

    private IntegerFilter startCount;

    private IntegerFilter endCount;

    private IntegerFilter multiplyCount;

    private InstantFilter modifiedDate;

    private LongFilter discountId;

    private LongFilter productId;

    private LongFilter productCategoryId;

    public DiscountDetailsCriteria() {
    }

    public DiscountDetailsCriteria(DiscountDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.isPercentage = other.isPercentage == null ? null : other.isPercentage.copy();
        this.isAllowCombinationDiscount = other.isAllowCombinationDiscount == null ? null : other.isAllowCombinationDiscount.copy();
        this.isFinalBillDiscount = other.isFinalBillDiscount == null ? null : other.isFinalBillDiscount.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.startCount = other.startCount == null ? null : other.startCount.copy();
        this.endCount = other.endCount == null ? null : other.endCount.copy();
        this.multiplyCount = other.multiplyCount == null ? null : other.multiplyCount.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.discountId = other.discountId == null ? null : other.discountId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
    }

    @Override
    public DiscountDetailsCriteria copy() {
        return new DiscountDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BooleanFilter getIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(BooleanFilter isPercentage) {
        this.isPercentage = isPercentage;
    }

    public BooleanFilter getIsAllowCombinationDiscount() {
        return isAllowCombinationDiscount;
    }

    public void setIsAllowCombinationDiscount(BooleanFilter isAllowCombinationDiscount) {
        this.isAllowCombinationDiscount = isAllowCombinationDiscount;
    }

    public BooleanFilter getIsFinalBillDiscount() {
        return isFinalBillDiscount;
    }

    public void setIsFinalBillDiscount(BooleanFilter isFinalBillDiscount) {
        this.isFinalBillDiscount = isFinalBillDiscount;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public InstantFilter getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(InstantFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LongFilter getDiscountId() {
        return discountId;
    }

    public void setDiscountId(LongFilter discountId) {
        this.discountId = discountId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DiscountDetailsCriteria that = (DiscountDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(isPercentage, that.isPercentage) &&
            Objects.equals(isAllowCombinationDiscount, that.isAllowCombinationDiscount) &&
            Objects.equals(isFinalBillDiscount, that.isFinalBillDiscount) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startCount, that.startCount) &&
            Objects.equals(endCount, that.endCount) &&
            Objects.equals(multiplyCount, that.multiplyCount) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(discountId, that.discountId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        isPercentage,
        isAllowCombinationDiscount,
        isFinalBillDiscount,
        name,
        startCount,
        endCount,
        multiplyCount,
        modifiedDate,
        discountId,
        productId,
        productCategoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (isPercentage != null ? "isPercentage=" + isPercentage + ", " : "") +
                (isAllowCombinationDiscount != null ? "isAllowCombinationDiscount=" + isAllowCombinationDiscount + ", " : "") +
                (isFinalBillDiscount != null ? "isFinalBillDiscount=" + isFinalBillDiscount + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startCount != null ? "startCount=" + startCount + ", " : "") +
                (endCount != null ? "endCount=" + endCount + ", " : "") +
                (multiplyCount != null ? "multiplyCount=" + multiplyCount + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (discountId != null ? "discountId=" + discountId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
