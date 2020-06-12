package com.vertical.commerce.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CreditCardType} entity.
 */
public class CreditCardTypeDTO implements Serializable {
    
    private Long id;

    private String name;

    private Instant modifiedDate;

    
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

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((CreditCardTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
