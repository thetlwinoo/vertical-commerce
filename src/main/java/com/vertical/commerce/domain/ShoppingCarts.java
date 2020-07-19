package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ShoppingCarts.
 */
@Entity
@Table(name = "shopping_carts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShoppingCarts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "total_tax_amount", precision = 21, scale = 2)
    private BigDecimal totalTaxAmount;

    @Column(name = "sub_total_price", precision = 21, scale = 2)
    private BigDecimal subTotalPrice;

    @Column(name = "total_shipping_fee", precision = 21, scale = 2)
    private BigDecimal totalShippingFee;

    @Column(name = "total_shipping_fee_discount", precision = 21, scale = 2)
    private BigDecimal totalShippingFeeDiscount;

    @Column(name = "promotion_total", precision = 21, scale = 2)
    private BigDecimal promotionTotal;

    @Column(name = "voucher_total", precision = 21, scale = 2)
    private BigDecimal voucherTotal;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "package_details")
    private String packageDetails;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "cart_details")
    private String cartDetails;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "deal_details")
    private String dealDetails;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private People cartUser;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ShoppingCartItems> cartItemLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "shoppingCarts", allowSetters = true)
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "cartLists", allowSetters = true)
    private SpecialDeals specialDeals;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCarts totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public ShoppingCarts totalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
        return this;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
    }

    public ShoppingCarts subTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
        return this;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public BigDecimal getTotalShippingFee() {
        return totalShippingFee;
    }

    public ShoppingCarts totalShippingFee(BigDecimal totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
        return this;
    }

    public void setTotalShippingFee(BigDecimal totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
    }

    public BigDecimal getTotalShippingFeeDiscount() {
        return totalShippingFeeDiscount;
    }

    public ShoppingCarts totalShippingFeeDiscount(BigDecimal totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
        return this;
    }

    public void setTotalShippingFeeDiscount(BigDecimal totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
    }

    public BigDecimal getPromotionTotal() {
        return promotionTotal;
    }

    public ShoppingCarts promotionTotal(BigDecimal promotionTotal) {
        this.promotionTotal = promotionTotal;
        return this;
    }

    public void setPromotionTotal(BigDecimal promotionTotal) {
        this.promotionTotal = promotionTotal;
    }

    public BigDecimal getVoucherTotal() {
        return voucherTotal;
    }

    public ShoppingCarts voucherTotal(BigDecimal voucherTotal) {
        this.voucherTotal = voucherTotal;
        return this;
    }

    public void setVoucherTotal(BigDecimal voucherTotal) {
        this.voucherTotal = voucherTotal;
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public ShoppingCarts packageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
        return this;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
    }

    public String getCartDetails() {
        return cartDetails;
    }

    public ShoppingCarts cartDetails(String cartDetails) {
        this.cartDetails = cartDetails;
        return this;
    }

    public void setCartDetails(String cartDetails) {
        this.cartDetails = cartDetails;
    }

    public String getDealDetails() {
        return dealDetails;
    }

    public ShoppingCarts dealDetails(String dealDetails) {
        this.dealDetails = dealDetails;
        return this;
    }

    public void setDealDetails(String dealDetails) {
        this.dealDetails = dealDetails;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public ShoppingCarts lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public ShoppingCarts lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public People getCartUser() {
        return cartUser;
    }

    public ShoppingCarts cartUser(People people) {
        this.cartUser = people;
        return this;
    }

    public void setCartUser(People people) {
        this.cartUser = people;
    }

    public Set<ShoppingCartItems> getCartItemLists() {
        return cartItemLists;
    }

    public ShoppingCarts cartItemLists(Set<ShoppingCartItems> shoppingCartItems) {
        this.cartItemLists = shoppingCartItems;
        return this;
    }

    public ShoppingCarts addCartItemList(ShoppingCartItems shoppingCartItems) {
        this.cartItemLists.add(shoppingCartItems);
        shoppingCartItems.setCart(this);
        return this;
    }

    public ShoppingCarts removeCartItemList(ShoppingCartItems shoppingCartItems) {
        this.cartItemLists.remove(shoppingCartItems);
        shoppingCartItems.setCart(null);
        return this;
    }

    public void setCartItemLists(Set<ShoppingCartItems> shoppingCartItems) {
        this.cartItemLists = shoppingCartItems;
    }

    public Customers getCustomer() {
        return customer;
    }

    public ShoppingCarts customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public SpecialDeals getSpecialDeals() {
        return specialDeals;
    }

    public ShoppingCarts specialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
        return this;
    }

    public void setSpecialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCarts)) {
            return false;
        }
        return id != null && id.equals(((ShoppingCarts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingCarts{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", totalTaxAmount=" + getTotalTaxAmount() +
            ", subTotalPrice=" + getSubTotalPrice() +
            ", totalShippingFee=" + getTotalShippingFee() +
            ", totalShippingFeeDiscount=" + getTotalShippingFeeDiscount() +
            ", promotionTotal=" + getPromotionTotal() +
            ", voucherTotal=" + getVoucherTotal() +
            ", packageDetails='" + getPackageDetails() + "'" +
            ", cartDetails='" + getCartDetails() + "'" +
            ", dealDetails='" + getDealDetails() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
