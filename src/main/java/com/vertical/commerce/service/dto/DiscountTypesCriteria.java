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
 * Criteria class for the {@link com.vertical.commerce.domain.DiscountTypes} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.DiscountTypesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /discount-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DiscountTypesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter modifiedDate;

    public DiscountTypesCriteria() {
    }

    public DiscountTypesCriteria(DiscountTypesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
    }

    @Override
    public DiscountTypesCriteria copy() {
        return new DiscountTypesCriteria(this);
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

    public InstantFilter getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(InstantFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DiscountTypesCriteria that = (DiscountTypesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        modifiedDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountTypesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
            "}";
    }

}
