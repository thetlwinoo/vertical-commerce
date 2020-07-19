package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.vertical.commerce.domain.enumeration.AddressTypeRefer;

/**
 * A AddressTypes.
 */
@Entity
@Table(name = "address_types")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AddressTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "refer")
    private AddressTypeRefer refer;

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

    public AddressTypes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressTypeRefer getRefer() {
        return refer;
    }

    public AddressTypes refer(AddressTypeRefer refer) {
        this.refer = refer;
        return this;
    }

    public void setRefer(AddressTypeRefer refer) {
        this.refer = refer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressTypes)) {
            return false;
        }
        return id != null && id.equals(((AddressTypes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressTypes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", refer='" + getRefer() + "'" +
            "}";
    }
}
