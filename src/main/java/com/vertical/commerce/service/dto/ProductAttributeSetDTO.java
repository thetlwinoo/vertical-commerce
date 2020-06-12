package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductAttributeSet} entity.
 */
public class ProductAttributeSetDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long productOptionSetId;

    private String productOptionSetValue;
    
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

    public Long getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(Long productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public String getProductOptionSetValue() {
        return productOptionSetValue;
    }

    public void setProductOptionSetValue(String productOptionSetValue) {
        this.productOptionSetValue = productOptionSetValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAttributeSetDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductAttributeSetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductAttributeSetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", productOptionSetId=" + getProductOptionSetId() +
            ", productOptionSetValue='" + getProductOptionSetValue() + "'" +
            "}";
    }
}
