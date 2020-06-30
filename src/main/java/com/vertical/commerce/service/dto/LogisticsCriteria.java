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
 * Criteria class for the {@link com.vertical.commerce.domain.Logistics} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.LogisticsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /logistics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LogisticsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter activeInd;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    public LogisticsCriteria() {
    }

    public LogisticsCriteria(LogisticsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
    }

    @Override
    public LogisticsCriteria copy() {
        return new LogisticsCriteria(this);
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

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LogisticsCriteria that = (LogisticsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        activeInd,
        lastEditedBy,
        lastEditedWhen
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogisticsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
            "}";
    }

}
