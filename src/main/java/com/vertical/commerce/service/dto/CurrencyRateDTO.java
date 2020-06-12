package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CurrencyRate} entity.
 */
public class CurrencyRateDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant currencyRateDate;

    private String fromcode;

    private String tocode;

    private BigDecimal averageRate;

    private BigDecimal endOfDayRate;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCurrencyRateDate() {
        return currencyRateDate;
    }

    public void setCurrencyRateDate(Instant currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
    }

    public String getFromcode() {
        return fromcode;
    }

    public void setFromcode(String fromcode) {
        this.fromcode = fromcode;
    }

    public String getTocode() {
        return tocode;
    }

    public void setTocode(String tocode) {
        this.tocode = tocode;
    }

    public BigDecimal getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(BigDecimal averageRate) {
        this.averageRate = averageRate;
    }

    public BigDecimal getEndOfDayRate() {
        return endOfDayRate;
    }

    public void setEndOfDayRate(BigDecimal endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyRateDTO)) {
            return false;
        }

        return id != null && id.equals(((CurrencyRateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyRateDTO{" +
            "id=" + getId() +
            ", currencyRateDate='" + getCurrencyRateDate() + "'" +
            ", fromcode='" + getFromcode() + "'" +
            ", tocode='" + getTocode() + "'" +
            ", averageRate=" + getAverageRate() +
            ", endOfDayRate=" + getEndOfDayRate() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
