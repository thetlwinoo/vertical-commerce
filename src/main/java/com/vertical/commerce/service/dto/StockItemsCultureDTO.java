package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.StockItemsCulture} entity.
 */
public class StockItemsCultureDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long stockItemId;

    private String stockItemName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCultureId() {
        return cultureId;
    }

    public void setCultureId(Long cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureCode() {
        return cultureCode;
    }

    public void setCultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemsName) {
        this.stockItemName = stockItemsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItemsCultureDTO)) {
            return false;
        }

        return id != null && id.equals(((StockItemsCultureDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockItemsCultureDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", stockItemId=" + getStockItemId() +
            ", stockItemName='" + getStockItemName() + "'" +
            "}";
    }
}
