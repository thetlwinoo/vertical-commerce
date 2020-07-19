package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Suppliers.
 */
@Entity
@Table(name = "suppliers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "supplier_reference")
    private String supplierReference;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_account_branch")
    private String bankAccountBranch;

    @Column(name = "bank_account_code")
    private String bankAccountCode;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_international_code")
    private String bankInternationalCode;

    @Column(name = "payment_days")
    private Integer paymentDays;

    @Column(name = "internal_comments")
    private String internalComments;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "nric")
    private String nric;

    @Column(name = "company_registration_no")
    private String companyRegistrationNo;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "web_service_url")
    private String webServiceUrl;

    @Column(name = "credit_rating")
    private Integer creditRating;

    @Column(name = "official_store_ind")
    private Boolean officialStoreInd;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "logo")
    private String logo;

    @Column(name = "nric_front_photo")
    private String nricFrontPhoto;

    @Column(name = "nric_back_photo")
    private String nricBackPhoto;

    @NotNull
    @Column(name = "bank_book_photo", nullable = false)
    private String bankBookPhoto;

    @Column(name = "company_registration_photo")
    private String companyRegistrationPhoto;

    @Column(name = "distributor_certificate_photo")
    private String distributorCertificatePhoto;

    @NotNull
    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @OneToMany(mappedBy = "supplierBanner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Photos> bannerLists = new HashSet<>();

    @OneToMany(mappedBy = "supplierDocument")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Photos> documentLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private SupplierCategories supplierCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private Addresses pickupAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private Addresses headOfficeAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private Addresses returnAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private People contactPerson;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "suppliers_delivery_method",
               joinColumns = @JoinColumn(name = "suppliers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "delivery_method_id", referencedColumnName = "id"))
    private Set<DeliveryMethods> deliveryMethods = new HashSet<>();

    @ManyToMany(mappedBy = "suppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<People> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Suppliers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierReference() {
        return supplierReference;
    }

    public Suppliers supplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
        return this;
    }

    public void setSupplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public Suppliers bankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
        return this;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountBranch() {
        return bankAccountBranch;
    }

    public Suppliers bankAccountBranch(String bankAccountBranch) {
        this.bankAccountBranch = bankAccountBranch;
        return this;
    }

    public void setBankAccountBranch(String bankAccountBranch) {
        this.bankAccountBranch = bankAccountBranch;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public Suppliers bankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
        return this;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Suppliers bankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankInternationalCode() {
        return bankInternationalCode;
    }

    public Suppliers bankInternationalCode(String bankInternationalCode) {
        this.bankInternationalCode = bankInternationalCode;
        return this;
    }

    public void setBankInternationalCode(String bankInternationalCode) {
        this.bankInternationalCode = bankInternationalCode;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public Suppliers paymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
        return this;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public Suppliers internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Suppliers phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Suppliers emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNric() {
        return nric;
    }

    public Suppliers nric(String nric) {
        this.nric = nric;
        return this;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public Suppliers companyRegistrationNo(String companyRegistrationNo) {
        this.companyRegistrationNo = companyRegistrationNo;
        return this;
    }

    public void setCompanyRegistrationNo(String companyRegistrationNo) {
        this.companyRegistrationNo = companyRegistrationNo;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public Suppliers faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public Suppliers websiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
        return this;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public Suppliers webServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
        return this;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public Integer getCreditRating() {
        return creditRating;
    }

    public Suppliers creditRating(Integer creditRating) {
        this.creditRating = creditRating;
        return this;
    }

    public void setCreditRating(Integer creditRating) {
        this.creditRating = creditRating;
    }

    public Boolean isOfficialStoreInd() {
        return officialStoreInd;
    }

    public Suppliers officialStoreInd(Boolean officialStoreInd) {
        this.officialStoreInd = officialStoreInd;
        return this;
    }

    public void setOfficialStoreInd(Boolean officialStoreInd) {
        this.officialStoreInd = officialStoreInd;
    }

    public String getStoreName() {
        return storeName;
    }

    public Suppliers storeName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLogo() {
        return logo;
    }

    public Suppliers logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNricFrontPhoto() {
        return nricFrontPhoto;
    }

    public Suppliers nricFrontPhoto(String nricFrontPhoto) {
        this.nricFrontPhoto = nricFrontPhoto;
        return this;
    }

    public void setNricFrontPhoto(String nricFrontPhoto) {
        this.nricFrontPhoto = nricFrontPhoto;
    }

    public String getNricBackPhoto() {
        return nricBackPhoto;
    }

    public Suppliers nricBackPhoto(String nricBackPhoto) {
        this.nricBackPhoto = nricBackPhoto;
        return this;
    }

    public void setNricBackPhoto(String nricBackPhoto) {
        this.nricBackPhoto = nricBackPhoto;
    }

    public String getBankBookPhoto() {
        return bankBookPhoto;
    }

    public Suppliers bankBookPhoto(String bankBookPhoto) {
        this.bankBookPhoto = bankBookPhoto;
        return this;
    }

    public void setBankBookPhoto(String bankBookPhoto) {
        this.bankBookPhoto = bankBookPhoto;
    }

    public String getCompanyRegistrationPhoto() {
        return companyRegistrationPhoto;
    }

    public Suppliers companyRegistrationPhoto(String companyRegistrationPhoto) {
        this.companyRegistrationPhoto = companyRegistrationPhoto;
        return this;
    }

    public void setCompanyRegistrationPhoto(String companyRegistrationPhoto) {
        this.companyRegistrationPhoto = companyRegistrationPhoto;
    }

    public String getDistributorCertificatePhoto() {
        return distributorCertificatePhoto;
    }

    public Suppliers distributorCertificatePhoto(String distributorCertificatePhoto) {
        this.distributorCertificatePhoto = distributorCertificatePhoto;
        return this;
    }

    public void setDistributorCertificatePhoto(String distributorCertificatePhoto) {
        this.distributorCertificatePhoto = distributorCertificatePhoto;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public Suppliers activeFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Suppliers validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Suppliers validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Set<Photos> getBannerLists() {
        return bannerLists;
    }

    public Suppliers bannerLists(Set<Photos> photos) {
        this.bannerLists = photos;
        return this;
    }

    public Suppliers addBannerList(Photos photos) {
        this.bannerLists.add(photos);
        photos.setSupplierBanner(this);
        return this;
    }

    public Suppliers removeBannerList(Photos photos) {
        this.bannerLists.remove(photos);
        photos.setSupplierBanner(null);
        return this;
    }

    public void setBannerLists(Set<Photos> photos) {
        this.bannerLists = photos;
    }

    public Set<Photos> getDocumentLists() {
        return documentLists;
    }

    public Suppliers documentLists(Set<Photos> photos) {
        this.documentLists = photos;
        return this;
    }

    public Suppliers addDocumentList(Photos photos) {
        this.documentLists.add(photos);
        photos.setSupplierDocument(this);
        return this;
    }

    public Suppliers removeDocumentList(Photos photos) {
        this.documentLists.remove(photos);
        photos.setSupplierDocument(null);
        return this;
    }

    public void setDocumentLists(Set<Photos> photos) {
        this.documentLists = photos;
    }

    public SupplierCategories getSupplierCategory() {
        return supplierCategory;
    }

    public Suppliers supplierCategory(SupplierCategories supplierCategories) {
        this.supplierCategory = supplierCategories;
        return this;
    }

    public void setSupplierCategory(SupplierCategories supplierCategories) {
        this.supplierCategory = supplierCategories;
    }

    public Addresses getPickupAddress() {
        return pickupAddress;
    }

    public Suppliers pickupAddress(Addresses addresses) {
        this.pickupAddress = addresses;
        return this;
    }

    public void setPickupAddress(Addresses addresses) {
        this.pickupAddress = addresses;
    }

    public Addresses getHeadOfficeAddress() {
        return headOfficeAddress;
    }

    public Suppliers headOfficeAddress(Addresses addresses) {
        this.headOfficeAddress = addresses;
        return this;
    }

    public void setHeadOfficeAddress(Addresses addresses) {
        this.headOfficeAddress = addresses;
    }

    public Addresses getReturnAddress() {
        return returnAddress;
    }

    public Suppliers returnAddress(Addresses addresses) {
        this.returnAddress = addresses;
        return this;
    }

    public void setReturnAddress(Addresses addresses) {
        this.returnAddress = addresses;
    }

    public People getContactPerson() {
        return contactPerson;
    }

    public Suppliers contactPerson(People people) {
        this.contactPerson = people;
        return this;
    }

    public void setContactPerson(People people) {
        this.contactPerson = people;
    }

    public Set<DeliveryMethods> getDeliveryMethods() {
        return deliveryMethods;
    }

    public Suppliers deliveryMethods(Set<DeliveryMethods> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
        return this;
    }

    public Suppliers addDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethods.add(deliveryMethods);
        deliveryMethods.getSuppliers().add(this);
        return this;
    }

    public Suppliers removeDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethods.remove(deliveryMethods);
        deliveryMethods.getSuppliers().remove(this);
        return this;
    }

    public void setDeliveryMethods(Set<DeliveryMethods> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    public Set<People> getPeople() {
        return people;
    }

    public Suppliers people(Set<People> people) {
        this.people = people;
        return this;
    }

    public Suppliers addPeople(People people) {
        this.people.add(people);
        people.getSuppliers().add(this);
        return this;
    }

    public Suppliers removePeople(People people) {
        this.people.remove(people);
        people.getSuppliers().remove(this);
        return this;
    }

    public void setPeople(Set<People> people) {
        this.people = people;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suppliers)) {
            return false;
        }
        return id != null && id.equals(((Suppliers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Suppliers{" +
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
            "}";
    }
}
