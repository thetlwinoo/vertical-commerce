package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.StockItemHoldings} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.StockItemHoldingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-item-holdings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockItemHoldingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantityOnHand;

    private StringFilter binLocation;

    private IntegerFilter lastStockTakeQuantity;

    private BigDecimalFilter lastCostPrice;

    private IntegerFilter reorderLevel;

    private IntegerFilter targerStockLevel;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter stockItemId;

    public StockItemHoldingsCriteria() {
    }

    public StockItemHoldingsCriteria(StockItemHoldingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantityOnHand = other.quantityOnHand == null ? null : other.quantityOnHand.copy();
        this.binLocation = other.binLocation == null ? null : other.binLocation.copy();
        this.lastStockTakeQuantity = other.lastStockTakeQuantity == null ? null : other.lastStockTakeQuantity.copy();
        this.lastCostPrice = other.lastCostPrice == null ? null : other.lastCostPrice.copy();
        this.reorderLevel = other.reorderLevel == null ? null : other.reorderLevel.copy();
        this.targerStockLevel = other.targerStockLevel == null ? null : other.targerStockLevel.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
    }

    @Override
    public StockItemHoldingsCriteria copy() {
        return new StockItemHoldingsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(IntegerFilter quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public StringFilter getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(StringFilter binLocation) {
        this.binLocation = binLocation;
    }

    public IntegerFilter getLastStockTakeQuantity() {
        return lastStockTakeQuantity;
    }

    public void setLastStockTakeQuantity(IntegerFilter lastStockTakeQuantity) {
        this.lastStockTakeQuantity = lastStockTakeQuantity;
    }

    public BigDecimalFilter getLastCostPrice() {
        return lastCostPrice;
    }

    public void setLastCostPrice(BigDecimalFilter lastCostPrice) {
        this.lastCostPrice = lastCostPrice;
    }

    public IntegerFilter getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(IntegerFilter reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public IntegerFilter getTargerStockLevel() {
        return targerStockLevel;
    }

    public void setTargerStockLevel(IntegerFilter targerStockLevel) {
        this.targerStockLevel = targerStockLevel;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockItemHoldingsCriteria that = (StockItemHoldingsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantityOnHand, that.quantityOnHand) &&
            Objects.equals(binLocation, that.binLocation) &&
            Objects.equals(lastStockTakeQuantity, that.lastStockTakeQuantity) &&
            Objects.equals(lastCostPrice, that.lastCostPrice) &&
            Objects.equals(reorderLevel, that.reorderLevel) &&
            Objects.equals(targerStockLevel, that.targerStockLevel) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(stockItemId, that.stockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantityOnHand,
        binLocation,
        lastStockTakeQuantity,
        lastCostPrice,
        reorderLevel,
        targerStockLevel,
        lastEditedBy,
        lastEditedWhen,
        stockItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockItemHoldingsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantityOnHand != null ? "quantityOnHand=" + quantityOnHand + ", " : "") +
                (binLocation != null ? "binLocation=" + binLocation + ", " : "") +
                (lastStockTakeQuantity != null ? "lastStockTakeQuantity=" + lastStockTakeQuantity + ", " : "") +
                (lastCostPrice != null ? "lastCostPrice=" + lastCostPrice + ", " : "") +
                (reorderLevel != null ? "reorderLevel=" + reorderLevel + ", " : "") +
                (targerStockLevel != null ? "targerStockLevel=" + targerStockLevel + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
            "}";
    }

}
