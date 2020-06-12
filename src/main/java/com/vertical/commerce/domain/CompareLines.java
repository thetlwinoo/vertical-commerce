package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CompareLines.
 */
@Entity
@Table(name = "compare_lines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompareLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "compareLines", allowSetters = true)
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "compareLineLists", allowSetters = true)
    private Compares compare;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public CompareLines stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public Compares getCompare() {
        return compare;
    }

    public CompareLines compare(Compares compares) {
        this.compare = compares;
        return this;
    }

    public void setCompare(Compares compares) {
        this.compare = compares;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompareLines)) {
            return false;
        }
        return id != null && id.equals(((CompareLines) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompareLines{" +
            "id=" + getId() +
            "}";
    }
}
