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
 * Criteria class for the {@link com.vertical.commerce.domain.CompareLines} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CompareLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compare-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompareLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter stockItemId;

    private LongFilter compareId;

    public CompareLinesCriteria() {
    }

    public CompareLinesCriteria(CompareLinesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.compareId = other.compareId == null ? null : other.compareId.copy();
    }

    @Override
    public CompareLinesCriteria copy() {
        return new CompareLinesCriteria(this);
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

    public LongFilter getCompareId() {
        return compareId;
    }

    public void setCompareId(LongFilter compareId) {
        this.compareId = compareId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompareLinesCriteria that = (CompareLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(compareId, that.compareId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockItemId,
        compareId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompareLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (compareId != null ? "compareId=" + compareId + ", " : "") +
            "}";
    }

}
