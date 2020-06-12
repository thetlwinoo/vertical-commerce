package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductSetDetailPrice} entity.
 */
public class ProductSetDetailPriceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer startCount;

    private Integer endCount;

    private Integer multiplyCount;

    @NotNull
    private Instant startDate;

    private Instant endDate;

    @NotNull
    private Instant modifiedDate;


    private Long productSetDetailId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Integer getMultiplyCount() {
        return multiplyCount;
    }

    public void setMultiplyCount(Integer multiplyCount) {
        this.multiplyCount = multiplyCount;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getProductSetDetailId() {
        return productSetDetailId;
    }

    public void setProductSetDetailId(Long productSetDetailsId) {
        this.productSetDetailId = productSetDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSetDetailPriceDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductSetDetailPriceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSetDetailPriceDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", startCount=" + getStartCount() +
            ", endCount=" + getEndCount() +
            ", multiplyCount=" + getMultiplyCount() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", productSetDetailId=" + getProductSetDetailId() +
            "}";
    }
}
