package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A CustomerPayment.
 */
@Entity
@Table(name = "customer_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount_excluding_tax", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountExcludingTax;

    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @NotNull
    @Column(name = "transaction_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal transactionAmount;

    @Column(name = "outstanding_amount", precision = 21, scale = 2)
    private BigDecimal outstandingAmount;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerTransactions customerTransaction;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerPayments", allowSetters = true)
    private PaymentMethods paymentMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerPayments", allowSetters = true)
    private Currency currency;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerPayments", allowSetters = true)
    private CurrencyRate currencyRate;

    @OneToOne(mappedBy = "customerPayment")
    @JsonIgnore
    private CustomerPaymentCreditCard customerPaymentCreditCard;

    @OneToOne(mappedBy = "customerPayment")
    @JsonIgnore
    private CustomerPaymentVoucher customerPaymentVoucher;

    @OneToOne(mappedBy = "customerPayment")
    @JsonIgnore
    private CustomerPaymentBankTransfer customerPaymentBankTransfer;

    @OneToOne(mappedBy = "customerPayment")
    @JsonIgnore
    private CustomerPaymentPaypal customerPaymentPaypal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public CustomerPayment amountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
        return this;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public CustomerPayment taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public CustomerPayment transactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public CustomerPayment outstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
        return this;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CustomerPayment lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CustomerPayment lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public CustomerTransactions getCustomerTransaction() {
        return customerTransaction;
    }

    public CustomerPayment customerTransaction(CustomerTransactions customerTransactions) {
        this.customerTransaction = customerTransactions;
        return this;
    }

    public void setCustomerTransaction(CustomerTransactions customerTransactions) {
        this.customerTransaction = customerTransactions;
    }

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public CustomerPayment paymentMethod(PaymentMethods paymentMethods) {
        this.paymentMethod = paymentMethods;
        return this;
    }

    public void setPaymentMethod(PaymentMethods paymentMethods) {
        this.paymentMethod = paymentMethods;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CustomerPayment currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CurrencyRate getCurrencyRate() {
        return currencyRate;
    }

    public CustomerPayment currencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
        return this;
    }

    public void setCurrencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
    }

    public CustomerPaymentCreditCard getCustomerPaymentCreditCard() {
        return customerPaymentCreditCard;
    }

    public CustomerPayment customerPaymentCreditCard(CustomerPaymentCreditCard customerPaymentCreditCard) {
        this.customerPaymentCreditCard = customerPaymentCreditCard;
        return this;
    }

    public void setCustomerPaymentCreditCard(CustomerPaymentCreditCard customerPaymentCreditCard) {
        this.customerPaymentCreditCard = customerPaymentCreditCard;
    }

    public CustomerPaymentVoucher getCustomerPaymentVoucher() {
        return customerPaymentVoucher;
    }

    public CustomerPayment customerPaymentVoucher(CustomerPaymentVoucher customerPaymentVoucher) {
        this.customerPaymentVoucher = customerPaymentVoucher;
        return this;
    }

    public void setCustomerPaymentVoucher(CustomerPaymentVoucher customerPaymentVoucher) {
        this.customerPaymentVoucher = customerPaymentVoucher;
    }

    public CustomerPaymentBankTransfer getCustomerPaymentBankTransfer() {
        return customerPaymentBankTransfer;
    }

    public CustomerPayment customerPaymentBankTransfer(CustomerPaymentBankTransfer customerPaymentBankTransfer) {
        this.customerPaymentBankTransfer = customerPaymentBankTransfer;
        return this;
    }

    public void setCustomerPaymentBankTransfer(CustomerPaymentBankTransfer customerPaymentBankTransfer) {
        this.customerPaymentBankTransfer = customerPaymentBankTransfer;
    }

    public CustomerPaymentPaypal getCustomerPaymentPaypal() {
        return customerPaymentPaypal;
    }

    public CustomerPayment customerPaymentPaypal(CustomerPaymentPaypal customerPaymentPaypal) {
        this.customerPaymentPaypal = customerPaymentPaypal;
        return this;
    }

    public void setCustomerPaymentPaypal(CustomerPaymentPaypal customerPaymentPaypal) {
        this.customerPaymentPaypal = customerPaymentPaypal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPayment)) {
            return false;
        }
        return id != null && id.equals(((CustomerPayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPayment{" +
            "id=" + getId() +
            ", amountExcludingTax=" + getAmountExcludingTax() +
            ", taxAmount=" + getTaxAmount() +
            ", transactionAmount=" + getTransactionAmount() +
            ", outstandingAmount=" + getOutstandingAmount() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
