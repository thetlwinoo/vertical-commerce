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
 * Criteria class for the {@link com.vertical.commerce.domain.Tax} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.TaxResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /taxes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaxCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private DoubleFilter rate;

    private InstantFilter modifiedDate;

    private LongFilter taxClassId;

    public TaxCriteria() {
    }

    public TaxCriteria(TaxCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.taxClassId = other.taxClassId == null ? null : other.taxClassId.copy();
    }

    @Override
    public TaxCriteria copy() {
        return new TaxCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public DoubleFilter getRate() {
        return rate;
    }

    public void setRate(DoubleFilter rate) {
        this.rate = rate;
    }

    public InstantFilter getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(InstantFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LongFilter getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(LongFilter taxClassId) {
        this.taxClassId = taxClassId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaxCriteria that = (TaxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(taxClassId, that.taxClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        rate,
        modifiedDate,
        taxClassId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (taxClassId != null ? "taxClassId=" + taxClassId + ", " : "") +
            "}";
    }

}
