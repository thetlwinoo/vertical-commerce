package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductCategoryLocalize.
 */
@Entity
@Table(name = "product_category_localize")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategoryLocalize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategoryLocalizes", allowSetters = true)
    private Culture culture;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategoryLocalizes", allowSetters = true)
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductCategoryLocalize name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Culture getCulture() {
        return culture;
    }

    public ProductCategoryLocalize culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductCategoryLocalize productCategory(ProductCategory productCategory) {
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
        if (!(o instanceof ProductCategoryLocalize)) {
            return false;
        }
        return id != null && id.equals(((ProductCategoryLocalize) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryLocalize{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
