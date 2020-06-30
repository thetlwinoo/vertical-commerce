package com.vertical.commerce.domain;

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

    @NotNull
    @Column(name = "payment_days", nullable = false)
    private Integer paymentDays;

    @Column(name = "internal_comments")
    private String internalComments;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "website_url")
    private String websiteURL;

    @Column(name = "web_service_url")
    private String webServiceUrl;

    @Column(name = "credit_rating")
    private Integer creditRating;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "pickup_same_as_head_office")
    private Boolean pickupSameAsHeadOffice;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @OneToOne
    @JoinColumn(unique = true)
    private People people;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private SupplierCategories supplierCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private Addresses pickupAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private Addresses headOfficeAddress;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "suppliers_delivery_method",
               joinColumns = @JoinColumn(name = "suppliers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "delivery_method_id", referencedColumnName = "id"))
    private Set<DeliveryMethods> deliveryMethods = new HashSet<>();

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

    public String getWebsiteURL() {
        return websiteURL;
    }

    public Suppliers websiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
        return this;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Suppliers thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean isPickupSameAsHeadOffice() {
        return pickupSameAsHeadOffice;
    }

    public Suppliers pickupSameAsHeadOffice(Boolean pickupSameAsHeadOffice) {
        this.pickupSameAsHeadOffice = pickupSameAsHeadOffice;
        return this;
    }

    public void setPickupSameAsHeadOffice(Boolean pickupSameAsHeadOffice) {
        this.pickupSameAsHeadOffice = pickupSameAsHeadOffice;
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

    public People getPeople() {
        return people;
    }

    public Suppliers people(People people) {
        this.people = people;
        return this;
    }

    public void setPeople(People people) {
        this.people = people;
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
            ", faxNumber='" + getFaxNumber() + "'" +
            ", websiteURL='" + getWebsiteURL() + "'" +
            ", webServiceUrl='" + getWebServiceUrl() + "'" +
            ", creditRating=" + getCreditRating() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", pickupSameAsHeadOffice='" + isPickupSameAsHeadOffice() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
