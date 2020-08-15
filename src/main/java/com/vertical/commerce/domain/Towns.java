package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Towns.
 */
@Entity
@Table(name = "towns")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Towns implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "description")
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "localization")
    private String localization;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "towns", allowSetters = true)
    private Townships township;

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

    public Towns name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Towns postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return description;
    }

    public Towns description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalization() {
        return localization;
    }

    public Towns localization(String localization) {
        this.localization = localization;
        return this;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Towns validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Towns validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Townships getTownship() {
        return township;
    }

    public Towns township(Townships townships) {
        this.township = townships;
        return this;
    }

    public void setTownship(Townships townships) {
        this.township = townships;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Towns)) {
            return false;
        }
        return id != null && id.equals(((Towns) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Towns{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
