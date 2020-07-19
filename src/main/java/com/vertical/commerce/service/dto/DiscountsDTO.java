package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Discounts} entity.
 */
public class DiscountsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String discountCode;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long supplierId;

    private String supplierName;

    private Long discountTypeId;

    private String discountTypeName;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(Long discountTypesId) {
        this.discountTypeId = discountTypesId;
    }

    public String getDiscountTypeName() {
        return discountTypeName;
    }

    public void setDiscountTypeName(String discountTypesName) {
        this.discountTypeName = discountTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountsDTO)) {
            return false;
        }

        return id != null && id.equals(((DiscountsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", discountCode='" + getDiscountCode() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", discountTypeId=" + getDiscountTypeId() +
            ", discountTypeName='" + getDiscountTypeName() + "'" +
            "}";
    }
}
