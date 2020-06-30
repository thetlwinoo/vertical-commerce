package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Tax} entity.
 */
public class TaxDTO implements Serializable {
    
    private Long id;

    private String code;

    @NotNull
    private String name;

    @NotNull
    private Double rate;

    @NotNull
    private Instant modifiedDate;


    private Long taxClassId;

    private String taxClassName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(Long taxClassId) {
        this.taxClassId = taxClassId;
    }

    public String getTaxClassName() {
        return taxClassName;
    }

    public void setTaxClassName(String taxClassName) {
        this.taxClassName = taxClassName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxDTO)) {
            return false;
        }

        return id != null && id.equals(((TaxDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", rate=" + getRate() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", taxClassId=" + getTaxClassId() +
            ", taxClassName='" + getTaxClassName() + "'" +
            "}";
    }
}
