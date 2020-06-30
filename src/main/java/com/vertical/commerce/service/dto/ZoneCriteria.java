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
 * Criteria class for the {@link com.vertical.commerce.domain.Zone} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ZoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter cityId;

    public ZoneCriteria() {
    }

    public ZoneCriteria(ZoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
    }

    @Override
    public ZoneCriteria copy() {
        return new ZoneCriteria(this);
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

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ZoneCriteria that = (ZoneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(cityId, that.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        validFrom,
        validTo,
        cityId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
            "}";
    }

}
