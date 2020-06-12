package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A StockItemHoldings.
 */
@Entity
@Table(name = "stock_item_holdings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockItemHoldings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand;

    @NotNull
    @Column(name = "bin_location", nullable = false)
    private String binLocation;

    @NotNull
    @Column(name = "last_stock_take_quantity", nullable = false)
    private Integer lastStockTakeQuantity;

    @NotNull
    @Column(name = "last_cost_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal lastCostPrice;

    @NotNull
    @Column(name = "reorder_level", nullable = false)
    private Integer reorderLevel;

    @NotNull
    @Column(name = "targer_stock_level", nullable = false)
    private Integer targerStockLevel;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private StockItems stockItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public StockItemHoldings quantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        return this;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public StockItemHoldings binLocation(String binLocation) {
        this.binLocation = binLocation;
        return this;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }

    public Integer getLastStockTakeQuantity() {
        return lastStockTakeQuantity;
    }

    public StockItemHoldings lastStockTakeQuantity(Integer lastStockTakeQuantity) {
        this.lastStockTakeQuantity = lastStockTakeQuantity;
        return this;
    }

    public void setLastStockTakeQuantity(Integer lastStockTakeQuantity) {
        this.lastStockTakeQuantity = lastStockTakeQuantity;
    }

    public BigDecimal getLastCostPrice() {
        return lastCostPrice;
    }

    public StockItemHoldings lastCostPrice(BigDecimal lastCostPrice) {
        this.lastCostPrice = lastCostPrice;
        return this;
    }

    public void setLastCostPrice(BigDecimal lastCostPrice) {
        this.lastCostPrice = lastCostPrice;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public StockItemHoldings reorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
        return this;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getTargerStockLevel() {
        return targerStockLevel;
    }

    public StockItemHoldings targerStockLevel(Integer targerStockLevel) {
        this.targerStockLevel = targerStockLevel;
        return this;
    }

    public void setTargerStockLevel(Integer targerStockLevel) {
        this.targerStockLevel = targerStockLevel;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public StockItemHoldings lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public StockItemHoldings lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public StockItemHoldings stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItemHoldings)) {
            return false;
        }
        return id != null && id.equals(((StockItemHoldings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockItemHoldings{" +
            "id=" + getId() +
            ", quantityOnHand=" + getQuantityOnHand() +
            ", binLocation='" + getBinLocation() + "'" +
            ", lastStockTakeQuantity=" + getLastStockTakeQuantity() +
            ", lastCostPrice=" + getLastCostPrice() +
            ", reorderLevel=" + getReorderLevel() +
            ", targerStockLevel=" + getTargerStockLevel() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
