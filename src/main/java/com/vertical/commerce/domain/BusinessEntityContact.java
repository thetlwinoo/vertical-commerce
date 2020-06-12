package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BusinessEntityContact.
 */
@Entity
@Table(name = "business_entity_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BusinessEntityContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessEntityContacts", allowSetters = true)
    private People person;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessEntityContacts", allowSetters = true)
    private ContactType contactType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public People getPerson() {
        return person;
    }

    public BusinessEntityContact person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public BusinessEntityContact contactType(ContactType contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessEntityContact)) {
            return false;
        }
        return id != null && id.equals(((BusinessEntityContact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessEntityContact{" +
            "id=" + getId() +
            "}";
    }
}
