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
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerTransactions} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerTransactionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter transactionDate;

    private BigDecimalFilter amountExcludingTax;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter transactionAmount;

    private BigDecimalFilter outstandingBalance;

    private InstantFilter finalizationDate;

    private BooleanFilter isFinalized;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter customerId;

    private LongFilter paymentMethodId;

    private LongFilter transactionTypeId;

    private LongFilter invoiceId;

    private LongFilter orderId;

    private LongFilter customerPaymentId;

    public CustomerTransactionsCriteria() {
    }

    public CustomerTransactionsCriteria(CustomerTransactionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.amountExcludingTax = other.amountExcludingTax == null ? null : other.amountExcludingTax.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.outstandingBalance = other.outstandingBalance == null ? null : other.outstandingBalance.copy();
        this.finalizationDate = other.finalizationDate == null ? null : other.finalizationDate.copy();
        this.isFinalized = other.isFinalized == null ? null : other.isFinalized.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.paymentMethodId = other.paymentMethodId == null ? null : other.paymentMethodId.copy();
        this.transactionTypeId = other.transactionTypeId == null ? null : other.transactionTypeId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.customerPaymentId = other.customerPaymentId == null ? null : other.customerPaymentId.copy();
    }

    @Override
    public CustomerTransactionsCriteria copy() {
        return new CustomerTransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(InstantFilter transactionDate) {
        this.transactionDate = transactionDate;
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

    public BigDecimalFilter getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimalFilter outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public InstantFilter getFinalizationDate() {
        return finalizationDate;
    }

    public void setFinalizationDate(InstantFilter finalizationDate) {
        this.finalizationDate = finalizationDate;
    }

    public BooleanFilter getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(BooleanFilter isFinalized) {
        this.isFinalized = isFinalized;
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

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(LongFilter paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public LongFilter getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(LongFilter transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getCustomerPaymentId() {
        return customerPaymentId;
    }

    public void setCustomerPaymentId(LongFilter customerPaymentId) {
        this.customerPaymentId = customerPaymentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerTransactionsCriteria that = (CustomerTransactionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(amountExcludingTax, that.amountExcludingTax) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(outstandingBalance, that.outstandingBalance) &&
            Objects.equals(finalizationDate, that.finalizationDate) &&
            Objects.equals(isFinalized, that.isFinalized) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(paymentMethodId, that.paymentMethodId) &&
            Objects.equals(transactionTypeId, that.transactionTypeId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(customerPaymentId, that.customerPaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionDate,
        amountExcludingTax,
        taxAmount,
        transactionAmount,
        outstandingBalance,
        finalizationDate,
        isFinalized,
        lastEditedBy,
        lastEditedWhen,
        customerId,
        paymentMethodId,
        transactionTypeId,
        invoiceId,
        orderId,
        customerPaymentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerTransactionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (amountExcludingTax != null ? "amountExcludingTax=" + amountExcludingTax + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (outstandingBalance != null ? "outstandingBalance=" + outstandingBalance + ", " : "") +
                (finalizationDate != null ? "finalizationDate=" + finalizationDate + ", " : "") +
                (isFinalized != null ? "isFinalized=" + isFinalized + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (paymentMethodId != null ? "paymentMethodId=" + paymentMethodId + ", " : "") +
                (transactionTypeId != null ? "transactionTypeId=" + transactionTypeId + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (customerPaymentId != null ? "customerPaymentId=" + customerPaymentId + ", " : "") +
            "}";
    }

}
