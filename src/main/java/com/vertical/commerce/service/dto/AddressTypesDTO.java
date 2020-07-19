package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.vertical.commerce.domain.enumeration.AddressTypeRefer;

/**
 * A DTO for the {@link com.vertical.commerce.domain.AddressTypes} entity.
 */
public class AddressTypesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private AddressTypeRefer refer;

    
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

    public AddressTypeRefer getRefer() {
        return refer;
    }

    public void setRefer(AddressTypeRefer refer) {
        this.refer = refer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressTypesDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressTypesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressTypesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", refer='" + getRefer() + "'" +
            "}";
    }
}
