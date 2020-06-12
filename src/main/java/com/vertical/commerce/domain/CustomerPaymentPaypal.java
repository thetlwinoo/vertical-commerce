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
 * A CustomerPaymentPaypal.
 */
@Entity
@Table(name = "customer_payment_paypal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPaymentPaypal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "paypal_account", nullable = false)
    private String paypalAccount;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

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
    @JsonIgnoreProperties(value = "customerPaymentPaypals", allowSetters = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalAccount() {
        return paypalAccount;
    }

    public CustomerPaymentPaypal paypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
        return this;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CustomerPaymentPaypal amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public CustomerPaymentPaypal responseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public CustomerPaymentPaypal approvalCode(String approvalCode) {
        this.approvalCode = approvalCode;
        return this;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public CustomerPaymentPaypal responseData(String responseData) {
        this.responseData = responseData;
        return this;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CustomerPaymentPaypal lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CustomerPaymentPaypal lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public CustomerPaymentPaypal customerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
        return this;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CustomerPaymentPaypal currency(Currency currency) {
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
        if (!(o instanceof CustomerPaymentPaypal)) {
            return false;
        }
        return id != null && id.equals(((CustomerPaymentPaypal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentPaypal{" +
            "id=" + getId() +
            ", paypalAccount='" + getPaypalAccount() + "'" +
            ", amount=" + getAmount() +
            ", responseCode='" + getResponseCode() + "'" +
            ", approvalCode='" + getApprovalCode() + "'" +
            ", responseData='" + getResponseData() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
