package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Addresses.
 */
@Entity
@Table(name = "addresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Addresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_number")
    private String contactNumber;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "contact_email_address")
    private String contactEmailAddress;

    @NotNull
    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Regions region;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Cities city;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Townships township;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Towns town;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private AddressTypes addressType;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Customers customerAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Suppliers supplierAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Addresses contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Addresses contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public Addresses contactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
        return this;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public Addresses addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Addresses addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Addresses postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return description;
    }

    public Addresses description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Addresses validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Addresses validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Regions getRegion() {
        return region;
    }

    public Addresses region(Regions regions) {
        this.region = regions;
        return this;
    }

    public void setRegion(Regions regions) {
        this.region = regions;
    }

    public Cities getCity() {
        return city;
    }

    public Addresses city(Cities cities) {
        this.city = cities;
        return this;
    }

    public void setCity(Cities cities) {
        this.city = cities;
    }

    public Townships getTownship() {
        return township;
    }

    public Addresses township(Townships townships) {
        this.township = townships;
        return this;
    }

    public void setTownship(Townships townships) {
        this.township = townships;
    }

    public Towns getTown() {
        return town;
    }

    public Addresses town(Towns towns) {
        this.town = towns;
        return this;
    }

    public void setTown(Towns towns) {
        this.town = towns;
    }

    public AddressTypes getAddressType() {
        return addressType;
    }

    public Addresses addressType(AddressTypes addressTypes) {
        this.addressType = addressTypes;
        return this;
    }

    public void setAddressType(AddressTypes addressTypes) {
        this.addressType = addressTypes;
    }

    public Customers getCustomerAddress() {
        return customerAddress;
    }

    public Addresses customerAddress(Customers customers) {
        this.customerAddress = customers;
        return this;
    }

    public void setCustomerAddress(Customers customers) {
        this.customerAddress = customers;
    }

    public Suppliers getSupplierAddress() {
        return supplierAddress;
    }

    public Addresses supplierAddress(Suppliers suppliers) {
        this.supplierAddress = suppliers;
        return this;
    }

    public void setSupplierAddress(Suppliers suppliers) {
        this.supplierAddress = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Addresses)) {
            return false;
        }
        return id != null && id.equals(((Addresses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Addresses{" +
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
            "}";
    }
}
