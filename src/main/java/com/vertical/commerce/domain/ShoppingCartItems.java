package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ShoppingCartItems.
 */
@Entity
@Table(name = "shopping_cart_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShoppingCartItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "select_order")
    private Boolean selectOrder;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties(value = "shoppingCartItems", allowSetters = true)
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "shoppingCartItems", allowSetters = true)
    private DeliveryMethods deliveryMethod;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = "cartItemLists", allowSetters = true)
    private ShoppingCarts cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ShoppingCartItems quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isSelectOrder() {
        return selectOrder;
    }

    public ShoppingCartItems selectOrder(Boolean selectOrder) {
        this.selectOrder = selectOrder;
        return this;
    }

    public void setSelectOrder(Boolean selectOrder) {
        this.selectOrder = selectOrder;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public ShoppingCartItems lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public ShoppingCartItems lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public ShoppingCartItems stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public ShoppingCartItems deliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
        return this;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
    }

    public ShoppingCarts getCart() {
        return cart;
    }

    public ShoppingCartItems cart(ShoppingCarts shoppingCarts) {
        this.cart = shoppingCarts;
        return this;
    }

    public void setCart(ShoppingCarts shoppingCarts) {
        this.cart = shoppingCarts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCartItems)) {
            return false;
        }
        return id != null && id.equals(((ShoppingCartItems) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingCartItems{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", selectOrder='" + isSelectOrder() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
