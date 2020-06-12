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
 * A ProductSetDetailPrice.
 */
@Entity
@Table(name = "product_set_detail_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductSetDetailPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "start_count", nullable = false)
    private Integer startCount;

    @Column(name = "end_count")
    private Integer endCount;

    @Column(name = "multiply_count")
    private Integer multiplyCount;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "productSetDetailPrices", allowSetters = true)
    private ProductSetDetails productSetDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductSetDetailPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public ProductSetDetailPrice startCount(Integer startCount) {
        this.startCount = startCount;
        return this;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public ProductSetDetailPrice endCount(Integer endCount) {
        this.endCount = endCount;
        return this;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Integer getMultiplyCount() {
        return multiplyCount;
    }

    public ProductSetDetailPrice multiplyCount(Integer multiplyCount) {
        this.multiplyCount = multiplyCount;
        return this;
    }

    public void setMultiplyCount(Integer multiplyCount) {
        this.multiplyCount = multiplyCount;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ProductSetDetailPrice startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ProductSetDetailPrice endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public ProductSetDetailPrice modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ProductSetDetails getProductSetDetail() {
        return productSetDetail;
    }

    public ProductSetDetailPrice productSetDetail(ProductSetDetails productSetDetails) {
        this.productSetDetail = productSetDetails;
        return this;
    }

    public void setProductSetDetail(ProductSetDetails productSetDetails) {
        this.productSetDetail = productSetDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSetDetailPrice)) {
            return false;
        }
        return id != null && id.equals(((ProductSetDetailPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSetDetailPrice{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", startCount=" + getStartCount() +
            ", endCount=" + getEndCount() +
            ", multiplyCount=" + getMultiplyCount() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
