package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.UploadActionTypes} entity.
 */
public class UploadActionTypesDTO implements Serializable {
    
    private Long id;

    private String name;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadActionTypesDTO)) {
            return false;
        }

        return id != null && id.equals(((UploadActionTypesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UploadActionTypesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
