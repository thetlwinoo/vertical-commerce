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
 * Criteria class for the {@link com.vertical.commerce.domain.PaymentMethods} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.PaymentMethodsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentMethodsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private BooleanFilter disabled;

    private IntegerFilter sortOrder;

    private StringFilter iconFont;

    private StringFilter iconPhoto;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    public PaymentMethodsCriteria() {
    }

    public PaymentMethodsCriteria(PaymentMethodsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.disabled = other.disabled == null ? null : other.disabled.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.iconFont = other.iconFont == null ? null : other.iconFont.copy();
        this.iconPhoto = other.iconPhoto == null ? null : other.iconPhoto.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public PaymentMethodsCriteria copy() {
        return new PaymentMethodsCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public BooleanFilter getDisabled() {
        return disabled;
    }

    public void setDisabled(BooleanFilter disabled) {
        this.disabled = disabled;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public StringFilter getIconFont() {
        return iconFont;
    }

    public void setIconFont(StringFilter iconFont) {
        this.iconFont = iconFont;
    }

    public StringFilter getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(StringFilter iconPhoto) {
        this.iconPhoto = iconPhoto;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentMethodsCriteria that = (PaymentMethodsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(disabled, that.disabled) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(iconFont, that.iconFont) &&
            Objects.equals(iconPhoto, that.iconPhoto) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        disabled,
        sortOrder,
        iconFont,
        iconPhoto,
        validFrom,
        validTo
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (disabled != null ? "disabled=" + disabled + ", " : "") +
                (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
                (iconFont != null ? "iconFont=" + iconFont + ", " : "") +
                (iconPhoto != null ? "iconPhoto=" + iconPhoto + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}
