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
 * A Townships.
 */
@Entity
@Table(name = "townships")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Townships implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "culture_details")
    private String cultureDetails;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "townships", allowSetters = true)
    private Cities city;

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

    public Townships name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCultureDetails() {
        return cultureDetails;
    }

    public Townships cultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
        return this;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getDescription() {
        return description;
    }

    public Townships description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Townships validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Townships validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Cities getCity() {
        return city;
    }

    public Townships city(Cities cities) {
        this.city = cities;
        return this;
    }

    public void setCity(Cities cities) {
        this.city = cities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Townships)) {
            return false;
        }
        return id != null && id.equals(((Townships) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Townships{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", description='" + getDescription() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
