package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A ProductListPriceHistory.
 */
@Entity
@Table(name = "product_list_price_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductListPriceHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "list_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal listPrice;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "productListPriceHistories", allowSetters = true)
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ProductListPriceHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ProductListPriceHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public ProductListPriceHistory listPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
        return this;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public ProductListPriceHistory modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Products getProduct() {
        return product;
    }

    public ProductListPriceHistory product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductListPriceHistory)) {
            return false;
        }
        return id != null && id.equals(((ProductListPriceHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductListPriceHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", listPrice=" + getListPrice() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
