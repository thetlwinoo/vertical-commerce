package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Discounts.
 */
@Entity
@Table(name = "discounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Discounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_code")
    private String discountCode;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "discounts", allowSetters = true)
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "discounts", allowSetters = true)
    private DiscountTypes discountType;

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

    public Discounts name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Discounts description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public Discounts discountCode(String discountCode) {
        this.discountCode = discountCode;
        return this;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Discounts validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Discounts validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public Discounts supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public DiscountTypes getDiscountType() {
        return discountType;
    }

    public Discounts discountType(DiscountTypes discountTypes) {
        this.discountType = discountTypes;
        return this;
    }

    public void setDiscountType(DiscountTypes discountTypes) {
        this.discountType = discountTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discounts)) {
            return false;
        }
        return id != null && id.equals(((Discounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Discounts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", discountCode='" + getDiscountCode() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
