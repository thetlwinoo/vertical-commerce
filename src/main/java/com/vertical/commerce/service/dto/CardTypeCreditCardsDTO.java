package com.vertical.commerce.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CardTypeCreditCards} entity.
 */
public class CardTypeCreditCardsDTO implements Serializable {
    
    private Long id;

    private String name;

    private Integer startNumber;

    private Integer endNumber;

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

    public Integer getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
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
        if (!(o instanceof CardTypeCreditCardsDTO)) {
            return false;
        }

        return id != null && id.equals(((CardTypeCreditCardsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardTypeCreditCardsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startNumber=" + getStartNumber() +
            ", endNumber=" + getEndNumber() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
