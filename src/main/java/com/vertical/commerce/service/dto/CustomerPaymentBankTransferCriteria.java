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
 * Criteria class for the {@link com.vertical.commerce.domain.CustomerPaymentBankTransfer} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomerPaymentBankTransferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-payment-bank-transfers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerPaymentBankTransferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter receiptPhoto;

    private StringFilter nameInBankAccount;

    private InstantFilter dateOfTransfer;

    private BigDecimalFilter amountTransferred;

    private StringFilter bankName;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter customerPaymentId;

    private LongFilter currencyId;

    public CustomerPaymentBankTransferCriteria() {
    }

    public CustomerPaymentBankTransferCriteria(CustomerPaymentBankTransferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.receiptPhoto = other.receiptPhoto == null ? null : other.receiptPhoto.copy();
        this.nameInBankAccount = other.nameInBankAccount == null ? null : other.nameInBankAccount.copy();
        this.dateOfTransfer = other.dateOfTransfer == null ? null : other.dateOfTransfer.copy();
        this.amountTransferred = other.amountTransferred == null ? null : other.amountTransferred.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.customerPaymentId = other.customerPaymentId == null ? null : other.customerPaymentId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public CustomerPaymentBankTransferCriteria copy() {
        return new CustomerPaymentBankTransferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReceiptPhoto() {
        return receiptPhoto;
    }

    public void setReceiptPhoto(StringFilter receiptPhoto) {
        this.receiptPhoto = receiptPhoto;
    }

    public StringFilter getNameInBankAccount() {
        return nameInBankAccount;
    }

    public void setNameInBankAccount(StringFilter nameInBankAccount) {
        this.nameInBankAccount = nameInBankAccount;
    }

    public InstantFilter getDateOfTransfer() {
        return dateOfTransfer;
    }

    public void setDateOfTransfer(InstantFilter dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    public BigDecimalFilter getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(BigDecimalFilter amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
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
        final CustomerPaymentBankTransferCriteria that = (CustomerPaymentBankTransferCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(receiptPhoto, that.receiptPhoto) &&
            Objects.equals(nameInBankAccount, that.nameInBankAccount) &&
            Objects.equals(dateOfTransfer, that.dateOfTransfer) &&
            Objects.equals(amountTransferred, that.amountTransferred) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(customerPaymentId, that.customerPaymentId) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        receiptPhoto,
        nameInBankAccount,
        dateOfTransfer,
        amountTransferred,
        bankName,
        lastEditedBy,
        lastEditedWhen,
        customerPaymentId,
        currencyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentBankTransferCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (receiptPhoto != null ? "receiptPhoto=" + receiptPhoto + ", " : "") +
                (nameInBankAccount != null ? "nameInBankAccount=" + nameInBankAccount + ", " : "") +
                (dateOfTransfer != null ? "dateOfTransfer=" + dateOfTransfer + ", " : "") +
                (amountTransferred != null ? "amountTransferred=" + amountTransferred + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (customerPaymentId != null ? "customerPaymentId=" + customerPaymentId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
