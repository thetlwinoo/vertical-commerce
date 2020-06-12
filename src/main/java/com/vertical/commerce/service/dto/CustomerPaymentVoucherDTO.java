package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CustomerPaymentVoucher} entity.
 */
public class CustomerPaymentVoucherDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String serialNo;

    @NotNull
    private BigDecimal amount;

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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        if (!(o instanceof CustomerPaymentVoucherDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerPaymentVoucherDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPaymentVoucherDTO{" +
            "id=" + getId() +
            ", serialNo='" + getSerialNo() + "'" +
            ", amount=" + getAmount() +
            ", lastEdityBy='" + getLastEdityBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", customerPaymentId=" + getCustomerPaymentId() +
            ", currencyId=" + getCurrencyId() +
            ", currencyName='" + getCurrencyName() + "'" +
            "}";
    }
}
