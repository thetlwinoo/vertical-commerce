package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductSetDetails} entity.
 */
public class ProductSetDetailsDTO implements Serializable {
    
    private Long id;

    private Integer subGroupNo;

    private Integer subGroupMinCount;

    @NotNull
    private BigDecimal subGroupMinTotal;

    private Integer minCount;

    private Integer maxCount;

    private Boolean isOptional;


    private Long productSetId;

    private String productSetName;

    private Long productId;

    private String productName;

    private Long productCategoryId;

    private String productCategoryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSubGroupNo() {
        return subGroupNo;
    }

    public void setSubGroupNo(Integer subGroupNo) {
        this.subGroupNo = subGroupNo;
    }

    public Integer getSubGroupMinCount() {
        return subGroupMinCount;
    }

    public void setSubGroupMinCount(Integer subGroupMinCount) {
        this.subGroupMinCount = subGroupMinCount;
    }

    public BigDecimal getSubGroupMinTotal() {
        return subGroupMinTotal;
    }

    public void setSubGroupMinTotal(BigDecimal subGroupMinTotal) {
        this.subGroupMinTotal = subGroupMinTotal;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Boolean isIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Long getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(Long productSetId) {
        this.productSetId = productSetId;
    }

    public String getProductSetName() {
        return productSetName;
    }

    public void setProductSetName(String productSetName) {
        this.productSetName = productSetName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSetDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductSetDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSetDetailsDTO{" +
            "id=" + getId() +
            ", subGroupNo=" + getSubGroupNo() +
            ", subGroupMinCount=" + getSubGroupMinCount() +
            ", subGroupMinTotal=" + getSubGroupMinTotal() +
            ", minCount=" + getMinCount() +
            ", maxCount=" + getMaxCount() +
            ", isOptional='" + isIsOptional() + "'" +
            ", productSetId=" + getProductSetId() +
            ", productSetName='" + getProductSetName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            "}";
    }
}
