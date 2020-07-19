package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Materials} entity.
 */
public class MaterialsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @Lob
    private String cultureDetails;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialsDTO)) {
            return false;
        }

        return id != null && id.equals(((MaterialsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            "}";
    }
}
