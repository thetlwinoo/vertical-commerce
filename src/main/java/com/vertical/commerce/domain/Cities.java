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
 * A Cities.
 */
@Entity
@Table(name = "cities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cities implements Serializable {

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

    @Column(name = "location")
    private String location;

    @Column(name = "latest_recorded_population")
    private Long latestRecordedPopulation;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "cities", allowSetters = true)
    private Regions region;

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

    public Cities name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCultureDetails() {
        return cultureDetails;
    }

    public Cities cultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
        return this;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getLocation() {
        return location;
    }

    public Cities location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public Cities latestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
        return this;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Cities validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Cities validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Regions getRegion() {
        return region;
    }

    public Cities region(Regions regions) {
        this.region = regions;
        return this;
    }

    public void setRegion(Regions regions) {
        this.region = regions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cities)) {
            return false;
        }
        return id != null && id.equals(((Cities) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cities{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", location='" + getLocation() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
