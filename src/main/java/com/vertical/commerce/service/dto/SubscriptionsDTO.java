package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Subscriptions} entity.
 */
public class SubscriptionsDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String emailAddress;

    @NotNull
    private Instant subscribedOn;

    @NotNull
    private Instant validFrom;

    private Instant validTo;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Instant getSubscribedOn() {
        return subscribedOn;
    }

    public void setSubscribedOn(Instant subscribedOn) {
        this.subscribedOn = subscribedOn;
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
        if (!(o instanceof SubscriptionsDTO)) {
            return false;
        }

        return id != null && id.equals(((SubscriptionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionsDTO{" +
            "id=" + getId() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", subscribedOn='" + getSubscribedOn() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
