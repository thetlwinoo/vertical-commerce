package com.vertical.commerce.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Cards} entity.
 */
public class CardsDTO implements Serializable {
    
    private Long id;

    private String number;

    private Instant modifiedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        if (!(o instanceof CardsDTO)) {
            return false;
        }

        return id != null && id.equals(((CardsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardsDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
