package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.ShippingFeeChart} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ShippingFeeChartResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /shipping-fee-charts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShippingFeeChartCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter sizeOfPercel;

    private IntegerFilter minVolumeWeight;

    private IntegerFilter maxVolumeWeight;

    private IntegerFilter minActualWeight;

    private IntegerFilter maxActualWeight;

    private BigDecimalFilter price;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter zoneId;

    private LongFilter deliveryMethodId;

    public ShippingFeeChartCriteria() {
    }

    public ShippingFeeChartCriteria(ShippingFeeChartCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sizeOfPercel = other.sizeOfPercel == null ? null : other.sizeOfPercel.copy();
        this.minVolumeWeight = other.minVolumeWeight == null ? null : other.minVolumeWeight.copy();
        this.maxVolumeWeight = other.maxVolumeWeight == null ? null : other.maxVolumeWeight.copy();
        this.minActualWeight = other.minActualWeight == null ? null : other.minActualWeight.copy();
        this.maxActualWeight = other.maxActualWeight == null ? null : other.maxActualWeight.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.zoneId = other.zoneId == null ? null : other.zoneId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
    }

    @Override
    public ShippingFeeChartCriteria copy() {
        return new ShippingFeeChartCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSizeOfPercel() {
        return sizeOfPercel;
    }

    public void setSizeOfPercel(StringFilter sizeOfPercel) {
        this.sizeOfPercel = sizeOfPercel;
    }

    public IntegerFilter getMinVolumeWeight() {
        return minVolumeWeight;
    }

    public void setMinVolumeWeight(IntegerFilter minVolumeWeight) {
        this.minVolumeWeight = minVolumeWeight;
    }

    public IntegerFilter getMaxVolumeWeight() {
        return maxVolumeWeight;
    }

    public void setMaxVolumeWeight(IntegerFilter maxVolumeWeight) {
        this.maxVolumeWeight = maxVolumeWeight;
    }

    public IntegerFilter getMinActualWeight() {
        return minActualWeight;
    }

    public void setMinActualWeight(IntegerFilter minActualWeight) {
        this.minActualWeight = minActualWeight;
    }

    public IntegerFilter getMaxActualWeight() {
        return maxActualWeight;
    }

    public void setMaxActualWeight(IntegerFilter maxActualWeight) {
        this.maxActualWeight = maxActualWeight;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShippingFeeChartCriteria that = (ShippingFeeChartCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sizeOfPercel, that.sizeOfPercel) &&
            Objects.equals(minVolumeWeight, that.minVolumeWeight) &&
            Objects.equals(maxVolumeWeight, that.maxVolumeWeight) &&
            Objects.equals(minActualWeight, that.minActualWeight) &&
            Objects.equals(maxActualWeight, that.maxActualWeight) &&
            Objects.equals(price, that.price) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(zoneId, that.zoneId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sizeOfPercel,
        minVolumeWeight,
        maxVolumeWeight,
        minActualWeight,
        maxActualWeight,
        price,
        lastEditedBy,
        lastEditedWhen,
        zoneId,
        deliveryMethodId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingFeeChartCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sizeOfPercel != null ? "sizeOfPercel=" + sizeOfPercel + ", " : "") +
                (minVolumeWeight != null ? "minVolumeWeight=" + minVolumeWeight + ", " : "") +
                (maxVolumeWeight != null ? "maxVolumeWeight=" + maxVolumeWeight + ", " : "") +
                (minActualWeight != null ? "minActualWeight=" + minActualWeight + ", " : "") +
                (maxActualWeight != null ? "maxActualWeight=" + maxActualWeight + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
            "}";
    }

}
