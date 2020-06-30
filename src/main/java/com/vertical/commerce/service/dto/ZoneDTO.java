package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Zone} entity.
 */
public class ZoneDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;


    private Long cityId;

    private String cityName;
    
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
        if (!(o instanceof ZoneDTO)) {
            return false;
        }

        return id != null && id.equals(((ZoneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            "}";
    }
}
