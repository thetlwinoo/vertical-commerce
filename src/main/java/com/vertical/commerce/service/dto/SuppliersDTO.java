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

    private Integer paymentDays;

    private String internalComments;

    @NotNull
    private String phoneNumber;

    private String emailAddress;

    private String nric;

    private String companyRegistrationNo;

    private String faxNumber;

    private String websiteUrl;

    private String webServiceUrl;

    private Integer creditRating;

    private Boolean officialStoreInd;

    private String storeName;

    private String logo;

    private String nricFrontPhoto;

    private String nricBackPhoto;

    @NotNull
    private String bankBookPhoto;

    private String companyRegistrationPhoto;

    private String distributorCertificatePhoto;

    @NotNull
    private Boolean activeFlag;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long supplierCategoryId;

    private String supplierCategoryName;

    private Long pickupAddressId;

    private Long headOfficeAddressId;

    private Long returnAddressId;

    private Long contactPersonId;

    private String contactPersonFullName;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public void setCompanyRegistrationNo(String companyRegistrationNo) {
        this.companyRegistrationNo = companyRegistrationNo;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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

    public Boolean isOfficialStoreInd() {
        return officialStoreInd;
    }

    public void setOfficialStoreInd(Boolean officialStoreInd) {
        this.officialStoreInd = officialStoreInd;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNricFrontPhoto() {
        return nricFrontPhoto;
    }

    public void setNricFrontPhoto(String nricFrontPhoto) {
        this.nricFrontPhoto = nricFrontPhoto;
    }

    public String getNricBackPhoto() {
        return nricBackPhoto;
    }

    public void setNricBackPhoto(String nricBackPhoto) {
        this.nricBackPhoto = nricBackPhoto;
    }

    public String getBankBookPhoto() {
        return bankBookPhoto;
    }

    public void setBankBookPhoto(String bankBookPhoto) {
        this.bankBookPhoto = bankBookPhoto;
    }

    public String getCompanyRegistrationPhoto() {
        return companyRegistrationPhoto;
    }

    public void setCompanyRegistrationPhoto(String companyRegistrationPhoto) {
        this.companyRegistrationPhoto = companyRegistrationPhoto;
    }

    public String getDistributorCertificatePhoto() {
        return distributorCertificatePhoto;
    }

    public void setDistributorCertificatePhoto(String distributorCertificatePhoto) {
        this.distributorCertificatePhoto = distributorCertificatePhoto;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
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

    public Long getReturnAddressId() {
        return returnAddressId;
    }

    public void setReturnAddressId(Long addressesId) {
        this.returnAddressId = addressesId;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(Long peopleId) {
        this.contactPersonId = peopleId;
    }

    public String getContactPersonFullName() {
        return contactPersonFullName;
    }

    public void setContactPersonFullName(String peopleFullName) {
        this.contactPersonFullName = peopleFullName;
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
            ", emailAddress='" + getEmailAddress() + "'" +
            ", nric='" + getNric() + "'" +
            ", companyRegistrationNo='" + getCompanyRegistrationNo() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", websiteUrl='" + getWebsiteUrl() + "'" +
            ", webServiceUrl='" + getWebServiceUrl() + "'" +
            ", creditRating=" + getCreditRating() +
            ", officialStoreInd='" + isOfficialStoreInd() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", nricFrontPhoto='" + getNricFrontPhoto() + "'" +
            ", nricBackPhoto='" + getNricBackPhoto() + "'" +
            ", bankBookPhoto='" + getBankBookPhoto() + "'" +
            ", companyRegistrationPhoto='" + getCompanyRegistrationPhoto() + "'" +
            ", distributorCertificatePhoto='" + getDistributorCertificatePhoto() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", supplierCategoryId=" + getSupplierCategoryId() +
            ", supplierCategoryName='" + getSupplierCategoryName() + "'" +
            ", pickupAddressId=" + getPickupAddressId() +
            ", headOfficeAddressId=" + getHeadOfficeAddressId() +
            ", returnAddressId=" + getReturnAddressId() +
            ", contactPersonId=" + getContactPersonId() +
            ", contactPersonFullName='" + getContactPersonFullName() + "'" +
            ", deliveryMethods='" + getDeliveryMethods() + "'" +
            "}";
    }
}
