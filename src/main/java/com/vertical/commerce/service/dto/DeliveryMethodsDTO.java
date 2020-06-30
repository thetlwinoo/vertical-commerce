package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.DeliveryMethods} entity.
 */
public class DeliveryMethodsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String thirdPartyName;

    private Integer expectedMinArrivalDays;

    private Integer expectedMaxArrivalDays;

    private Boolean activeInd;

    private Boolean defaultInd;

    private String deliveryNote;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public Integer getExpectedMinArrivalDays() {
        return expectedMinArrivalDays;
    }

    public void setExpectedMinArrivalDays(Integer expectedMinArrivalDays) {
        this.expectedMinArrivalDays = expectedMinArrivalDays;
    }

    public Integer getExpectedMaxArrivalDays() {
        return expectedMaxArrivalDays;
    }

    public void setExpectedMaxArrivalDays(Integer expectedMaxArrivalDays) {
        this.expectedMaxArrivalDays = expectedMaxArrivalDays;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryMethodsDTO)) {
            return false;
        }

        return id != null && id.equals(((DeliveryMethodsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryMethodsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", thirdPartyName='" + getThirdPartyName() + "'" +
            ", expectedMinArrivalDays=" + getExpectedMinArrivalDays() +
            ", expectedMaxArrivalDays=" + getExpectedMaxArrivalDays() +
            ", activeInd='" + isActiveInd() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", deliveryNote='" + getDeliveryNote() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
