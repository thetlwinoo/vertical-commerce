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
 * Criteria class for the {@link com.vertical.commerce.domain.Compares} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ComparesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compares?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComparesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter compareUserId;

    private LongFilter compareLineListId;

    public ComparesCriteria() {
    }

    public ComparesCriteria(ComparesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.compareUserId = other.compareUserId == null ? null : other.compareUserId.copy();
        this.compareLineListId = other.compareLineListId == null ? null : other.compareLineListId.copy();
    }

    @Override
    public ComparesCriteria copy() {
        return new ComparesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCompareUserId() {
        return compareUserId;
    }

    public void setCompareUserId(LongFilter compareUserId) {
        this.compareUserId = compareUserId;
    }

    public LongFilter getCompareLineListId() {
        return compareLineListId;
    }

    public void setCompareLineListId(LongFilter compareLineListId) {
        this.compareLineListId = compareLineListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ComparesCriteria that = (ComparesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(compareUserId, that.compareUserId) &&
            Objects.equals(compareLineListId, that.compareLineListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        compareUserId,
        compareLineListId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComparesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (compareUserId != null ? "compareUserId=" + compareUserId + ", " : "") +
                (compareLineListId != null ? "compareLineListId=" + compareLineListId + ", " : "") +
            "}";
    }

}
