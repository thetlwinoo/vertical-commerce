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
 * A Customers.
 */
@Entity
@Table(name = "customers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "account_opened_date", nullable = false)
    private Instant accountOpenedDate;

    @NotNull
    @Column(name = "standard_discount_percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal standardDiscountPercentage;

    @NotNull
    @Column(name = "is_statement_sent", nullable = false)
    private Boolean isStatementSent;

    @NotNull
    @Column(name = "is_on_credit_hold", nullable = false)
    private Boolean isOnCreditHold;

    @NotNull
    @Column(name = "payment_days", nullable = false)
    private Integer paymentDays;

    @Column(name = "delivery_run")
    private String deliveryRun;

    @Column(name = "run_position")
    private String runPosition;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @OneToOne
    @JoinColumn(unique = true)
    private People people;

    @ManyToOne
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private DeliveryMethods deliveryMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Addresses deliveryAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customers accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getAccountOpenedDate() {
        return accountOpenedDate;
    }

    public Customers accountOpenedDate(Instant accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
        return this;
    }

    public void setAccountOpenedDate(Instant accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
    }

    public BigDecimal getStandardDiscountPercentage() {
        return standardDiscountPercentage;
    }

    public Customers standardDiscountPercentage(BigDecimal standardDiscountPercentage) {
        this.standardDiscountPercentage = standardDiscountPercentage;
        return this;
    }

    public void setStandardDiscountPercentage(BigDecimal standardDiscountPercentage) {
        this.standardDiscountPercentage = standardDiscountPercentage;
    }

    public Boolean isIsStatementSent() {
        return isStatementSent;
    }

    public Customers isStatementSent(Boolean isStatementSent) {
        this.isStatementSent = isStatementSent;
        return this;
    }

    public void setIsStatementSent(Boolean isStatementSent) {
        this.isStatementSent = isStatementSent;
    }

    public Boolean isIsOnCreditHold() {
        return isOnCreditHold;
    }

    public Customers isOnCreditHold(Boolean isOnCreditHold) {
        this.isOnCreditHold = isOnCreditHold;
        return this;
    }

    public void setIsOnCreditHold(Boolean isOnCreditHold) {
        this.isOnCreditHold = isOnCreditHold;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public Customers paymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
        return this;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getDeliveryRun() {
        return deliveryRun;
    }

    public Customers deliveryRun(String deliveryRun) {
        this.deliveryRun = deliveryRun;
        return this;
    }

    public void setDeliveryRun(String deliveryRun) {
        this.deliveryRun = deliveryRun;
    }

    public String getRunPosition() {
        return runPosition;
    }

    public Customers runPosition(String runPosition) {
        this.runPosition = runPosition;
        return this;
    }

    public void setRunPosition(String runPosition) {
        this.runPosition = runPosition;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Customers lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Customers validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Customers validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public People getPeople() {
        return people;
    }

    public Customers people(People people) {
        this.people = people;
        return this;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public Customers deliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
        return this;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
    }

    public Addresses getDeliveryAddress() {
        return deliveryAddress;
    }

    public Customers deliveryAddress(Addresses addresses) {
        this.deliveryAddress = addresses;
        return this;
    }

    public void setDeliveryAddress(Addresses addresses) {
        this.deliveryAddress = addresses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customers)) {
            return false;
        }
        return id != null && id.equals(((Customers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customers{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountOpenedDate='" + getAccountOpenedDate() + "'" +
            ", standardDiscountPercentage=" + getStandardDiscountPercentage() +
            ", isStatementSent='" + isIsStatementSent() + "'" +
            ", isOnCreditHold='" + isIsOnCreditHold() + "'" +
            ", paymentDays=" + getPaymentDays() +
            ", deliveryRun='" + getDeliveryRun() + "'" +
            ", runPosition='" + getRunPosition() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
