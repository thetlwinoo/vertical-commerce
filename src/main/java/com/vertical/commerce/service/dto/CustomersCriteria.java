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
 * Criteria class for the {@link com.vertical.commerce.domain.Customers} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.CustomersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter accountNumber;

    private InstantFilter accountOpenedDate;

    private BigDecimalFilter standardDiscountPercentage;

    private BooleanFilter isStatementSent;

    private BooleanFilter isOnCreditHold;

    private IntegerFilter paymentDays;

    private StringFilter deliveryRun;

    private StringFilter runPosition;

    private StringFilter thumbnailUrl;

    private BooleanFilter billToAddressSameAsDeliveryAddress;

    private StringFilter lastEditedBy;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter peopleId;

    private LongFilter deliveryMethodId;

    private LongFilter deliveryAddressId;

    private LongFilter billToAddressId;

    public CustomersCriteria() {
    }

    public CustomersCriteria(CustomersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.accountOpenedDate = other.accountOpenedDate == null ? null : other.accountOpenedDate.copy();
        this.standardDiscountPercentage = other.standardDiscountPercentage == null ? null : other.standardDiscountPercentage.copy();
        this.isStatementSent = other.isStatementSent == null ? null : other.isStatementSent.copy();
        this.isOnCreditHold = other.isOnCreditHold == null ? null : other.isOnCreditHold.copy();
        this.paymentDays = other.paymentDays == null ? null : other.paymentDays.copy();
        this.deliveryRun = other.deliveryRun == null ? null : other.deliveryRun.copy();
        this.runPosition = other.runPosition == null ? null : other.runPosition.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.billToAddressSameAsDeliveryAddress = other.billToAddressSameAsDeliveryAddress == null ? null : other.billToAddressSameAsDeliveryAddress.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.peopleId = other.peopleId == null ? null : other.peopleId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
        this.deliveryAddressId = other.deliveryAddressId == null ? null : other.deliveryAddressId.copy();
        this.billToAddressId = other.billToAddressId == null ? null : other.billToAddressId.copy();
    }

    @Override
    public CustomersCriteria copy() {
        return new CustomersCriteria(this);
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

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public InstantFilter getAccountOpenedDate() {
        return accountOpenedDate;
    }

    public void setAccountOpenedDate(InstantFilter accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
    }

    public BigDecimalFilter getStandardDiscountPercentage() {
        return standardDiscountPercentage;
    }

    public void setStandardDiscountPercentage(BigDecimalFilter standardDiscountPercentage) {
        this.standardDiscountPercentage = standardDiscountPercentage;
    }

    public BooleanFilter getIsStatementSent() {
        return isStatementSent;
    }

    public void setIsStatementSent(BooleanFilter isStatementSent) {
        this.isStatementSent = isStatementSent;
    }

    public BooleanFilter getIsOnCreditHold() {
        return isOnCreditHold;
    }

    public void setIsOnCreditHold(BooleanFilter isOnCreditHold) {
        this.isOnCreditHold = isOnCreditHold;
    }

    public IntegerFilter getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(IntegerFilter paymentDays) {
        this.paymentDays = paymentDays;
    }

    public StringFilter getDeliveryRun() {
        return deliveryRun;
    }

    public void setDeliveryRun(StringFilter deliveryRun) {
        this.deliveryRun = deliveryRun;
    }

    public StringFilter getRunPosition() {
        return runPosition;
    }

    public void setRunPosition(StringFilter runPosition) {
        this.runPosition = runPosition;
    }

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public BooleanFilter getBillToAddressSameAsDeliveryAddress() {
        return billToAddressSameAsDeliveryAddress;
    }

    public void setBillToAddressSameAsDeliveryAddress(BooleanFilter billToAddressSameAsDeliveryAddress) {
        this.billToAddressSameAsDeliveryAddress = billToAddressSameAsDeliveryAddress;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
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

    public LongFilter getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(LongFilter peopleId) {
        this.peopleId = peopleId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public LongFilter getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(LongFilter deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public LongFilter getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(LongFilter billToAddressId) {
        this.billToAddressId = billToAddressId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomersCriteria that = (CustomersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(accountOpenedDate, that.accountOpenedDate) &&
            Objects.equals(standardDiscountPercentage, that.standardDiscountPercentage) &&
            Objects.equals(isStatementSent, that.isStatementSent) &&
            Objects.equals(isOnCreditHold, that.isOnCreditHold) &&
            Objects.equals(paymentDays, that.paymentDays) &&
            Objects.equals(deliveryRun, that.deliveryRun) &&
            Objects.equals(runPosition, that.runPosition) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(billToAddressSameAsDeliveryAddress, that.billToAddressSameAsDeliveryAddress) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(peopleId, that.peopleId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId) &&
            Objects.equals(deliveryAddressId, that.deliveryAddressId) &&
            Objects.equals(billToAddressId, that.billToAddressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        accountNumber,
        accountOpenedDate,
        standardDiscountPercentage,
        isStatementSent,
        isOnCreditHold,
        paymentDays,
        deliveryRun,
        runPosition,
        thumbnailUrl,
        billToAddressSameAsDeliveryAddress,
        lastEditedBy,
        validFrom,
        validTo,
        peopleId,
        deliveryMethodId,
        deliveryAddressId,
        billToAddressId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (accountOpenedDate != null ? "accountOpenedDate=" + accountOpenedDate + ", " : "") +
                (standardDiscountPercentage != null ? "standardDiscountPercentage=" + standardDiscountPercentage + ", " : "") +
                (isStatementSent != null ? "isStatementSent=" + isStatementSent + ", " : "") +
                (isOnCreditHold != null ? "isOnCreditHold=" + isOnCreditHold + ", " : "") +
                (paymentDays != null ? "paymentDays=" + paymentDays + ", " : "") +
                (deliveryRun != null ? "deliveryRun=" + deliveryRun + ", " : "") +
                (runPosition != null ? "runPosition=" + runPosition + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (billToAddressSameAsDeliveryAddress != null ? "billToAddressSameAsDeliveryAddress=" + billToAddressSameAsDeliveryAddress + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (peopleId != null ? "peopleId=" + peopleId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
                (deliveryAddressId != null ? "deliveryAddressId=" + deliveryAddressId + ", " : "") +
                (billToAddressId != null ? "billToAddressId=" + billToAddressId + ", " : "") +
            "}";
    }

}
