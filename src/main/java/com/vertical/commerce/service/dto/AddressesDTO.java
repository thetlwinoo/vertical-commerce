package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Addresses} entity.
 */
public class AddressesDTO implements Serializable {
    
    private Long id;

    private String contactPerson;

    private String contactNumber;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String contactEmailAddress;

    @NotNull
    private String addressLine1;

    private String addressLine2;

    private String postalCode;

    private String description;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long regionId;

    private String regionName;

    private Long cityId;

    private String cityName;

    private Long townshipId;

    private String townshipName;

    private Long addressTypeId;

    private String addressTypeName;

    private Long customerId;

    private String customerName;

    private Long supplierId;

    private String supplierName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionsId) {
        this.regionId = regionsId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionsName) {
        this.regionName = regionsName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long citiesId) {
        this.cityId = citiesId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String citiesName) {
        this.cityName = citiesName;
    }

    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipsId) {
        this.townshipId = townshipsId;
    }

    public String getTownshipName() {
        return townshipName;
    }

    public void setTownshipName(String townshipsName) {
        this.townshipName = townshipsName;
    }

    public Long getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Long addressTypesId) {
        this.addressTypeId = addressTypesId;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypesName) {
        this.addressTypeName = addressTypesName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customersName) {
        this.customerName = customersName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressesDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesDTO{" +
            "id=" + getId() +
            ", contactPerson='" + getContactPerson() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", contactEmailAddress='" + getContactEmailAddress() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", regionId=" + getRegionId() +
            ", regionName='" + getRegionName() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            ", townshipId=" + getTownshipId() +
            ", townshipName='" + getTownshipName() + "'" +
            ", addressTypeId=" + getAddressTypeId() +
            ", addressTypeName='" + getAddressTypeName() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            "}";
    }
}
