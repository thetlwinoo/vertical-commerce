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

    private StringFilter name;

    private BigDecimalFilter amount;

    private BooleanFilter isPercentage;

    private BooleanFilter isAllowCombinationDiscount;

    private BooleanFilter isFinalBillDiscount;

    private IntegerFilter startCount;

    private IntegerFilter endCount;

    private IntegerFilter multiplyCount;

    private BigDecimalFilter minAmount;

    private BigDecimalFilter maxAmount;

    private IntegerFilter minVolumeWeight;

    private IntegerFilter maxVolumeWeight;

    private InstantFilter modifiedDate;

    private LongFilter discountId;

    private LongFilter stockItemId;

    private LongFilter productCategoryId;

    public DiscountDetailsCriteria() {
    }

    public DiscountDetailsCriteria(DiscountDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.isPercentage = other.isPercentage == null ? null : other.isPercentage.copy();
        this.isAllowCombinationDiscount = other.isAllowCombinationDiscount == null ? null : other.isAllowCombinationDiscount.copy();
        this.isFinalBillDiscount = other.isFinalBillDiscount == null ? null : other.isFinalBillDiscount.copy();
        this.startCount = other.startCount == null ? null : other.startCount.copy();
        this.endCount = other.endCount == null ? null : other.endCount.copy();
        this.multiplyCount = other.multiplyCount == null ? null : other.multiplyCount.copy();
        this.minAmount = other.minAmount == null ? null : other.minAmount.copy();
        this.maxAmount = other.maxAmount == null ? null : other.maxAmount.copy();
        this.minVolumeWeight = other.minVolumeWeight == null ? null : other.minVolumeWeight.copy();
        this.maxVolumeWeight = other.maxVolumeWeight == null ? null : other.maxVolumeWeight.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.discountId = other.discountId == null ? null : other.discountId.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public BigDecimalFilter getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimalFilter minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimalFilter getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimalFilter maxAmount) {
        this.maxAmount = maxAmount;
    }

    public IntegerFilter getMinVolumeWeight() {
        return minVolumeWeight;
    }

    public void setMinVolumeWeight(IntegerFilter minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
    }

    public IntegerFilter getMaxVolumeWeight() {
        return maxVolumeWeight;
    }

    public void setMaxVolumeWeight(IntegerFilter maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
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

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
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
            Objects.equals(name, that.name) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(isPercentage, that.isPercentage) &&
            Objects.equals(isAllowCombinationDiscount, that.isAllowCombinationDiscount) &&
            Objects.equals(isFinalBillDiscount, that.isFinalBillDiscount) &&
            Objects.equals(startCount, that.startCount) &&
            Objects.equals(endCount, that.endCount) &&
            Objects.equals(multiplyCount, that.multiplyCount) &&
            Objects.equals(minAmount, that.minAmount) &&
            Objects.equals(maxAmount, that.maxAmount) &&
            Objects.equals(minVolumeWeight, that.minVolumeWeight) &&
            Objects.equals(maxVolumeWeight, that.maxVolumeWeight) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(discountId, that.discountId) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        amount,
        isPercentage,
        isAllowCombinationDiscount,
        isFinalBillDiscount,
        startCount,
        endCount,
        multiplyCount,
        minAmount,
        maxAmount,
        minVolumeWeight,
        maxVolumeWeight,
        modifiedDate,
        discountId,
        stockItemId,
        productCategoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (isPercentage != null ? "isPercentage=" + isPercentage + ", " : "") +
                (isAllowCombinationDiscount != null ? "isAllowCombinationDiscount=" + isAllowCombinationDiscount + ", " : "") +
                (isFinalBillDiscount != null ? "isFinalBillDiscount=" + isFinalBillDiscount + ", " : "") +
                (startCount != null ? "startCount=" + startCount + ", " : "") +
                (endCount != null ? "endCount=" + endCount + ", " : "") +
                (multiplyCount != null ? "multiplyCount=" + multiplyCount + ", " : "") +
                (minAmount != null ? "minAmount=" + minAmount + ", " : "") +
                (maxAmount != null ? "maxAmount=" + maxAmount + ", " : "") +
                (minVolumeWeight != null ? "minVolumeWeight=" + minVolumeWeight + ", " : "") +
                (maxVolumeWeight != null ? "maxVolumeWeight=" + maxVolumeWeight + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (discountId != null ? "discountId=" + discountId + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
