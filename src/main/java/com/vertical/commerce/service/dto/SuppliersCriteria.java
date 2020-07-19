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
 * Criteria class for the {@link com.vertical.commerce.domain.Suppliers} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.SuppliersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /suppliers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SuppliersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter supplierReference;

    private StringFilter bankAccountName;

    private StringFilter bankAccountBranch;

    private StringFilter bankAccountCode;

    private StringFilter bankAccountNumber;

    private StringFilter bankInternationalCode;

    private IntegerFilter paymentDays;

    private StringFilter internalComments;

    private StringFilter phoneNumber;

    private StringFilter emailAddress;

    private StringFilter nric;

    private StringFilter companyRegistrationNo;

    private StringFilter faxNumber;

    private StringFilter websiteUrl;

    private StringFilter webServiceUrl;

    private IntegerFilter creditRating;

    private BooleanFilter officialStoreInd;

    private StringFilter storeName;

    private StringFilter logo;

    private StringFilter nricFrontPhoto;

    private StringFilter nricBackPhoto;

    private StringFilter bankBookPhoto;

    private StringFilter companyRegistrationPhoto;

    private StringFilter distributorCertificatePhoto;

    private BooleanFilter activeFlag;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter bannerListId;

    private LongFilter documentListId;

    private LongFilter supplierCategoryId;

    private LongFilter pickupAddressId;

    private LongFilter headOfficeAddressId;

    private LongFilter returnAddressId;

    private LongFilter contactPersonId;

    private LongFilter deliveryMethodId;

    private LongFilter peopleId;

    public SuppliersCriteria() {
    }

    public SuppliersCriteria(SuppliersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.supplierReference = other.supplierReference == null ? null : other.supplierReference.copy();
        this.bankAccountName = other.bankAccountName == null ? null : other.bankAccountName.copy();
        this.bankAccountBranch = other.bankAccountBranch == null ? null : other.bankAccountBranch.copy();
        this.bankAccountCode = other.bankAccountCode == null ? null : other.bankAccountCode.copy();
        this.bankAccountNumber = other.bankAccountNumber == null ? null : other.bankAccountNumber.copy();
        this.bankInternationalCode = other.bankInternationalCode == null ? null : other.bankInternationalCode.copy();
        this.paymentDays = other.paymentDays == null ? null : other.paymentDays.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.nric = other.nric == null ? null : other.nric.copy();
        this.companyRegistrationNo = other.companyRegistrationNo == null ? null : other.companyRegistrationNo.copy();
        this.faxNumber = other.faxNumber == null ? null : other.faxNumber.copy();
        this.websiteUrl = other.websiteUrl == null ? null : other.websiteUrl.copy();
        this.webServiceUrl = other.webServiceUrl == null ? null : other.webServiceUrl.copy();
        this.creditRating = other.creditRating == null ? null : other.creditRating.copy();
        this.officialStoreInd = other.officialStoreInd == null ? null : other.officialStoreInd.copy();
        this.storeName = other.storeName == null ? null : other.storeName.copy();
        this.logo = other.logo == null ? null : other.logo.copy();
        this.nricFrontPhoto = other.nricFrontPhoto == null ? null : other.nricFrontPhoto.copy();
        this.nricBackPhoto = other.nricBackPhoto == null ? null : other.nricBackPhoto.copy();
        this.bankBookPhoto = other.bankBookPhoto == null ? null : other.bankBookPhoto.copy();
        this.companyRegistrationPhoto = other.companyRegistrationPhoto == null ? null : other.companyRegistrationPhoto.copy();
        this.distributorCertificatePhoto = other.distributorCertificatePhoto == null ? null : other.distributorCertificatePhoto.copy();
        this.activeFlag = other.activeFlag == null ? null : other.activeFlag.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.bannerListId = other.bannerListId == null ? null : other.bannerListId.copy();
        this.documentListId = other.documentListId == null ? null : other.documentListId.copy();
        this.supplierCategoryId = other.supplierCategoryId == null ? null : other.supplierCategoryId.copy();
        this.pickupAddressId = other.pickupAddressId == null ? null : other.pickupAddressId.copy();
        this.headOfficeAddressId = other.headOfficeAddressId == null ? null : other.headOfficeAddressId.copy();
        this.returnAddressId = other.returnAddressId == null ? null : other.returnAddressId.copy();
        this.contactPersonId = other.contactPersonId == null ? null : other.contactPersonId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
        this.peopleId = other.peopleId == null ? null : other.peopleId.copy();
    }

    @Override
    public SuppliersCriteria copy() {
        return new SuppliersCriteria(this);
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

    public StringFilter getSupplierReference() {
        return supplierReference;
    }

    public void setSupplierReference(StringFilter supplierReference) {
        this.supplierReference = supplierReference;
    }

    public StringFilter getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(StringFilter bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public StringFilter getBankAccountBranch() {
        return bankAccountBranch;
    }

    public void setBankAccountBranch(StringFilter bankAccountBranch) {
        this.bankAccountBranch = bankAccountBranch;
    }

    public StringFilter getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(StringFilter bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public StringFilter getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(StringFilter bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public StringFilter getBankInternationalCode() {
        return bankInternationalCode;
    }

    public void setBankInternationalCode(StringFilter bankInternationalCode) {
        this.bankInternationalCode = bankInternationalCode;
    }

    public IntegerFilter getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(IntegerFilter paymentDays) {
        this.paymentDays = paymentDays;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public StringFilter getNric() {
        return nric;
    }

    public void setNric(StringFilter nric) {
        this.nric = nric;
    }

    public StringFilter getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public void setCompanyRegistrationNo(StringFilter companyRegistrationNo) {
        this.companyRegistrationNo = companyRegistrationNo;
    }

    public StringFilter getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(StringFilter faxNumber) {
        this.faxNumber = faxNumber;
    }

    public StringFilter getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(StringFilter websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public StringFilter getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(StringFilter webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public IntegerFilter getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(IntegerFilter creditRating) {
        this.creditRating = creditRating;
    }

    public BooleanFilter getOfficialStoreInd() {
        return officialStoreInd;
    }

    public void setOfficialStoreInd(BooleanFilter officialStoreInd) {
        this.officialStoreInd = officialStoreInd;
    }

    public StringFilter getStoreName() {
        return storeName;
    }

    public void setStoreName(StringFilter storeName) {
        this.storeName = storeName;
    }

    public StringFilter getLogo() {
        return logo;
    }

    public void setLogo(StringFilter logo) {
        this.logo = logo;
    }

    public StringFilter getNricFrontPhoto() {
        return nricFrontPhoto;
    }

    public void setNricFrontPhoto(StringFilter nricFrontPhoto) {
        this.nricFrontPhoto = nricFrontPhoto;
    }

    public StringFilter getNricBackPhoto() {
        return nricBackPhoto;
    }

    public void setNricBackPhoto(StringFilter nricBackPhoto) {
        this.nricBackPhoto = nricBackPhoto;
    }

    public StringFilter getBankBookPhoto() {
        return bankBookPhoto;
    }

    public void setBankBookPhoto(StringFilter bankBookPhoto) {
        this.bankBookPhoto = bankBookPhoto;
    }

    public StringFilter getCompanyRegistrationPhoto() {
        return companyRegistrationPhoto;
    }

    public void setCompanyRegistrationPhoto(StringFilter companyRegistrationPhoto) {
        this.companyRegistrationPhoto = companyRegistrationPhoto;
    }

    public StringFilter getDistributorCertificatePhoto() {
        return distributorCertificatePhoto;
    }

    public void setDistributorCertificatePhoto(StringFilter distributorCertificatePhoto) {
        this.distributorCertificatePhoto = distributorCertificatePhoto;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
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

    public LongFilter getBannerListId() {
        return bannerListId;
    }

    public void setBannerListId(LongFilter bannerListId) {
        this.bannerListId = bannerListId;
    }

    public LongFilter getDocumentListId() {
        return documentListId;
    }

    public void setDocumentListId(LongFilter documentListId) {
        this.documentListId = documentListId;
    }

    public LongFilter getSupplierCategoryId() {
        return supplierCategoryId;
    }

    public void setSupplierCategoryId(LongFilter supplierCategoryId) {
        this.supplierCategoryId = supplierCategoryId;
    }

    public LongFilter getPickupAddressId() {
        return pickupAddressId;
    }

    public void setPickupAddressId(LongFilter pickupAddressId) {
        this.pickupAddressId = pickupAddressId;
    }

    public LongFilter getHeadOfficeAddressId() {
        return headOfficeAddressId;
    }

    public void setHeadOfficeAddressId(LongFilter headOfficeAddressId) {
        this.headOfficeAddressId = headOfficeAddressId;
    }

    public LongFilter getReturnAddressId() {
        return returnAddressId;
    }

    public void setReturnAddressId(LongFilter returnAddressId) {
        this.returnAddressId = returnAddressId;
    }

    public LongFilter getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(LongFilter contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public LongFilter getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(LongFilter peopleId) {
        this.peopleId = peopleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SuppliersCriteria that = (SuppliersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(supplierReference, that.supplierReference) &&
            Objects.equals(bankAccountName, that.bankAccountName) &&
            Objects.equals(bankAccountBranch, that.bankAccountBranch) &&
            Objects.equals(bankAccountCode, that.bankAccountCode) &&
            Objects.equals(bankAccountNumber, that.bankAccountNumber) &&
            Objects.equals(bankInternationalCode, that.bankInternationalCode) &&
            Objects.equals(paymentDays, that.paymentDays) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(nric, that.nric) &&
            Objects.equals(companyRegistrationNo, that.companyRegistrationNo) &&
            Objects.equals(faxNumber, that.faxNumber) &&
            Objects.equals(websiteUrl, that.websiteUrl) &&
            Objects.equals(webServiceUrl, that.webServiceUrl) &&
            Objects.equals(creditRating, that.creditRating) &&
            Objects.equals(officialStoreInd, that.officialStoreInd) &&
            Objects.equals(storeName, that.storeName) &&
            Objects.equals(logo, that.logo) &&
            Objects.equals(nricFrontPhoto, that.nricFrontPhoto) &&
            Objects.equals(nricBackPhoto, that.nricBackPhoto) &&
            Objects.equals(bankBookPhoto, that.bankBookPhoto) &&
            Objects.equals(companyRegistrationPhoto, that.companyRegistrationPhoto) &&
            Objects.equals(distributorCertificatePhoto, that.distributorCertificatePhoto) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(bannerListId, that.bannerListId) &&
            Objects.equals(documentListId, that.documentListId) &&
            Objects.equals(supplierCategoryId, that.supplierCategoryId) &&
            Objects.equals(pickupAddressId, that.pickupAddressId) &&
            Objects.equals(headOfficeAddressId, that.headOfficeAddressId) &&
            Objects.equals(returnAddressId, that.returnAddressId) &&
            Objects.equals(contactPersonId, that.contactPersonId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId) &&
            Objects.equals(peopleId, that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        supplierReference,
        bankAccountName,
        bankAccountBranch,
        bankAccountCode,
        bankAccountNumber,
        bankInternationalCode,
        paymentDays,
        internalComments,
        phoneNumber,
        emailAddress,
        nric,
        companyRegistrationNo,
        faxNumber,
        websiteUrl,
        webServiceUrl,
        creditRating,
        officialStoreInd,
        storeName,
        logo,
        nricFrontPhoto,
        nricBackPhoto,
        bankBookPhoto,
        companyRegistrationPhoto,
        distributorCertificatePhoto,
        activeFlag,
        validFrom,
        validTo,
        bannerListId,
        documentListId,
        supplierCategoryId,
        pickupAddressId,
        headOfficeAddressId,
        returnAddressId,
        contactPersonId,
        deliveryMethodId,
        peopleId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuppliersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (supplierReference != null ? "supplierReference=" + supplierReference + ", " : "") +
                (bankAccountName != null ? "bankAccountName=" + bankAccountName + ", " : "") +
                (bankAccountBranch != null ? "bankAccountBranch=" + bankAccountBranch + ", " : "") +
                (bankAccountCode != null ? "bankAccountCode=" + bankAccountCode + ", " : "") +
                (bankAccountNumber != null ? "bankAccountNumber=" + bankAccountNumber + ", " : "") +
                (bankInternationalCode != null ? "bankInternationalCode=" + bankInternationalCode + ", " : "") +
                (paymentDays != null ? "paymentDays=" + paymentDays + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
                (nric != null ? "nric=" + nric + ", " : "") +
                (companyRegistrationNo != null ? "companyRegistrationNo=" + companyRegistrationNo + ", " : "") +
                (faxNumber != null ? "faxNumber=" + faxNumber + ", " : "") +
                (websiteUrl != null ? "websiteUrl=" + websiteUrl + ", " : "") +
                (webServiceUrl != null ? "webServiceUrl=" + webServiceUrl + ", " : "") +
                (creditRating != null ? "creditRating=" + creditRating + ", " : "") +
                (officialStoreInd != null ? "officialStoreInd=" + officialStoreInd + ", " : "") +
                (storeName != null ? "storeName=" + storeName + ", " : "") +
                (logo != null ? "logo=" + logo + ", " : "") +
                (nricFrontPhoto != null ? "nricFrontPhoto=" + nricFrontPhoto + ", " : "") +
                (nricBackPhoto != null ? "nricBackPhoto=" + nricBackPhoto + ", " : "") +
                (bankBookPhoto != null ? "bankBookPhoto=" + bankBookPhoto + ", " : "") +
                (companyRegistrationPhoto != null ? "companyRegistrationPhoto=" + companyRegistrationPhoto + ", " : "") +
                (distributorCertificatePhoto != null ? "distributorCertificatePhoto=" + distributorCertificatePhoto + ", " : "") +
                (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (bannerListId != null ? "bannerListId=" + bannerListId + ", " : "") +
                (documentListId != null ? "documentListId=" + documentListId + ", " : "") +
                (supplierCategoryId != null ? "supplierCategoryId=" + supplierCategoryId + ", " : "") +
                (pickupAddressId != null ? "pickupAddressId=" + pickupAddressId + ", " : "") +
                (headOfficeAddressId != null ? "headOfficeAddressId=" + headOfficeAddressId + ", " : "") +
                (returnAddressId != null ? "returnAddressId=" + returnAddressId + ", " : "") +
                (contactPersonId != null ? "contactPersonId=" + contactPersonId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
                (peopleId != null ? "peopleId=" + peopleId + ", " : "") +
            "}";
    }

}
