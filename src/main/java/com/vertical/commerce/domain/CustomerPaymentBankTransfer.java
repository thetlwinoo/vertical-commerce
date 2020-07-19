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
 * A CustomerPaymentBankTransfer.
 */
@Entity
@Table(name = "customer_payment_bank_transfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPaymentBankTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "receipt_photo", nullable = false)
    private String receiptPhoto;

    @NotNull
    @Column(name = "name_in_bank_account", nullable = false)
    private String nameInBankAccount;

    @NotNull
    @Column(name = "date_of_transfer", nullable = false)
    private Instant dateOfTransfer;

    @NotNull
    @Column(name = "amount_transferred", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountTransferred;

    @Column(name = "bank_name")
    private String bankName;

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
    @JsonIgnoreProperties(value = "customerPaymentBankTransfers", allowSetters = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiptPhoto() {
        return receiptPhoto;
    }

    public CustomerPaymentBankTransfer receiptPhoto(String receiptPhoto) {
        this.receiptPhoto = receiptPhoto;
        return this;
    }

    public void setReceiptPhoto(String receiptPhoto) {
        this.receiptPhoto = receiptPhoto;
    }

    public String getNameInBankAccount() {
        return nameInBankAccount;
    }

    public CustomerPaymentBankTransfer nameInBankAccount(String nameInBankAccount) {
        this.nameInBankAccount = nameInBankAccount;
        return this;
    }

    public void setNameInBankAccount(String nameInBankAccount) {
        this.nameInBankAccount = nameInBankAccount;
    }

    public Instant getDateOfTransfer() {
        return dateOfTransfer;
    }

    public CustomerPaymentBankTransfer dateOfTransfer(Instant dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
        return this;
    }

    public void setDateOfTransfer(Instant dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    public BigDecimal getAmountTransferred() {
        return amountTransferred;
    }

    public CustomerPaymentBankTransfer amountTransferred(BigDecimal amountTransferred) {
        this.amountTransferred = amountTransferred;
        return this;
    }

    public void setAmountTransferred(BigDecimal amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public String getBankName() {
        return bankName;
    }

    public CustomerPaymentBankTransfer bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CustomerPaymentBankTransfer lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CustomerPaymentBankTransfer lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public CustomerPaymentBankTransfer customerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
        return this;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CustomerPaymentBankTransfer currency(Currency currency) {
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
        if (!(o instanceof CustomerPaymentBankTransfer)) {
            return false;
        }
        return id != null && id.equals(((CustomerPaymentBankTransfer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentBankTransfer{" +
            "id=" + getId() +
            ", receiptPhoto='" + getReceiptPhoto() + "'" +
            ", nameInBankAccount='" + getNameInBankAccount() + "'" +
            ", dateOfTransfer='" + getDateOfTransfer() + "'" +
            ", amountTransferred=" + getAmountTransferred() +
            ", bankName='" + getBankName() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
