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

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "sub_total", precision = 21, scale = 2)
    private BigDecimal subTotal;

    @Column(name = "tax_amount", precision = 21, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "frieight", precision = 21, scale = 2)
    private BigDecimal frieight;

    @Column(name = "total_due", precision = 21, scale = 2)
    private BigDecimal totalDue;

    @Column(name = "comments")
    private String comments;

    @Column(name = "delivery_instructions")
    private String deliveryInstructions;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "picking_completed_when")
    private Instant pickingCompletedWhen;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "customer_reviewed_on")
    private Instant customerReviewedOn;

    @Column(name = "seller_rating")
    private Integer sellerRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "seller_review")
    private String sellerReview;

    @Column(name = "delivery_rating")
    private Integer deliveryRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "delivery_review")
    private String deliveryReview;

    @Column(name = "review_as_anonymous")
    private Boolean reviewAsAnonymous;

    @Column(name = "completed_review")
    private Boolean completedReview;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "order_line_string")
    private String orderLineString;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OrderLines> orderLineLists = new HashSet<>();

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
    private ShipMethod shipMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private CurrencyRate currencyRate;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private PaymentMethods paymentMethod;

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

    public Instant getDueDate() {
        return dueDate;
    }

    public Orders dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public Orders expectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public Orders accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public Orders taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getFrieight() {
        return frieight;
    }

    public Orders frieight(BigDecimal frieight) {
        this.frieight = frieight;
        return this;
    }

    public void setFrieight(BigDecimal frieight) {
        this.frieight = frieight;
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

    public String getComments() {
        return comments;
    }

    public Orders comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public Orders deliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
        return this;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public Orders internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public Orders pickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
        return this;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
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

    public Instant getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public Orders customerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
        return this;
    }

    public void setCustomerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public Orders sellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
        return this;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerReview() {
        return sellerReview;
    }

    public Orders sellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
        return this;
    }

    public void setSellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
    }

    public Integer getDeliveryRating() {
        return deliveryRating;
    }

    public Orders deliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
        return this;
    }

    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryReview() {
        return deliveryReview;
    }

    public Orders deliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
        return this;
    }

    public void setDeliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public Boolean isReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public Orders reviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
        return this;
    }

    public void setReviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public Boolean isCompletedReview() {
        return completedReview;
    }

    public Orders completedReview(Boolean completedReview) {
        this.completedReview = completedReview;
        return this;
    }

    public void setCompletedReview(Boolean completedReview) {
        this.completedReview = completedReview;
    }

    public String getOrderLineString() {
        return orderLineString;
    }

    public Orders orderLineString(String orderLineString) {
        this.orderLineString = orderLineString;
        return this;
    }

    public void setOrderLineString(String orderLineString) {
        this.orderLineString = orderLineString;
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

    public Set<OrderLines> getOrderLineLists() {
        return orderLineLists;
    }

    public Orders orderLineLists(Set<OrderLines> orderLines) {
        this.orderLineLists = orderLines;
        return this;
    }

    public Orders addOrderLineList(OrderLines orderLines) {
        this.orderLineLists.add(orderLines);
        orderLines.setOrder(this);
        return this;
    }

    public Orders removeOrderLineList(OrderLines orderLines) {
        this.orderLineLists.remove(orderLines);
        orderLines.setOrder(null);
        return this;
    }

    public void setOrderLineLists(Set<OrderLines> orderLines) {
        this.orderLineLists = orderLines;
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

    public ShipMethod getShipMethod() {
        return shipMethod;
    }

    public Orders shipMethod(ShipMethod shipMethod) {
        this.shipMethod = shipMethod;
        return this;
    }

    public void setShipMethod(ShipMethod shipMethod) {
        this.shipMethod = shipMethod;
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
            ", dueDate='" + getDueDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", subTotal=" + getSubTotal() +
            ", taxAmount=" + getTaxAmount() +
            ", frieight=" + getFrieight() +
            ", totalDue=" + getTotalDue() +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", sellerRating=" + getSellerRating() +
            ", sellerReview='" + getSellerReview() + "'" +
            ", deliveryRating=" + getDeliveryRating() +
            ", deliveryReview='" + getDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            ", orderLineString='" + getOrderLineString() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
