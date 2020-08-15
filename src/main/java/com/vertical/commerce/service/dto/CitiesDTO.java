package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Cities} entity.
 */
public class CitiesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String location;

    private Long latestRecordedPopulation;

    @Lob
    private String localization;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long regionId;

    private String regionName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionsId) {
        this.regionId = regionsId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionsName) {
        this.regionName = regionsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitiesDTO)) {
            return false;
        }

        return id != null && id.equals(((CitiesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitiesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", regionId=" + getRegionId() +
            ", regionName='" + getRegionName() + "'" +
            "}";
    }
}
