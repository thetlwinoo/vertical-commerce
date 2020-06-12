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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerPaymentVoucher} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerPaymentVoucherResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-payment-vouchers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerPaymentVoucherCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serialNo;

    private BigDecimalFilter amount;

    private StringFilter lastEdityBy;

    private InstantFilter lastEditedWhen;

    private LongFilter customerPaymentId;

    private LongFilter currencyId;

    public CustomerPaymentVoucherCriteria() {
    }

    public CustomerPaymentVoucherCriteria(CustomerPaymentVoucherCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serialNo = other.serialNo == null ? null : other.serialNo.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.lastEdityBy = other.lastEdityBy == null ? null : other.lastEdityBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.customerPaymentId = other.customerPaymentId == null ? null : other.customerPaymentId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public CustomerPaymentVoucherCriteria copy() {
        return new CustomerPaymentVoucherCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(StringFilter serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getLastEdityBy() {
        return lastEdityBy;
    }

    public void setLastEdityBy(StringFilter lastEdityBy) {
        this.lastEdityBy = lastEdityBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getCustomerPaymentId() {
        return customerPaymentId;
    }

    public void setCustomerPaymentId(LongFilter customerPaymentId) {
        this.customerPaymentId = customerPaymentId;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerPaymentVoucherCriteria that = (CustomerPaymentVoucherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(lastEdityBy, that.lastEdityBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(customerPaymentId, that.customerPaymentId) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serialNo,
        amount,
        lastEdityBy,
        lastEditedWhen,
        customerPaymentId,
        currencyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentVoucherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (lastEdityBy != null ? "lastEdityBy=" + lastEdityBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (customerPaymentId != null ? "customerPaymentId=" + customerPaymentId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
