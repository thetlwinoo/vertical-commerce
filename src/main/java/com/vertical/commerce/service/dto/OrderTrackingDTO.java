package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.OrderTracking} entity.
 */
public class OrderTrackingDTO implements Serializable {
    
    private Long id;

    private String carrierTrackingNumber;

    
    @Lob
    private String eventDetails;

    @NotNull
    private Instant eventDate;


    private Long orderId;

    private Long trackingEventId;

    private String trackingEventName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrierTrackingNumber() {
        return carrierTrackingNumber;
    }

    public void setCarrierTrackingNumber(String carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long ordersId) {
        this.orderId = ordersId;
    }

    public Long getTrackingEventId() {
        return trackingEventId;
    }

    public void setTrackingEventId(Long trackingEventId) {
        this.trackingEventId = trackingEventId;
    }

    public String getTrackingEventName() {
        return trackingEventName;
    }

    public void setTrackingEventName(String trackingEventName) {
        this.trackingEventName = trackingEventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTrackingDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderTrackingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderTrackingDTO{" +
            "id=" + getId() +
            ", carrierTrackingNumber='" + getCarrierTrackingNumber() + "'" +
            ", eventDetails='" + getEventDetails() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", orderId=" + getOrderId() +
            ", trackingEventId=" + getTrackingEventId() +
            ", trackingEventName='" + getTrackingEventName() + "'" +
            "}";
    }
}
