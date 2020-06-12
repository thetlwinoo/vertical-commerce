package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CustomerPaymentCreditCardExtended.
 */
@Entity
@Table(name = "customer_payment_credit_card_extended")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPaymentCreditCardExtended implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "error_code", nullable = false)
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edite_when", nullable = false)
    private Instant lastEditeWhen;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public CustomerPaymentCreditCardExtended errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CustomerPaymentCreditCardExtended errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CustomerPaymentCreditCardExtended lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditeWhen() {
        return lastEditeWhen;
    }

    public CustomerPaymentCreditCardExtended lastEditeWhen(Instant lastEditeWhen) {
        this.lastEditeWhen = lastEditeWhen;
        return this;
    }

    public void setLastEditeWhen(Instant lastEditeWhen) {
        this.lastEditeWhen = lastEditeWhen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentCreditCardExtended)) {
            return false;
        }
        return id != null && id.equals(((CustomerPaymentCreditCardExtended) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentCreditCardExtended{" +
            "id=" + getId() +
            ", errorCode='" + getErrorCode() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditeWhen='" + getLastEditeWhen() + "'" +
            "}";
    }
}
