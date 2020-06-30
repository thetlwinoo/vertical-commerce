package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "shopping_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShoppingItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "stock_item_id")
    private Long stockItemId;

    @Column(name = "stock_item_name")
    private String stockItemName;

    @Column(name = "select_order")
    private Boolean selectOrder;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "quantity_on_hand")
    private Integer quantityOnHand;

    @Column(name = "delivery_method_id")
    private Long deliveryMethodId;

    @Column(name = "blob_id")
    private String blobId;

//    @ManyToOne
//    @JsonIgnoreProperties(value = "stockItemsLists", allowSetters = true)
//    private ShippingFeeCalculated shippingFeeCalculated;

    //id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //productId
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    //productName
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //stockItemId
    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemName(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    //stockItemName
    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
    }

    //selectOrder
    public Boolean getSelectOrder() {
        return selectOrder;
    }

    public void setSelectOrder(Boolean selectOrder) {
        this.selectOrder = selectOrder;
    }

    //quantity
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    //unitPrice
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    //quantityOnHand
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    //deliveryMethodId
    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    //blobId
    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    //shippingFeeCalculated
//    public ShippingFeeCalculated getShippingFeeCalculated() {
//        return shippingFeeCalculated;
//    }
//
//    public ShoppingItems shippingFeeCalculated(ShippingFeeCalculated shippingFeeCalculated) {
//        this.shippingFeeCalculated = shippingFeeCalculated;
//        return this;
//    }
//
//    public void setShippingFeeCalculated(ShippingFeeCalculated shippingFeeCalculated) {
//        this.shippingFeeCalculated = shippingFeeCalculated;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingItems)) {
            return false;
        }
        return id != null && id.equals(((ShoppingItems) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShoppingItems{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", stockItemId='" + getStockItemId() + "'" +
            ", stockItemName='" + getStockItemName() + "'" +
            ", selectOrder='" + getSelectOrder() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", unitPrice='" + getUnitPrice() + "'" +
            ", quantityOnHand='" + getQuantityOnHand() + "'" +
            ", deliveryMethodId='" + getDeliveryMethodId() + "'" +
            ", blobId='" + getBlobId() + "'" +
            "}";
    }
}

