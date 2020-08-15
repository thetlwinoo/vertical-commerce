package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WishlistLines.
 */
@Entity
@Table(name = "wishlist_lines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WishlistLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "wishlistLines", allowSetters = true)
    private StockItems stockItem;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = "wishlistLineLists", allowSetters = true)
    private Wishlists wishlist;

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

    public WishlistLines stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public Wishlists getWishlist() {
        return wishlist;
    }

    public WishlistLines wishlist(Wishlists wishlists) {
        this.wishlist = wishlists;
        return this;
    }

    public void setWishlist(Wishlists wishlists) {
        this.wishlist = wishlists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WishlistLines)) {
            return false;
        }
        return id != null && id.equals(((WishlistLines) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WishlistLines{" +
            "id=" + getId() +
            "}";
    }
}
