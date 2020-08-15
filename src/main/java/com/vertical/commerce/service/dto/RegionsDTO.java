package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Regions} entity.
 */
public class RegionsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    @Lob
    private String localization;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long stateProvinceId;

    private String stateProvinceName;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(Long stateProvincesId) {
        this.stateProvinceId = stateProvincesId;
    }

    public String getStateProvinceName() {
        return stateProvinceName;
    }

    public void setStateProvinceName(String stateProvincesName) {
        this.stateProvinceName = stateProvincesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionsDTO)) {
            return false;
        }

        return id != null && id.equals(((RegionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegionsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", stateProvinceId=" + getStateProvinceId() +
            ", stateProvinceName='" + getStateProvinceName() + "'" +
            "}";
    }
}
