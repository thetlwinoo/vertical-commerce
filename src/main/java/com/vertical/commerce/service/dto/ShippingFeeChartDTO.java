package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ShippingFeeChart} entity.
 */
public class ShippingFeeChartDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String sizeOfPercel;

    @NotNull
    private Integer minVolumeWeight;

    @NotNull
    private Integer maxVolumeWeight;

    @NotNull
    private Integer minActualWeight;

    @NotNull
    private Integer maxActualWeight;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long sourceTownshipId;

    private String sourceTownshipName;

    private Long destinationTownshipId;

    private String destinationTownshipName;

    private Long deliveryMethodId;

    private String deliveryMethodName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizeOfPercel() {
        return sizeOfPercel;
    }

    public void setSizeOfPercel(String sizeOfPercel) {
        this.sizeOfPercel = sizeOfPercel;
    }

    public Integer getMinVolumeWeight() {
        return minVolumeWeight;
    }

    public void setMinVolumeWeight(Integer minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
    }

    public Integer getMaxVolumeWeight() {
        return maxVolumeWeight;
    }

    public void setMaxVolumeWeight(Integer maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
    }

    public Integer getMinActualWeight() {
        return minActualWeight;
    }

    public void setMinActualWeight(Integer minActualWeight) {
        this.minActualWeight = minActualWeight;
    }

    public Integer getMaxActualWeight() {
        return maxActualWeight;
    }

    public void setMaxActualWeight(Integer maxActualWeight) {
        this.maxActualWeight = maxActualWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getSourceTownshipId() {
        return sourceTownshipId;
    }

    public void setSourceTownshipId(Long townshipsId) {
        this.sourceTownshipId = townshipsId;
    }

    public String getSourceTownshipName() {
        return sourceTownshipName;
    }

    public void setSourceTownshipName(String townshipsName) {
        this.sourceTownshipName = townshipsName;
    }

    public Long getDestinationTownshipId() {
        return destinationTownshipId;
    }

    public void setDestinationTownshipId(Long townshipsId) {
        this.destinationTownshipId = townshipsId;
    }

    public String getDestinationTownshipName() {
        return destinationTownshipName;
    }

    public void setDestinationTownshipName(String townshipsName) {
        this.destinationTownshipName = townshipsName;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodsId) {
        this.deliveryMethodId = deliveryMethodsId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodsName) {
        this.deliveryMethodName = deliveryMethodsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingFeeChartDTO)) {
            return false;
        }

        return id != null && id.equals(((ShippingFeeChartDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingFeeChartDTO{" +
            "id=" + getId() +
            ", sizeOfPercel='" + getSizeOfPercel() + "'" +
            ", minVolumeWeight=" + getMinVolumeWeight() +
            ", maxVolumeWeight=" + getMaxVolumeWeight() +
            ", minActualWeight=" + getMinActualWeight() +
            ", maxActualWeight=" + getMaxActualWeight() +
            ", price=" + getPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", sourceTownshipId=" + getSourceTownshipId() +
            ", sourceTownshipName='" + getSourceTownshipName() + "'" +
            ", destinationTownshipId=" + getDestinationTownshipId() +
            ", destinationTownshipName='" + getDestinationTownshipName() + "'" +
            ", deliveryMethodId=" + getDeliveryMethodId() +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            "}";
    }
}
