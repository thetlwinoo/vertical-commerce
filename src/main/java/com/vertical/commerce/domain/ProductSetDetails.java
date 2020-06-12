package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ProductSetDetails.
 */
@Entity
@Table(name = "product_set_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductSetDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sub_group_no")
    private Integer subGroupNo;

    @Column(name = "sub_group_min_count")
    private Integer subGroupMinCount;

    @NotNull
    @Column(name = "sub_group_min_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal subGroupMinTotal;

    @Column(name = "min_count")
    private Integer minCount;

    @Column(name = "max_count")
    private Integer maxCount;

    @Column(name = "is_optional")
    private Boolean isOptional;

    @ManyToOne
    @JsonIgnoreProperties(value = "productSetDetails", allowSetters = true)
    private ProductSet productSet;

    @ManyToOne
    @JsonIgnoreProperties(value = "productSetDetails", allowSetters = true)
    private Products product;

    @ManyToOne
    @JsonIgnoreProperties(value = "productSetDetails", allowSetters = true)
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSubGroupNo() {
        return subGroupNo;
    }

    public ProductSetDetails subGroupNo(Integer subGroupNo) {
        this.subGroupNo = subGroupNo;
        return this;
    }

    public void setSubGroupNo(Integer subGroupNo) {
        this.subGroupNo = subGroupNo;
    }

    public Integer getSubGroupMinCount() {
        return subGroupMinCount;
    }

    public ProductSetDetails subGroupMinCount(Integer subGroupMinCount) {
        this.subGroupMinCount = subGroupMinCount;
        return this;
    }

    public void setSubGroupMinCount(Integer subGroupMinCount) {
        this.subGroupMinCount = subGroupMinCount;
    }

    public BigDecimal getSubGroupMinTotal() {
        return subGroupMinTotal;
    }

    public ProductSetDetails subGroupMinTotal(BigDecimal subGroupMinTotal) {
        this.subGroupMinTotal = subGroupMinTotal;
        return this;
    }

    public void setSubGroupMinTotal(BigDecimal subGroupMinTotal) {
        this.subGroupMinTotal = subGroupMinTotal;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public ProductSetDetails minCount(Integer minCount) {
        this.minCount = minCount;
        return this;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public ProductSetDetails maxCount(Integer maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Boolean isIsOptional() {
        return isOptional;
    }

    public ProductSetDetails isOptional(Boolean isOptional) {
        this.isOptional = isOptional;
        return this;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public ProductSet getProductSet() {
        return productSet;
    }

    public ProductSetDetails productSet(ProductSet productSet) {
        this.productSet = productSet;
        return this;
    }

    public void setProductSet(ProductSet productSet) {
        this.productSet = productSet;
    }

    public Products getProduct() {
        return product;
    }

    public ProductSetDetails product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductSetDetails productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSetDetails)) {
            return false;
        }
        return id != null && id.equals(((ProductSetDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSetDetails{" +
            "id=" + getId() +
            ", subGroupNo=" + getSubGroupNo() +
            ", subGroupMinCount=" + getSubGroupMinCount() +
            ", subGroupMinTotal=" + getSubGroupMinTotal() +
            ", minCount=" + getMinCount() +
            ", maxCount=" + getMaxCount() +
            ", isOptional='" + isIsOptional() + "'" +
            "}";
    }
}
