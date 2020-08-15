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
 * Criteria class for the {@link com.vertical.commerce.domain.PostalCodeMappersLocalize} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.PostalCodeMappersLocalizeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /postal-code-mappers-localizes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostalCodeMappersLocalizeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter cultureId;

    private LongFilter postalCodeMapperId;

    public PostalCodeMappersLocalizeCriteria() {
    }

    public PostalCodeMappersLocalizeCriteria(PostalCodeMappersLocalizeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.cultureId = other.cultureId == null ? null : other.cultureId.copy();
        this.postalCodeMapperId = other.postalCodeMapperId == null ? null : other.postalCodeMapperId.copy();
    }

    @Override
    public PostalCodeMappersLocalizeCriteria copy() {
        return new PostalCodeMappersLocalizeCriteria(this);
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

    public LongFilter getCultureId() {
        return cultureId;
    }

    public void setCultureId(LongFilter cultureId) {
        this.cultureId = cultureId;
    }

    public LongFilter getPostalCodeMapperId() {
        return postalCodeMapperId;
    }

    public void setPostalCodeMapperId(LongFilter postalCodeMapperId) {
        this.postalCodeMapperId = postalCodeMapperId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostalCodeMappersLocalizeCriteria that = (PostalCodeMappersLocalizeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(cultureId, that.cultureId) &&
            Objects.equals(postalCodeMapperId, that.postalCodeMapperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        cultureId,
        postalCodeMapperId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostalCodeMappersLocalizeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (cultureId != null ? "cultureId=" + cultureId + ", " : "") +
                (postalCodeMapperId != null ? "postalCodeMapperId=" + postalCodeMapperId + ", " : "") +
            "}";
    }

}
