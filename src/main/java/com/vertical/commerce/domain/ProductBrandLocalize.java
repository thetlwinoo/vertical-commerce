package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductBrandLocalize.
 */
@Entity
@Table(name = "product_brand_localize")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductBrandLocalize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "productBrandLocalizes", allowSetters = true)
    private Culture culture;

    @ManyToOne
    @JsonIgnoreProperties(value = "productBrandLocalizes", allowSetters = true)
    private ProductBrand productBrand;

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

    public ProductBrandLocalize name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Culture getCulture() {
        return culture;
    }

    public ProductBrandLocalize culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public ProductBrandLocalize productBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
        return this;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductBrandLocalize)) {
            return false;
        }
        return id != null && id.equals(((ProductBrandLocalize) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductBrandLocalize{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
