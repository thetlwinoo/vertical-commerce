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
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerPaymentCreditCard} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerPaymentCreditCardResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-payment-credit-cards?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerPaymentCreditCardCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter creditCardNumber;

    private IntegerFilter creditCardExpiryMonth;

    private IntegerFilter creditCardExpiryYear;

    private BigDecimalFilter amount;

    private StringFilter batchId;

    private StringFilter responseCode;

    private StringFilter approvalCode;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter customerPaymentId;

    private LongFilter currencyId;

    public CustomerPaymentCreditCardCriteria() {
    }

    public CustomerPaymentCreditCardCriteria(CustomerPaymentCreditCardCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.creditCardNumber = other.creditCardNumber == null ? null : other.creditCardNumber.copy();
        this.creditCardExpiryMonth = other.creditCardExpiryMonth == null ? null : other.creditCardExpiryMonth.copy();
        this.creditCardExpiryYear = other.creditCardExpiryYear == null ? null : other.creditCardExpiryYear.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.responseCode = other.responseCode == null ? null : other.responseCode.copy();
        this.approvalCode = other.approvalCode == null ? null : other.approvalCode.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.customerPaymentId = other.customerPaymentId == null ? null : other.customerPaymentId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public CustomerPaymentCreditCardCriteria copy() {
        return new CustomerPaymentCreditCardCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(StringFilter creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public IntegerFilter getCreditCardExpiryMonth() {
        return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth(IntegerFilter creditCardExpiryMonth) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public IntegerFilter getCreditCardExpiryYear() {
        return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear(IntegerFilter creditCardExpiryYear) {
        this.creditCardExpiryYear = creditCardExpiryYear;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getBatchId() {
        return batchId;
    }

    public void setBatchId(StringFilter batchId) {
        this.batchId = batchId;
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
        final CustomerPaymentCreditCardCriteria that = (CustomerPaymentCreditCardCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creditCardNumber, that.creditCardNumber) &&
            Objects.equals(creditCardExpiryMonth, that.creditCardExpiryMonth) &&
            Objects.equals(creditCardExpiryYear, that.creditCardExpiryYear) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(batchId, that.batchId) &&
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
        creditCardNumber,
        creditCardExpiryMonth,
        creditCardExpiryYear,
        amount,
        batchId,
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
        return "CustomerPaymentCreditCardCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creditCardNumber != null ? "creditCardNumber=" + creditCardNumber + ", " : "") +
                (creditCardExpiryMonth != null ? "creditCardExpiryMonth=" + creditCardExpiryMonth + ", " : "") +
                (creditCardExpiryYear != null ? "creditCardExpiryYear=" + creditCardExpiryYear + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (batchId != null ? "batchId=" + batchId + ", " : "") +
                (responseCode != null ? "responseCode=" + responseCode + ", " : "") +
                (approvalCode != null ? "approvalCode=" + approvalCode + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (customerPaymentId != null ? "customerPaymentId=" + customerPaymentId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
