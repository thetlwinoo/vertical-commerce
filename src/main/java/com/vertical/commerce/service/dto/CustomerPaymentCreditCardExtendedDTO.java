package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CustomerPaymentCreditCardExtended} entity.
 */
public class CustomerPaymentCreditCardExtendedDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String errorCode;

    private String errorMessage;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditeWhen;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditeWhen() {
        return lastEditeWhen;
    }

    public void setLastEditeWhen(Instant lastEditeWhen) {
        this.lastEditeWhen = lastEditeWhen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentCreditCardExtendedDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerPaymentCreditCardExtendedDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentCreditCardExtendedDTO{" +
            "id=" + getId() +
            ", errorCode='" + getErrorCode() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditeWhen='" + getLastEditeWhen() + "'" +
            "}";
    }
}
