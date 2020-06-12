package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Customers} entity.
 */
public class CustomersDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private Instant accountOpenedDate;

    @NotNull
    private BigDecimal standardDiscountPercentage;

    @NotNull
    private Boolean isStatementSent;

    @NotNull
    private Boolean isOnCreditHold;

    @NotNull
    private Integer paymentDays;

    private String deliveryRun;

    private String runPosition;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;


    private Long peopleId;

    private String peopleFullName;

    private Long deliveryMethodId;

    private String deliveryMethodName;

    private Long deliveryAddressId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getAccountOpenedDate() {
        return accountOpenedDate;
    }

    public void setAccountOpenedDate(Instant accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
    }

    public BigDecimal getStandardDiscountPercentage() {
        return standardDiscountPercentage;
    }

    public void setStandardDiscountPercentage(BigDecimal standardDiscountPercentage) {
        this.standardDiscountPercentage = standardDiscountPercentage;
    }

    public Boolean isIsStatementSent() {
        return isStatementSent;
    }

    public void setIsStatementSent(Boolean isStatementSent) {
        this.isStatementSent = isStatementSent;
    }

    public Boolean isIsOnCreditHold() {
        return isOnCreditHold;
    }

    public void setIsOnCreditHold(Boolean isOnCreditHold) {
        this.isOnCreditHold = isOnCreditHold;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getDeliveryRun() {
        return deliveryRun;
    }

    public void setDeliveryRun(String deliveryRun) {
        this.deliveryRun = deliveryRun;
    }

    public String getRunPosition() {
        return runPosition;
    }

    public void setRunPosition(String runPosition) {
        this.runPosition = runPosition;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
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

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleFullName() {
        return peopleFullName;
    }

    public void setPeopleFullName(String peopleFullName) {
        this.peopleFullName = peopleFullName;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodsId) {
        this.deliveryMethodId = deliveryMethodsId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodsName) {
        this.deliveryMethodName = deliveryMethodsName;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long addressesId) {
        this.deliveryAddressId = addressesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersDTO{" +
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
            ", peopleId=" + getPeopleId() +
            ", peopleFullName='" + getPeopleFullName() + "'" +
            ", deliveryMethodId=" + getDeliveryMethodId() +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            ", deliveryAddressId=" + getDeliveryAddressId() +
            "}";
    }
}
