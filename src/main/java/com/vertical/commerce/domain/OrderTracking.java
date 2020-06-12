package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A OrderTracking.
 */
@Entity
@Table(name = "order_tracking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "carrier_tracking_number")
    private String carrierTrackingNumber;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "event_details", nullable = false)
    private String eventDetails;

    @NotNull
    @Column(name = "event_date", nullable = false)
    private Instant eventDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Orders order;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderTrackings", allowSetters = true)
    private TrackingEvent trackingEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrierTrackingNumber() {
        return carrierTrackingNumber;
    }

    public OrderTracking carrierTrackingNumber(String carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
        return this;
    }

    public void setCarrierTrackingNumber(String carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public OrderTracking eventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
        return this;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public OrderTracking eventDate(Instant eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public Orders getOrder() {
        return order;
    }

    public OrderTracking order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }

    public TrackingEvent getTrackingEvent() {
        return trackingEvent;
    }

    public OrderTracking trackingEvent(TrackingEvent trackingEvent) {
        this.trackingEvent = trackingEvent;
        return this;
    }

    public void setTrackingEvent(TrackingEvent trackingEvent) {
        this.trackingEvent = trackingEvent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTracking)) {
            return false;
        }
        return id != null && id.equals(((OrderTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderTracking{" +
            "id=" + getId() +
            ", carrierTrackingNumber='" + getCarrierTrackingNumber() + "'" +
            ", eventDetails='" + getEventDetails() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            "}";
    }
}
