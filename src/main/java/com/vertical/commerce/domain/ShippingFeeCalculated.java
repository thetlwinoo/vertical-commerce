package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shipping_fee_calculated")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShippingFeeCalculated implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long supplierId;

    @Column(name = "packageName")
    private String packageName;

    @Column(name = "volumeWeight")
    private Integer volumeWeight;

    @Column(name = "actualWeight")
    private BigDecimal actualWeight;

    @Column(name = "totalQuantity")
    private Integer totalQuantity;

    @Column(name = "totalItems")
    private Integer totalItems;

    @Column(name = "shippingFee")
    private BigDecimal shippingFee;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "shopping_items")
    private String shoppingItems;
//    @OneToMany(mappedBy = "shippingFeeCalculated")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    private Set<ShoppingItems> shoppingItemsLists = new HashSet<>();

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "suppliers_delivery_method")
    private String suppliersDeliveryMethod;

    //supplierId
    public Long getSupplierId() {
        return supplierId;
    }

    public ShippingFeeCalculated supplierId(Long supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    //packageName
    public String getPackageName() {
        return packageName;
    }

    public ShippingFeeCalculated packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    //volumeWeight
    public Integer getVolumeWeight() {
        return volumeWeight;
    }

    public ShippingFeeCalculated volumeWeight(Integer volumeWeight) {
        this.volumeWeight = volumeWeight;
        return this;
    }

    public void setVolumeWeight(Integer volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    //actualWeight
    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public ShippingFeeCalculated actualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
        return this;
    }

    public void setActutalWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    //totalItems
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public ShippingFeeCalculated totalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    //totalItems
    public Integer getTotalItems() {
        return totalItems;
    }

    public ShippingFeeCalculated totalItems(Integer totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    //shippingFee
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public ShippingFeeCalculated shippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
        return this;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    //totalPrice
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShippingFeeCalculated totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    //shoppingItems
//    public Set<ShoppingItems> getShoppingItemsLists() {
//        return shoppingItemsLists;
//    }
//
//    public ShippingFeeCalculated shoppingItemsLists(Set<ShoppingItems> shoppingItems) {
//        this.shoppingItemsLists = shoppingItems;
//        return this;
//    }
//
//    public void setShoppingItemsLists(Set<ShoppingItems> shoppingItemsLists) {
//        this.shoppingItemsLists = shoppingItemsLists;
//    }
    public String getShoppingItems() {
        return shoppingItems;
    }

    public ShippingFeeCalculated shoppingItems(String shoppingItems) {
        this.shoppingItems = shoppingItems;
        return this;
    }

    public void setShoppingItems(String shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    //suppliersDeliveryMethod
    public String getSuppliersDeliveryMethod() {
        return suppliersDeliveryMethod;
    }

    public ShippingFeeCalculated suppliersDeliveryMethod(String suppliersDeliveryMethod) {
        this.suppliersDeliveryMethod = suppliersDeliveryMethod;
        return this;
    }

    public void setSuppliersDeliveryMethod(String suppliersDeliveryMethod) {
        this.suppliersDeliveryMethod = suppliersDeliveryMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingFeeCalculated)) {
            return false;
        }
        return supplierId != null && supplierId.equals(((ShippingFeeCalculated) o).supplierId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingFeeCalculated{" +
            "supplierId=" + getSupplierId() +
            ", packageName='" + getPackageName() + "'" +
            ", volumeWeight='" + getVolumeWeight() + "'" +
            ", actualWeight='" + getActualWeight() + "'" +
            ", totalItems='" + getActualWeight() + "'" +
            ", shippingFee='" + getShippingFee() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", suppliersDeliveryMethod='" + getSuppliersDeliveryMethod() + "'" +
            "}";
    }
}
