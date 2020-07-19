package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.PersonEmailAddress} entity.
 */
public class PersonEmailAddressDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String emailAddress;

    private Boolean defaultInd;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long personId;

    private String personFullName;
    
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

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String peopleFullName) {
        this.personFullName = peopleFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonEmailAddressDTO)) {
            return false;
        }

        return id != null && id.equals(((PersonEmailAddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonEmailAddressDTO{" +
            "id=" + getId() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", personId=" + getPersonId() +
            ", personFullName='" + getPersonFullName() + "'" +
            "}";
    }
}
