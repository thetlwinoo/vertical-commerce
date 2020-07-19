package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.StateProvinces} entity.
 */
public class StateProvincesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @Lob
    private String cultureDetails;

    private String salesTerritory;

    private String border;

    private Long latestRecordedPopulation;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long countryId;

    private String countryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCultureDetails() {
        return cultureDetails;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getSalesTerritory() {
        return salesTerritory;
    }

    public void setSalesTerritory(String salesTerritory) {
        this.salesTerritory = salesTerritory;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countriesId) {
        this.countryId = countriesId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countriesName) {
        this.countryName = countriesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateProvincesDTO)) {
            return false;
        }

        return id != null && id.equals(((StateProvincesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateProvincesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", salesTerritory='" + getSalesTerritory() + "'" +
            ", border='" + getBorder() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", countryId=" + getCountryId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
