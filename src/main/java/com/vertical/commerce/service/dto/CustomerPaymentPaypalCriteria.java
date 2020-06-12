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
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerPaymentPaypal} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerPaymentPaypalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-payment-paypals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerPaymentPaypalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paypalAccount;

    private BigDecimalFilter amount;

    private StringFilter responseCode;

    private StringFilter approvalCode;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter customerPaymentId;

    private LongFilter currencyId;

    public CustomerPaymentPaypalCriteria() {
    }

    public CustomerPaymentPaypalCriteria(CustomerPaymentPaypalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paypalAccount = other.paypalAccount == null ? null : other.paypalAccount.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.responseCode = other.responseCode == null ? null : other.responseCode.copy();
        this.approvalCode = other.approvalCode == null ? null : other.approvalCode.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.customerPaymentId = other.customerPaymentId == null ? null : other.customerPaymentId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public CustomerPaymentPaypalCriteria copy() {
        return new CustomerPaymentPaypalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPaypalAccount() {
        return paypalAccount;
    }

    public void setPaypalAccount(StringFilter paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(StringFilter responseCode) {
        this.responseCode = responseCode;
    }

    public StringFilter getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(StringFilter approvalCode) {
        this.approvalCode = approvalCode;
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
        final CustomerPaymentPaypalCriteria that = (CustomerPaymentPaypalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paypalAccount, that.paypalAccount) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(responseCode, that.responseCode) &&
            Objects.equals(approvalCode, that.approvalCode) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(customerPaymentId, that.customerPaymentId) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paypalAccount,
        amount,
        responseCode,
        approvalCode,
        lastEditedBy,
        lastEditedWhen,
        customerPaymentId,
        currencyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentPaypalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paypalAccount != null ? "paypalAccount=" + paypalAccount + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (responseCode != null ? "responseCode=" + responseCode + ", " : "") +
                (approvalCode != null ? "approvalCode=" + approvalCode + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (customerPaymentId != null ? "customerPaymentId=" + customerPaymentId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
