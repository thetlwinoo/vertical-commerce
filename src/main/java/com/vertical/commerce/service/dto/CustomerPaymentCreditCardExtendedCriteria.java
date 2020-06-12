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
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerPaymentCreditCardExtended} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerPaymentCreditCardExtendedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-payment-credit-card-extendeds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerPaymentCreditCardExtendedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter errorCode;

    private StringFilter errorMessage;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditeWhen;

    public CustomerPaymentCreditCardExtendedCriteria() {
    }

    public CustomerPaymentCreditCardExtendedCriteria(CustomerPaymentCreditCardExtendedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.errorCode = other.errorCode == null ? null : other.errorCode.copy();
        this.errorMessage = other.errorMessage == null ? null : other.errorMessage.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditeWhen = other.lastEditeWhen == null ? null : other.lastEditeWhen.copy();
    }

    @Override
    public CustomerPaymentCreditCardExtendedCriteria copy() {
        return new CustomerPaymentCreditCardExtendedCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(StringFilter errorCode) {
        this.errorCode = errorCode;
    }

    public StringFilter getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(StringFilter errorMessage) {
        this.errorMessage = errorMessage;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditeWhen() {
        return lastEditeWhen;
    }

    public void setLastEditeWhen(InstantFilter lastEditeWhen) {
        this.lastEditeWhen = lastEditeWhen;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerPaymentCreditCardExtendedCriteria that = (CustomerPaymentCreditCardExtendedCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(errorCode, that.errorCode) &&
            Objects.equals(errorMessage, that.errorMessage) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditeWhen, that.lastEditeWhen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        errorCode,
        errorMessage,
        lastEditedBy,
        lastEditeWhen
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentCreditCardExtendedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (errorCode != null ? "errorCode=" + errorCode + ", " : "") +
                (errorMessage != null ? "errorMessage=" + errorMessage + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditeWhen != null ? "lastEditeWhen=" + lastEditeWhen + ", " : "") +
            "}";
    }

}
