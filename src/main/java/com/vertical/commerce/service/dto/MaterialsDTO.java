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
    private String localization;

    
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

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
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
            ", localization='" + getLocalization() + "'" +
            "}";
    }
}
