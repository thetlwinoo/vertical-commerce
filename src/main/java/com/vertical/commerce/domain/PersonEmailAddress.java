package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PersonEmailAddress.
 */
@Entity
@Table(name = "person_email_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonEmailAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "personEmailAddresses", allowSetters = true)
    private People person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public PersonEmailAddress emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public PersonEmailAddress defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public PersonEmailAddress validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public PersonEmailAddress validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public People getPerson() {
        return person;
    }

    public PersonEmailAddress person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonEmailAddress)) {
            return false;
        }
        return id != null && id.equals(((PersonEmailAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonEmailAddress{" +
            "id=" + getId() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
