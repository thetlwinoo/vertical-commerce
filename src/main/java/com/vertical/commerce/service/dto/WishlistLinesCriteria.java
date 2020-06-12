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

/**
 * Criteria class for the {@link com.vertical.commerce.domain.WishlistLines} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.WishlistLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wishlist-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WishlistLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter stockItemId;

    private LongFilter wishlistId;

    public WishlistLinesCriteria() {
    }

    public WishlistLinesCriteria(WishlistLinesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.wishlistId = other.wishlistId == null ? null : other.wishlistId.copy();
    }

    @Override
    public WishlistLinesCriteria copy() {
        return new WishlistLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(LongFilter wishlistId) {
        this.wishlistId = wishlistId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WishlistLinesCriteria that = (WishlistLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(wishlistId, that.wishlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockItemId,
        wishlistId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WishlistLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (wishlistId != null ? "wishlistId=" + wishlistId + ", " : "") +
            "}";
    }

}
