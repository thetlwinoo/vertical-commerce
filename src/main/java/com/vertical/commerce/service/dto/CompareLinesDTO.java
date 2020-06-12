package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.CompareLines} entity.
 */
public class CompareLinesDTO implements Serializable {
    
    private Long id;


    private Long stockItemId;

    private String stockItemName;

    private Long compareId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompareId() {
        return compareId;
    }

    public void setCompareId(Long comparesId) {
        this.compareId = comparesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompareLinesDTO)) {
            return false;
        }

        return id != null && id.equals(((CompareLinesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompareLinesDTO{" +
            "id=" + getId() +
            ", stockItemId=" + getStockItemId() +
            ", stockItemName='" + getStockItemName() + "'" +
            ", compareId=" + getCompareId() +
            "}";
    }
}
