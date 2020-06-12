package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CustomerPaymentCreditCard} entity.
 */
public class CustomerPaymentCreditCardDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String creditCardNumber;

    @NotNull
    private Integer creditCardExpiryMonth;

    @NotNull
    private Integer creditCardExpiryYear;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String batchId;

    @NotNull
    private String responseCode;

    @NotNull
    private String approvalCode;

    @Lob
    private String responseData;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long customerPaymentId;

    private Long currencyId;

    private String currencyName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Integer getCreditCardExpiryMonth() {
        return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth(Integer creditCardExpiryMonth) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public Integer getCreditCardExpiryYear() {
        return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear(Integer creditCardExpiryYear) {
        this.creditCardExpiryYear = creditCardExpiryYear;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getCustomerPaymentId() {
        return customerPaymentId;
    }

    public void setCustomerPaymentId(Long customerPaymentId) {
        this.customerPaymentId = customerPaymentId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentCreditCardDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerPaymentCreditCardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentCreditCardDTO{" +
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
            ", customerPaymentId=" + getCustomerPaymentId() +
            ", currencyId=" + getCurrencyId() +
            ", currencyName='" + getCurrencyName() + "'" +
            "}";
    }
}
