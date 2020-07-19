package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PersonPhone.
 */
@Entity
@Table(name = "person_phone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "personPhones", allowSetters = true)
    private People person;

    @ManyToOne
    @JsonIgnoreProperties(value = "personPhones", allowSetters = true)
    private PhoneNumberType phoneNumberType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PersonPhone phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public PersonPhone defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public PersonPhone validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public PersonPhone validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public People getPerson() {
        return person;
    }

    public PersonPhone person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }

    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    public PersonPhone phoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
        return this;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonPhone)) {
            return false;
        }
        return id != null && id.equals(((PersonPhone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonPhone{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
