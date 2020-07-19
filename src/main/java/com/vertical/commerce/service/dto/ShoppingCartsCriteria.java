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
 * Criteria class for the {@link com.vertical.commerce.domain.ShoppingCarts} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ShoppingCartsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /shopping-carts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShoppingCartsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter totalPrice;

    private BigDecimalFilter totalTaxAmount;

    private BigDecimalFilter subTotalPrice;

    private BigDecimalFilter totalShippingFee;

    private BigDecimalFilter totalShippingFeeDiscount;

    private BigDecimalFilter promotionTotal;

    private BigDecimalFilter voucherTotal;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter cartUserId;

    private LongFilter cartItemListId;

    private LongFilter customerId;

    private LongFilter specialDealsId;

    public ShoppingCartsCriteria() {
    }

    public ShoppingCartsCriteria(ShoppingCartsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.totalTaxAmount = other.totalTaxAmount == null ? null : other.totalTaxAmount.copy();
        this.subTotalPrice = other.subTotalPrice == null ? null : other.subTotalPrice.copy();
        this.totalShippingFee = other.totalShippingFee == null ? null : other.totalShippingFee.copy();
        this.totalShippingFeeDiscount = other.totalShippingFeeDiscount == null ? null : other.totalShippingFeeDiscount.copy();
        this.promotionTotal = other.promotionTotal == null ? null : other.promotionTotal.copy();
        this.voucherTotal = other.voucherTotal == null ? null : other.voucherTotal.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.cartUserId = other.cartUserId == null ? null : other.cartUserId.copy();
        this.cartItemListId = other.cartItemListId == null ? null : other.cartItemListId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.specialDealsId = other.specialDealsId == null ? null : other.specialDealsId.copy();
    }

    @Override
    public ShoppingCartsCriteria copy() {
        return new ShoppingCartsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimalFilter getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimalFilter totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public BigDecimalFilter getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimalFilter subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public BigDecimalFilter getTotalShippingFee() {
        return totalShippingFee;
    }

    public void setTotalShippingFee(BigDecimalFilter totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
    }

    public BigDecimalFilter getTotalShippingFeeDiscount() {
        return totalShippingFeeDiscount;
    }

    public void setTotalShippingFeeDiscount(BigDecimalFilter totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
    }

    public BigDecimalFilter getPromotionTotal() {
        return promotionTotal;
    }

    public void setPromotionTotal(BigDecimalFilter promotionTotal) {
        this.promotionTotal = promotionTotal;
    }

    public BigDecimalFilter getVoucherTotal() {
        return voucherTotal;
    }

    public void setVoucherTotal(BigDecimalFilter voucherTotal) {
        this.voucherTotal = voucherTotal;
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

    public LongFilter getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(LongFilter cartUserId) {
        this.cartUserId = cartUserId;
    }

    public LongFilter getCartItemListId() {
        return cartItemListId;
    }

    public void setCartItemListId(LongFilter cartItemListId) {
        this.cartItemListId = cartItemListId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(LongFilter specialDealsId) {
        this.specialDealsId = specialDealsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShoppingCartsCriteria that = (ShoppingCartsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(totalTaxAmount, that.totalTaxAmount) &&
            Objects.equals(subTotalPrice, that.subTotalPrice) &&
            Objects.equals(totalShippingFee, that.totalShippingFee) &&
            Objects.equals(totalShippingFeeDiscount, that.totalShippingFeeDiscount) &&
            Objects.equals(promotionTotal, that.promotionTotal) &&
            Objects.equals(voucherTotal, that.voucherTotal) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(cartUserId, that.cartUserId) &&
            Objects.equals(cartItemListId, that.cartItemListId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(specialDealsId, that.specialDealsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalPrice,
        totalTaxAmount,
        subTotalPrice,
        totalShippingFee,
        totalShippingFeeDiscount,
        promotionTotal,
        voucherTotal,
        lastEditedBy,
        lastEditedWhen,
        cartUserId,
        cartItemListId,
        customerId,
        specialDealsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingCartsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (totalTaxAmount != null ? "totalTaxAmount=" + totalTaxAmount + ", " : "") +
                (subTotalPrice != null ? "subTotalPrice=" + subTotalPrice + ", " : "") +
                (totalShippingFee != null ? "totalShippingFee=" + totalShippingFee + ", " : "") +
                (totalShippingFeeDiscount != null ? "totalShippingFeeDiscount=" + totalShippingFeeDiscount + ", " : "") +
                (promotionTotal != null ? "promotionTotal=" + promotionTotal + ", " : "") +
                (voucherTotal != null ? "voucherTotal=" + voucherTotal + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (cartUserId != null ? "cartUserId=" + cartUserId + ", " : "") +
                (cartItemListId != null ? "cartItemListId=" + cartItemListId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
            "}";
    }

}
