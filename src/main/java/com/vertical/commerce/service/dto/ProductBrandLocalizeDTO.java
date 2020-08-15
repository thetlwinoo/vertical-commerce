package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductBrandLocalize} entity.
 */
public class ProductBrandLocalizeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long productBrandId;

    private String productBrandName;
    
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

    public Long getCultureId() {
        return cultureId;
    }

    public void setCultureId(Long cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureCode() {
        return cultureCode;
    }

    public void setCultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
    }

    public Long getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(Long productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductBrandLocalizeDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductBrandLocalizeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductBrandLocalizeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", productBrandId=" + getProductBrandId() +
            ", productBrandName='" + getProductBrandName() + "'" +
            "}";
    }
}
