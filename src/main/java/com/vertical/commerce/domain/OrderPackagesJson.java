package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_packages_json")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderPackagesJson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "volume_weight")
    private Integer volumeWeight;

    @Column(name = "actual_weight")
    private BigDecimal actualWeight;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_items")
    private Integer totalItems;

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "shopping_items")
    private String shoppingItems;

    //id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //supplierId
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    //supplierId
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    //volumeWeight
    public Integer getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(Integer volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    //actualWeight
    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    //totalQuantity
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    //totalItems
    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    //shippingFee
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    //totalPrice
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    //shoppingItems
    public String getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(String shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPackagesJson)) {
            return false;
        }
        return id != null && id.equals(((OrderPackagesJson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderPackagesJson{" +
            "id=" + getId() +
            ", supplierId='" + getSupplierId() + "'" +
            ", packageName='" + getPackageName() + "'" +
            ", volumeWeight='" + getVolumeWeight() + "'" +
            ", actualWeight='" + getActualWeight() + "'" +
            ", totalQuantity='" + getTotalQuantity() + "'" +
            ", totalItems='" + getTotalItems() + "'" +
            ", shippingFee='" + getShippingFee() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", shoppingItems='" + getShoppingItems() + "'" +
            "}";
    }
}
