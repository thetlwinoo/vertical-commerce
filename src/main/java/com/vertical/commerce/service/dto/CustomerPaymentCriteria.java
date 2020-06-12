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
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerPayment} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerPaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerPaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter amountExcludingTax;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter transactionAmount;

    private BigDecimalFilter outstandingAmount;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter customerTransactionId;

    private LongFilter paymentMethodId;

    private LongFilter currencyId;

    private LongFilter currencyRateId;

    private LongFilter customerPaymentCreditCardId;

    private LongFilter customerPaymentVoucherId;

    private LongFilter customerPaymentBankTransferId;

    private LongFilter customerPaymentPaypalId;

    public CustomerPaymentCriteria() {
    }

    public CustomerPaymentCriteria(CustomerPaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amountExcludingTax = other.amountExcludingTax == null ? null : other.amountExcludingTax.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.outstandingAmount = other.outstandingAmount == null ? null : other.outstandingAmount.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.customerTransactionId = other.customerTransactionId == null ? null : other.customerTransactionId.copy();
        this.paymentMethodId = other.paymentMethodId == null ? null : other.paymentMethodId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.currencyRateId = other.currencyRateId == null ? null : other.currencyRateId.copy();
        this.customerPaymentCreditCardId = other.customerPaymentCreditCardId == null ? null : other.customerPaymentCreditCardId.copy();
        this.customerPaymentVoucherId = other.customerPaymentVoucherId == null ? null : other.customerPaymentVoucherId.copy();
        this.customerPaymentBankTransferId = other.customerPaymentBankTransferId == null ? null : other.customerPaymentBankTransferId.copy();
        this.customerPaymentPaypalId = other.customerPaymentPaypalId == null ? null : other.customerPaymentPaypalId.copy();
    }

    @Override
    public CustomerPaymentCriteria copy() {
        return new CustomerPaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimalFilter amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimalFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimalFilter getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimalFilter outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
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

    public LongFilter getCustomerTransactionId() {
        return customerTransactionId;
    }

    public void setCustomerTransactionId(LongFilter customerTransactionId) {
        this.customerTransactionId = customerTransactionId;
    }

    public LongFilter getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(LongFilter paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public LongFilter getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(LongFilter currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public LongFilter getCustomerPaymentCreditCardId() {
        return customerPaymentCreditCardId;
    }

    public void setCustomerPaymentCreditCardId(LongFilter customerPaymentCreditCardId) {
        this.customerPaymentCreditCardId = customerPaymentCreditCardId;
    }

    public LongFilter getCustomerPaymentVoucherId() {
        return customerPaymentVoucherId;
    }

    public void setCustomerPaymentVoucherId(LongFilter customerPaymentVoucherId) {
        this.customerPaymentVoucherId = customerPaymentVoucherId;
    }

    public LongFilter getCustomerPaymentBankTransferId() {
        return customerPaymentBankTransferId;
    }

    public void setCustomerPaymentBankTransferId(LongFilter customerPaymentBankTransferId) {
        this.customerPaymentBankTransferId = customerPaymentBankTransferId;
    }

    public LongFilter getCustomerPaymentPaypalId() {
        return customerPaymentPaypalId;
    }

    public void setCustomerPaymentPaypalId(LongFilter customerPaymentPaypalId) {
        this.customerPaymentPaypalId = customerPaymentPaypalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerPaymentCriteria that = (CustomerPaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amountExcludingTax, that.amountExcludingTax) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(outstandingAmount, that.outstandingAmount) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(customerTransactionId, that.customerTransactionId) &&
            Objects.equals(paymentMethodId, that.paymentMethodId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(currencyRateId, that.currencyRateId) &&
            Objects.equals(customerPaymentCreditCardId, that.customerPaymentCreditCardId) &&
            Objects.equals(customerPaymentVoucherId, that.customerPaymentVoucherId) &&
            Objects.equals(customerPaymentBankTransferId, that.customerPaymentBankTransferId) &&
            Objects.equals(customerPaymentPaypalId, that.customerPaymentPaypalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amountExcludingTax,
        taxAmount,
        transactionAmount,
        outstandingAmount,
        lastEditedBy,
        lastEditedWhen,
        customerTransactionId,
        paymentMethodId,
        currencyId,
        currencyRateId,
        customerPaymentCreditCardId,
        customerPaymentVoucherId,
        customerPaymentBankTransferId,
        customerPaymentPaypalId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amountExcludingTax != null ? "amountExcludingTax=" + amountExcludingTax + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (outstandingAmount != null ? "outstandingAmount=" + outstandingAmount + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (customerTransactionId != null ? "customerTransactionId=" + customerTransactionId + ", " : "") +
                (paymentMethodId != null ? "paymentMethodId=" + paymentMethodId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (currencyRateId != null ? "currencyRateId=" + currencyRateId + ", " : "") +
                (customerPaymentCreditCardId != null ? "customerPaymentCreditCardId=" + customerPaymentCreditCardId + ", " : "") +
                (customerPaymentVoucherId != null ? "customerPaymentVoucherId=" + customerPaymentVoucherId + ", " : "") +
                (customerPaymentBankTransferId != null ? "customerPaymentBankTransferId=" + customerPaymentBankTransferId + ", " : "") +
                (customerPaymentPaypalId != null ? "customerPaymentPaypalId=" + customerPaymentPaypalId + ", " : "") +
            "}";
    }

}
