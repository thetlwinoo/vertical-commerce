package com.vertical.commerce.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CardTypes} entity.
 */
public class CardTypesDTO implements Serializable {
    
    private Long id;

    private String name;

    private Long issuerId;

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

    public Long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Long issuerId) {
        this.issuerId = issuerId;
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
        if (!(o instanceof CardTypesDTO)) {
            return false;
        }

        return id != null && id.equals(((CardTypesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardTypesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", issuerId=" + getIssuerId() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
