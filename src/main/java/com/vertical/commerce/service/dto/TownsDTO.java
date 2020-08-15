package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Towns} entity.
 */
public class TownsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String postalCode;

    private String description;

    @Lob
    private String localization;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long townshipId;

    private String townshipName;
    
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipsId) {
        this.townshipId = townshipsId;
    }

    public String getTownshipName() {
        return townshipName;
    }

    public void setTownshipName(String townshipsName) {
        this.townshipName = townshipsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownsDTO)) {
            return false;
        }

        return id != null && id.equals(((TownsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", townshipId=" + getTownshipId() +
            ", townshipName='" + getTownshipName() + "'" +
            "}";
    }
}
