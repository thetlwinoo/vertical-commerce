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
 * A ShippingFeeChart.
 */
@Entity
@Table(name = "shipping_fee_chart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShippingFeeChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "size_of_percel", nullable = false)
    private String sizeOfPercel;

    @NotNull
    @Column(name = "min_volume_weight", nullable = false)
    private Integer minVolumeWeight;

    @NotNull
    @Column(name = "max_volume_weight", nullable = false)
    private Integer maxVolumeWeight;

    @NotNull
    @Column(name = "min_actual_weight", nullable = false)
    private Integer minActualWeight;

    @NotNull
    @Column(name = "max_actual_weight", nullable = false)
    private Integer maxActualWeight;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties(value = "shippingFeeCharts", allowSetters = true)
    private Towns sourceTown;

    @ManyToOne
    @JsonIgnoreProperties(value = "shippingFeeCharts", allowSetters = true)
    private Towns destinationTown;

    @ManyToOne
    @JsonIgnoreProperties(value = "shippingFeeCharts", allowSetters = true)
    private DeliveryMethods deliveryMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizeOfPercel() {
        return sizeOfPercel;
    }

    public ShippingFeeChart sizeOfPercel(String sizeOfPercel) {
        this.sizeOfPercel = sizeOfPercel;
        return this;
    }

    public void setSizeOfPercel(String sizeOfPercel) {
        this.sizeOfPercel = sizeOfPercel;
    }

    public Integer getMinVolumeWeight() {
        return minVolumeWeight;
    }

    public ShippingFeeChart minVolumeWeight(Integer minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
        return this;
    }

    public void setMinVolumeWeight(Integer minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
    }

    public Integer getMaxVolumeWeight() {
        return maxVolumeWeight;
    }

    public ShippingFeeChart maxVolumeWeight(Integer maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
        return this;
    }

    public void setMaxVolumeWeight(Integer maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
    }

    public Integer getMinActualWeight() {
        return minActualWeight;
    }

    public ShippingFeeChart minActualWeight(Integer minActualWeight) {
        this.minActualWeight = minActualWeight;
        return this;
    }

    public void setMinActualWeight(Integer minActualWeight) {
        this.minActualWeight = minActualWeight;
    }

    public Integer getMaxActualWeight() {
        return maxActualWeight;
    }

    public ShippingFeeChart maxActualWeight(Integer maxActualWeight) {
        this.maxActualWeight = maxActualWeight;
        return this;
    }

    public void setMaxActualWeight(Integer maxActualWeight) {
        this.maxActualWeight = maxActualWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ShippingFeeChart price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public ShippingFeeChart lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public ShippingFeeChart lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Towns getSourceTown() {
        return sourceTown;
    }

    public ShippingFeeChart sourceTown(Towns towns) {
        this.sourceTown = towns;
        return this;
    }

    public void setSourceTown(Towns towns) {
        this.sourceTown = towns;
    }

    public Towns getDestinationTown() {
        return destinationTown;
    }

    public ShippingFeeChart destinationTown(Towns towns) {
        this.destinationTown = towns;
        return this;
    }

    public void setDestinationTown(Towns towns) {
        this.destinationTown = towns;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public ShippingFeeChart deliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
        return this;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingFeeChart)) {
            return false;
        }
        return id != null && id.equals(((ShippingFeeChart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingFeeChart{" +
            "id=" + getId() +
            ", sizeOfPercel='" + getSizeOfPercel() + "'" +
            ", minVolumeWeight=" + getMinVolumeWeight() +
            ", maxVolumeWeight=" + getMaxVolumeWeight() +
            ", minActualWeight=" + getMinActualWeight() +
            ", maxActualWeight=" + getMaxActualWeight() +
            ", price=" + getPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
