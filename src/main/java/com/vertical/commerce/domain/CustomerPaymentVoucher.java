package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A CustomerPaymentVoucher.
 */
@Entity
@Table(name = "customer_payment_voucher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPaymentVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "serial_no", nullable = false)
    private String serialNo;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "last_edity_by", nullable = false)
    private String lastEdityBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerPayment customerPayment;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerPaymentVouchers", allowSetters = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public CustomerPaymentVoucher serialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CustomerPaymentVoucher amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLastEdityBy() {
        return lastEdityBy;
    }

    public CustomerPaymentVoucher lastEdityBy(String lastEdityBy) {
        this.lastEdityBy = lastEdityBy;
        return this;
    }

    public void setLastEdityBy(String lastEdityBy) {
        this.lastEdityBy = lastEdityBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CustomerPaymentVoucher lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public CustomerPaymentVoucher customerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
        return this;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CustomerPaymentVoucher currency(Currency currency) {
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
        if (!(o instanceof CustomerPaymentVoucher)) {
            return false;
        }
        return id != null && id.equals(((CustomerPaymentVoucher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentVoucher{" +
            "id=" + getId() +
            ", serialNo='" + getSerialNo() + "'" +
            ", amount=" + getAmount() +
            ", lastEdityBy='" + getLastEdityBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
