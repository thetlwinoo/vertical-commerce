package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BusinessEntityAddress.
 */
@Entity
@Table(name = "business_entity_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BusinessEntityAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessEntityAddresses", allowSetters = true)
    private Addresses address;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessEntityAddresses", allowSetters = true)
    private People person;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessEntityAddresses", allowSetters = true)
    private AddressTypes addressType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Addresses getAddress() {
        return address;
    }

    public BusinessEntityAddress address(Addresses addresses) {
        this.address = addresses;
        return this;
    }

    public void setAddress(Addresses addresses) {
        this.address = addresses;
    }

    public People getPerson() {
        return person;
    }

    public BusinessEntityAddress person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }

    public AddressTypes getAddressType() {
        return addressType;
    }

    public BusinessEntityAddress addressType(AddressTypes addressTypes) {
        this.addressType = addressTypes;
        return this;
    }

    public void setAddressType(AddressTypes addressTypes) {
        this.addressType = addressTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessEntityAddress)) {
            return false;
        }
        return id != null && id.equals(((BusinessEntityAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessEntityAddress{" +
            "id=" + getId() +
            "}";
    }
}
