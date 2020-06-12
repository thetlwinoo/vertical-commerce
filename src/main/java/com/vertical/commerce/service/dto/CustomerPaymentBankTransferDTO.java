package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CustomerPaymentBankTransfer} entity.
 */
public class CustomerPaymentBankTransferDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String receiptImageUrl;

    @NotNull
    private String nameInBankAccount;

    @NotNull
    private Instant dateOfTransfer;

    @NotNull
    private BigDecimal amountTransferred;

    @NotNull
    private String lastEdityBy;

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

    public String getReceiptImageUrl() {
        return receiptImageUrl;
    }

    public void setReceiptImageUrl(String receiptImageUrl) {
        this.receiptImageUrl = receiptImageUrl;
    }

    public String getNameInBankAccount() {
        return nameInBankAccount;
    }

    public void setNameInBankAccount(String nameInBankAccount) {
        this.nameInBankAccount = nameInBankAccount;
    }

    public Instant getDateOfTransfer() {
        return dateOfTransfer;
    }

    public void setDateOfTransfer(Instant dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    public BigDecimal getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(BigDecimal amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public String getLastEdityBy() {
        return lastEdityBy;
    }

    public void setLastEdityBy(String lastEdityBy) {
        this.lastEdityBy = lastEdityBy;
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
        if (!(o instanceof CustomerPaymentBankTransferDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerPaymentBankTransferDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentBankTransferDTO{" +
            "id=" + getId() +
            ", receiptImageUrl='" + getReceiptImageUrl() + "'" +
            ", nameInBankAccount='" + getNameInBankAccount() + "'" +
            ", dateOfTransfer='" + getDateOfTransfer() + "'" +
            ", amountTransferred=" + getAmountTransferred() +
            ", lastEdityBy='" + getLastEdityBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", customerPaymentId=" + getCustomerPaymentId() +
            ", currencyId=" + getCurrencyId() +
            ", currencyName='" + getCurrencyName() + "'" +
            "}";
    }
}
