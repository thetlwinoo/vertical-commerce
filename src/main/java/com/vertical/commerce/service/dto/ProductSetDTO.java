package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductSet} entity.
 */
public class ProductSetDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer noOfPerson;

    @NotNull
    private Boolean isExclusive;

    @NotNull
    private Instant modifinedDate;

    
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

    public Integer getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(Integer noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public Boolean isIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(Boolean isExclusive) {
        this.isExclusive = isExclusive;
    }

    public Instant getModifinedDate() {
        return modifinedDate;
    }

    public void setModifinedDate(Instant modifinedDate) {
        this.modifinedDate = modifinedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSetDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductSetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", noOfPerson=" + getNoOfPerson() +
            ", isExclusive='" + isIsExclusive() + "'" +
            ", modifinedDate='" + getModifinedDate() + "'" +
            "}";
    }
}
