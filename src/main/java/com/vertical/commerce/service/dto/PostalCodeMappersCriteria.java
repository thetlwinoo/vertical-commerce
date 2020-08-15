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
 * Criteria class for the {@link com.vertical.commerce.domain.PostalCodeMappers} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.PostalCodeMappersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /postal-code-mappers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostalCodeMappersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter postalCode;

    private StringFilter description;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter townshipId;

    public PostalCodeMappersCriteria() {
    }

    public PostalCodeMappersCriteria(PostalCodeMappersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.townshipId = other.townshipId == null ? null : other.townshipId.copy();
    }

    @Override
    public PostalCodeMappersCriteria copy() {
        return new PostalCodeMappersCriteria(this);
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

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(LongFilter townshipId) {
        this.townshipId = townshipId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostalCodeMappersCriteria that = (PostalCodeMappersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(description, that.description) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(townshipId, that.townshipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        postalCode,
        description,
        validFrom,
        validTo,
        townshipId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostalCodeMappersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (townshipId != null ? "townshipId=" + townshipId + ", " : "") +
            "}";
    }

}
