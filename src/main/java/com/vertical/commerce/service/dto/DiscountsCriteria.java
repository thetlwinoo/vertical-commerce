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
 * Criteria class for the {@link com.vertical.commerce.domain.Discounts} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.DiscountsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /discounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DiscountsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter discountCode;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter supplierId;

    private LongFilter discountTypeId;

    public DiscountsCriteria() {
    }

    public DiscountsCriteria(DiscountsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.discountCode = other.discountCode == null ? null : other.discountCode.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.discountTypeId = other.discountTypeId == null ? null : other.discountTypeId.copy();
    }

    @Override
    public DiscountsCriteria copy() {
        return new DiscountsCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(StringFilter discountCode) {
        this.discountCode = discountCode;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(LongFilter discountTypeId) {
        this.discountTypeId = discountTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DiscountsCriteria that = (DiscountsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(discountCode, that.discountCode) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(discountTypeId, that.discountTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        discountCode,
        validFrom,
        validTo,
        supplierId,
        discountTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (discountCode != null ? "discountCode=" + discountCode + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (discountTypeId != null ? "discountTypeId=" + discountTypeId + ", " : "") +
            "}";
    }

}
