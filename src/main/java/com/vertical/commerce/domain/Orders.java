package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import com.vertical.commerce.domain.enumeration.PaymentStatus;

import com.vertical.commerce.domain.enumeration.OrderStatus;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Column(name = "sub_total", precision = 21, scale = 2)
    private BigDecimal subTotal;

    @Column(name = "total_tax_amount", precision = 21, scale = 2)
    private BigDecimal totalTaxAmount;

    @Column(name = "total_shipping_fee", precision = 21, scale = 2)
    private BigDecimal totalShippingFee;

    @Column(name = "total_shipping_fee_discount", precision = 21, scale = 2)
    private BigDecimal totalShippingFeeDiscount;

    @Column(name = "total_voucher_discount", precision = 21, scale = 2)
    private BigDecimal totalVoucherDiscount;

    @Column(name = "total_promtion_discount", precision = 21, scale = 2)
    private BigDecimal totalPromtionDiscount;

    @Column(name = "total_due", precision = 21, scale = 2)
    private BigDecimal totalDue;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "customer_purchase_order_number")
    private String customerPurchaseOrderNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "order_details")
    private String orderDetails;

    @Column(name = "is_under_supply_back_ordered")
    private Boolean isUnderSupplyBackOrdered;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OrderPackages> orderPackageLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Addresses shipToAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Addresses billToAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private CurrencyRate currencyRate;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private PaymentMethods paymentMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private People salePerson;

    @OneToOne(mappedBy = "order")
    @JsonIgnore
    private OrderTracking orderTracking;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderLists", allowSetters = true)
    private SpecialDeals specialDeals;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Orders orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public Orders subTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public Orders totalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
        return this;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public BigDecimal getTotalShippingFee() {
        return totalShippingFee;
    }

    public Orders totalShippingFee(BigDecimal totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
        return this;
    }

    public void setTotalShippingFee(BigDecimal totalShippingFee) {
        this.totalShippingFee = totalShippingFee;
    }

    public BigDecimal getTotalShippingFeeDiscount() {
        return totalShippingFeeDiscount;
    }

    public Orders totalShippingFeeDiscount(BigDecimal totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
        return this;
    }

    public void setTotalShippingFeeDiscount(BigDecimal totalShippingFeeDiscount) {
        this.totalShippingFeeDiscount = totalShippingFeeDiscount;
    }

    public BigDecimal getTotalVoucherDiscount() {
        return totalVoucherDiscount;
    }

    public Orders totalVoucherDiscount(BigDecimal totalVoucherDiscount) {
        this.totalVoucherDiscount = totalVoucherDiscount;
        return this;
    }

    public void setTotalVoucherDiscount(BigDecimal totalVoucherDiscount) {
        this.totalVoucherDiscount = totalVoucherDiscount;
    }

    public BigDecimal getTotalPromtionDiscount() {
        return totalPromtionDiscount;
    }

    public Orders totalPromtionDiscount(BigDecimal totalPromtionDiscount) {
        this.totalPromtionDiscount = totalPromtionDiscount;
        return this;
    }

    public void setTotalPromtionDiscount(BigDecimal totalPromtionDiscount) {
        this.totalPromtionDiscount = totalPromtionDiscount;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public Orders totalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
        return this;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Orders paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerPurchaseOrderNumber() {
        return customerPurchaseOrderNumber;
    }

    public Orders customerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
        return this;
    }

    public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Orders status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public Orders orderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Boolean isIsUnderSupplyBackOrdered() {
        return isUnderSupplyBackOrdered;
    }

    public Orders isUnderSupplyBackOrdered(Boolean isUnderSupplyBackOrdered) {
        this.isUnderSupplyBackOrdered = isUnderSupplyBackOrdered;
        return this;
    }

    public void setIsUnderSupplyBackOrdered(Boolean isUnderSupplyBackOrdered) {
        this.isUnderSupplyBackOrdered = isUnderSupplyBackOrdered;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Orders lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public Orders lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Set<OrderPackages> getOrderPackageLists() {
        return orderPackageLists;
    }

    public Orders orderPackageLists(Set<OrderPackages> orderPackages) {
        this.orderPackageLists = orderPackages;
        return this;
    }

    public Orders addOrderPackageList(OrderPackages orderPackages) {
        this.orderPackageLists.add(orderPackages);
        orderPackages.setOrder(this);
        return this;
    }

    public Orders removeOrderPackageList(OrderPackages orderPackages) {
        this.orderPackageLists.remove(orderPackages);
        orderPackages.setOrder(null);
        return this;
    }

    public void setOrderPackageLists(Set<OrderPackages> orderPackages) {
        this.orderPackageLists = orderPackages;
    }

    public Customers getCustomer() {
        return customer;
    }

    public Orders customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public Addresses getShipToAddress() {
        return shipToAddress;
    }

    public Orders shipToAddress(Addresses addresses) {
        this.shipToAddress = addresses;
        return this;
    }

    public void setShipToAddress(Addresses addresses) {
        this.shipToAddress = addresses;
    }

    public Addresses getBillToAddress() {
        return billToAddress;
    }

    public Orders billToAddress(Addresses addresses) {
        this.billToAddress = addresses;
        return this;
    }

    public void setBillToAddress(Addresses addresses) {
        this.billToAddress = addresses;
    }

    public CurrencyRate getCurrencyRate() {
        return currencyRate;
    }

    public Orders currencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
        return this;
    }

    public void setCurrencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
    }

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public Orders paymentMethod(PaymentMethods paymentMethods) {
        this.paymentMethod = paymentMethods;
        return this;
    }

    public void setPaymentMethod(PaymentMethods paymentMethods) {
        this.paymentMethod = paymentMethods;
    }

    public People getSalePerson() {
        return salePerson;
    }

    public Orders salePerson(People people) {
        this.salePerson = people;
        return this;
    }

    public void setSalePerson(People people) {
        this.salePerson = people;
    }

    public OrderTracking getOrderTracking() {
        return orderTracking;
    }

    public Orders orderTracking(OrderTracking orderTracking) {
        this.orderTracking = orderTracking;
        return this;
    }

    public void setOrderTracking(OrderTracking orderTracking) {
        this.orderTracking = orderTracking;
    }

    public SpecialDeals getSpecialDeals() {
        return specialDeals;
    }

    public Orders specialDeals(SpecialDeals specialDeals) {
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
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", subTotal=" + getSubTotal() +
            ", totalTaxAmount=" + getTotalTaxAmount() +
            ", totalShippingFee=" + getTotalShippingFee() +
            ", totalShippingFeeDiscount=" + getTotalShippingFeeDiscount() +
            ", totalVoucherDiscount=" + getTotalVoucherDiscount() +
            ", totalPromtionDiscount=" + getTotalPromtionDiscount() +
            ", totalDue=" + getTotalDue() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", customerPurchaseOrderNumber='" + getCustomerPurchaseOrderNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", orderDetails='" + getOrderDetails() + "'" +
            ", isUnderSupplyBackOrdered='" + isIsUnderSupplyBackOrdered() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
