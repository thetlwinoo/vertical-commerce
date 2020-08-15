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
 * Criteria class for the {@link com.vertical.commerce.domain.Addresses} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.AddressesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contactPerson;

    private StringFilter contactNumber;

    private StringFilter contactEmailAddress;

    private StringFilter addressLine1;

    private StringFilter addressLine2;

    private StringFilter postalCode;

    private StringFilter description;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter regionId;

    private LongFilter cityId;

    private LongFilter townshipId;

    private LongFilter addressTypeId;

    private LongFilter customerId;

    private LongFilter supplierId;

    public AddressesCriteria() {
    }

    public AddressesCriteria(AddressesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contactPerson = other.contactPerson == null ? null : other.contactPerson.copy();
        this.contactNumber = other.contactNumber == null ? null : other.contactNumber.copy();
        this.contactEmailAddress = other.contactEmailAddress == null ? null : other.contactEmailAddress.copy();
        this.addressLine1 = other.addressLine1 == null ? null : other.addressLine1.copy();
        this.addressLine2 = other.addressLine2 == null ? null : other.addressLine2.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.townshipId = other.townshipId == null ? null : other.townshipId.copy();
        this.addressTypeId = other.addressTypeId == null ? null : other.addressTypeId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
    }

    @Override
    public AddressesCriteria copy() {
        return new AddressesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(StringFilter contactPerson) {
        this.contactPerson = contactPerson;
    }

    public StringFilter getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(StringFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public StringFilter getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(StringFilter contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(LongFilter townshipId) {
        this.townshipId = townshipId;
    }

    public LongFilter getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(LongFilter addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressesCriteria that = (AddressesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(contactPerson, that.contactPerson) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(contactEmailAddress, that.contactEmailAddress) &&
            Objects.equals(addressLine1, that.addressLine1) &&
            Objects.equals(addressLine2, that.addressLine2) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(description, that.description) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(townshipId, that.townshipId) &&
            Objects.equals(addressTypeId, that.addressTypeId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        contactPerson,
        contactNumber,
        contactEmailAddress,
        addressLine1,
        addressLine2,
        postalCode,
        description,
        validFrom,
        validTo,
        regionId,
        cityId,
        townshipId,
        addressTypeId,
        customerId,
        supplierId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contactPerson != null ? "contactPerson=" + contactPerson + ", " : "") +
                (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
                (contactEmailAddress != null ? "contactEmailAddress=" + contactEmailAddress + ", " : "") +
                (addressLine1 != null ? "addressLine1=" + addressLine1 + ", " : "") +
                (addressLine2 != null ? "addressLine2=" + addressLine2 + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (townshipId != null ? "townshipId=" + townshipId + ", " : "") +
                (addressTypeId != null ? "addressTypeId=" + addressTypeId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            "}";
    }

}
