package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Suppliers} entity.
 */
public class SuppliersDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String supplierReference;

    private String bankAccountName;

    private String bankAccountBranch;

    private String bankAccountCode;

    private String bankAccountNumber;

    private String bankInternationalCode;

    @NotNull
    private Integer paymentDays;

    private String internalComments;

    @NotNull
    private String phoneNumber;

    private String faxNumber;

    private String websiteURL;

    private String webServiceUrl;

    private Integer creditRating;

    private Boolean activeFlag;

    private String thumbnailUrl;

    private Boolean pickupSameAsHeadOffice;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;


    private Long peopleId;

    private String peopleFullName;

    private Long supplierCategoryId;

    private String supplierCategoryName;

    private Long pickupAddressId;

    private Long headOfficeAddressId;
    private Set<DeliveryMethodsDTO> deliveryMethods = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierReference() {
        return supplierReference;
    }

    public void setSupplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountBranch() {
        return bankAccountBranch;
    }

    public void setBankAccountBranch(String bankAccountBranch) {
        this.bankAccountBranch = bankAccountBranch;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankInternationalCode() {
        return bankInternationalCode;
    }

    public void setBankInternationalCode(String bankInternationalCode) {
        this.bankInternationalCode = bankInternationalCode;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public Integer getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(Integer creditRating) {
        this.creditRating = creditRating;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean isPickupSameAsHeadOffice() {
        return pickupSameAsHeadOffice;
    }

    public void setPickupSameAsHeadOffice(Boolean pickupSameAsHeadOffice) {
        this.pickupSameAsHeadOffice = pickupSameAsHeadOffice;
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

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleFullName() {
        return peopleFullName;
    }

    public void setPeopleFullName(String peopleFullName) {
        this.peopleFullName = peopleFullName;
    }

    public Long getSupplierCategoryId() {
        return supplierCategoryId;
    }

    public void setSupplierCategoryId(Long supplierCategoriesId) {
        this.supplierCategoryId = supplierCategoriesId;
    }

    public String getSupplierCategoryName() {
        return supplierCategoryName;
    }

    public void setSupplierCategoryName(String supplierCategoriesName) {
        this.supplierCategoryName = supplierCategoriesName;
    }

    public Long getPickupAddressId() {
        return pickupAddressId;
    }

    public void setPickupAddressId(Long addressesId) {
        this.pickupAddressId = addressesId;
    }

    public Long getHeadOfficeAddressId() {
        return headOfficeAddressId;
    }

    public void setHeadOfficeAddressId(Long addressesId) {
        this.headOfficeAddressId = addressesId;
    }

    public Set<DeliveryMethodsDTO> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(Set<DeliveryMethodsDTO> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SuppliersDTO)) {
            return false;
        }

        return id != null && id.equals(((SuppliersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuppliersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", supplierReference='" + getSupplierReference() + "'" +
            ", bankAccountName='" + getBankAccountName() + "'" +
            ", bankAccountBranch='" + getBankAccountBranch() + "'" +
            ", bankAccountCode='" + getBankAccountCode() + "'" +
            ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", bankInternationalCode='" + getBankInternationalCode() + "'" +
            ", paymentDays=" + getPaymentDays() +
            ", internalComments='" + getInternalComments() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", websiteURL='" + getWebsiteURL() + "'" +
            ", webServiceUrl='" + getWebServiceUrl() + "'" +
            ", creditRating=" + getCreditRating() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", pickupSameAsHeadOffice='" + isPickupSameAsHeadOffice() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", peopleId=" + getPeopleId() +
            ", peopleFullName='" + getPeopleFullName() + "'" +
            ", supplierCategoryId=" + getSupplierCategoryId() +
            ", supplierCategoryName='" + getSupplierCategoryName() + "'" +
            ", pickupAddressId=" + getPickupAddressId() +
            ", headOfficeAddressId=" + getHeadOfficeAddressId() +
            ", deliveryMethods='" + getDeliveryMethods() + "'" +
            "}";
    }
}
