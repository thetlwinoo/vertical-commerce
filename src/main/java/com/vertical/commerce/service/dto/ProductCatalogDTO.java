package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductCatalog} entity.
 */
public class ProductCatalogDTO implements Serializable {
    
    private Long id;


    private Long productCategoryId;

    private String productCategoryName;

    private Long productId;

    private String productName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productsName) {
        this.productName = productsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCatalogDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductCatalogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCatalogDTO{" +
            "id=" + getId() +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
