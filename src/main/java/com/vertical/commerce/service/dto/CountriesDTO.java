package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Countries} entity.
 */
public class CountriesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String formalName;

    private String isoAplha3Code;

    private Integer isoNumericCode;

    private String countryType;

    private Long latestRecordedPopulation;

    @NotNull
    private String continent;

    @NotNull
    private String region;

    @NotNull
    private String subRegion;

    private String border;

    @Lob
    private String localization;

    @NotNull
    private Instant validFrom;

    private Instant validTo;

    
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

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getIsoAplha3Code() {
        return isoAplha3Code;
    }

    public void setIsoAplha3Code(String isoAplha3Code) {
        this.isoAplha3Code = isoAplha3Code;
    }

    public Integer getIsoNumericCode() {
        return isoNumericCode;
    }

    public void setIsoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountriesDTO)) {
            return false;
        }

        return id != null && id.equals(((CountriesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountriesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", formalName='" + getFormalName() + "'" +
            ", isoAplha3Code='" + getIsoAplha3Code() + "'" +
            ", isoNumericCode=" + getIsoNumericCode() +
            ", countryType='" + getCountryType() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", continent='" + getContinent() + "'" +
            ", region='" + getRegion() + "'" +
            ", subRegion='" + getSubRegion() + "'" +
            ", border='" + getBorder() + "'" +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
