package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A CustomerPaymentCreditCard.
 */
@Entity
@Table(name = "customer_payment_credit_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPaymentCreditCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumber;

    @NotNull
    @Column(name = "credit_card_expiry_month", nullable = false)
    private Integer creditCardExpiryMonth;

    @NotNull
    @Column(name = "credit_card_expiry_year", nullable = false)
    private Integer creditCardExpiryYear;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "batch_id", nullable = false)
    private String batchId;

    @NotNull
    @Column(name = "response_code", nullable = false)
    private String responseCode;

    @NotNull
    @Column(name = "approval_code", nullable = false)
    private String approvalCode;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "response_data")
    private String responseData;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerPayment customerPayment;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerPaymentCreditCards", allowSetters = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public CustomerPaymentCreditCard creditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Integer getCreditCardExpiryMonth() {
        return creditCardExpiryMonth;
    }

    public CustomerPaymentCreditCard creditCardExpiryMonth(Integer creditCardExpiryMonth) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
        return this;
    }

    public void setCreditCardExpiryMonth(Integer creditCardExpiryMonth) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public Integer getCreditCardExpiryYear() {
        return creditCardExpiryYear;
    }

    public CustomerPaymentCreditCard creditCardExpiryYear(Integer creditCardExpiryYear) {
        this.creditCardExpiryYear = creditCardExpiryYear;
        return this;
    }

    public void setCreditCardExpiryYear(Integer creditCardExpiryYear) {
        this.creditCardExpiryYear = creditCardExpiryYear;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CustomerPaymentCreditCard amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBatchId() {
        return batchId;
    }

    public CustomerPaymentCreditCard batchId(String batchId) {
        this.batchId = batchId;
        return this;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public CustomerPaymentCreditCard responseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public CustomerPaymentCreditCard approvalCode(String approvalCode) {
        this.approvalCode = approvalCode;
        return this;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public CustomerPaymentCreditCard responseData(String responseData) {
        this.responseData = responseData;
        return this;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CustomerPaymentCreditCard lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CustomerPaymentCreditCard lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public CustomerPaymentCreditCard customerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
        return this;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CustomerPaymentCreditCard currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentCreditCard)) {
            return false;
        }
        return id != null && id.equals(((CustomerPaymentCreditCard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentCreditCard{" +
            "id=" + getId() +
            ", creditCardNumber='" + getCreditCardNumber() + "'" +
            ", creditCardExpiryMonth=" + getCreditCardExpiryMonth() +
            ", creditCardExpiryYear=" + getCreditCardExpiryYear() +
            ", amount=" + getAmount() +
            ", batchId='" + getBatchId() + "'" +
            ", responseCode='" + getResponseCode() + "'" +
            ", approvalCode='" + getApprovalCode() + "'" +
            ", responseData='" + getResponseData() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
