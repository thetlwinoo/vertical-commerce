package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Townships} entity.
 */
public class TownshipsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @Lob
    private String cultureDetails;

    private String description;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long cityId;

    private String cityName;
    
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

    public String getCultureDetails() {
        return cultureDetails;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long citiesId) {
        this.cityId = citiesId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String citiesName) {
        this.cityName = citiesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownshipsDTO)) {
            return false;
        }

        return id != null && id.equals(((TownshipsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownshipsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", description='" + getDescription() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            "}";
    }
}
