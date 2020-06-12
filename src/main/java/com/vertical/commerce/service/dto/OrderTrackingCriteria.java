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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.OrderTracking} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.OrderTrackingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-trackings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderTrackingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter carrierTrackingNumber;

    private InstantFilter eventDate;

    private LongFilter orderId;

    private LongFilter trackingEventId;

    public OrderTrackingCriteria() {
    }

    public OrderTrackingCriteria(OrderTrackingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.carrierTrackingNumber = other.carrierTrackingNumber == null ? null : other.carrierTrackingNumber.copy();
        this.eventDate = other.eventDate == null ? null : other.eventDate.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.trackingEventId = other.trackingEventId == null ? null : other.trackingEventId.copy();
    }

    @Override
    public OrderTrackingCriteria copy() {
        return new OrderTrackingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCarrierTrackingNumber() {
        return carrierTrackingNumber;
    }

    public void setCarrierTrackingNumber(StringFilter carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
    }

    public InstantFilter getEventDate() {
        return eventDate;
    }

    public void setEventDate(InstantFilter eventDate) {
        this.eventDate = eventDate;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getTrackingEventId() {
        return trackingEventId;
    }

    public void setTrackingEventId(LongFilter trackingEventId) {
        this.trackingEventId = trackingEventId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderTrackingCriteria that = (OrderTrackingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(carrierTrackingNumber, that.carrierTrackingNumber) &&
            Objects.equals(eventDate, that.eventDate) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(trackingEventId, that.trackingEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        carrierTrackingNumber,
        eventDate,
        orderId,
        trackingEventId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderTrackingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (carrierTrackingNumber != null ? "carrierTrackingNumber=" + carrierTrackingNumber + ", " : "") +
                (eventDate != null ? "eventDate=" + eventDate + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (trackingEventId != null ? "trackingEventId=" + trackingEventId + ", " : "") +
            "}";
    }

}
