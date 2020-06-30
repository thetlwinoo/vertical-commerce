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
 * A OrderPackages.
 */
@Entity
@Table(name = "order_packages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderPackages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Column(name = "comments")
    private String comments;

    @Column(name = "delivery_instructions")
    private String deliveryInstructions;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "package_shipping_fee", precision = 21, scale = 2)
    private BigDecimal packageShippingFee;

    @Column(name = "package_shipping_fee_discount", precision = 21, scale = 2)
    private BigDecimal packageShippingFeeDiscount;

    @Column(name = "package_price", precision = 21, scale = 2)
    private BigDecimal packagePrice;

    @Column(name = "package_sub_total", precision = 21, scale = 2)
    private BigDecimal packageSubTotal;

    @Column(name = "package_tax_amount", precision = 21, scale = 2)
    private BigDecimal packageTaxAmount;

    @Column(name = "package_voucher_discount", precision = 21, scale = 2)
    private BigDecimal packageVoucherDiscount;

    @Column(name = "package_promotion_discount", precision = 21, scale = 2)
    private BigDecimal packagePromotionDiscount;

    @Column(name = "picking_completed_when")
    private Instant pickingCompletedWhen;

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
    @Column(name = "order_package_details")
    private String orderPackageDetails;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "orderPackage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OrderLines> orderLineLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "orderPackages", allowSetters = true)
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderPackages", allowSetters = true)
    private DeliveryMethods deliveryMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderPackageLists", allowSetters = true)
    private SpecialDeals specialDeals;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderPackageLists", allowSetters = true)
    private Orders order;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public OrderPackages expectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getComments() {
        return comments;
    }

    public OrderPackages comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public OrderPackages deliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
        return this;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public OrderPackages internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public BigDecimal getPackageShippingFee() {
        return packageShippingFee;
    }

    public OrderPackages packageShippingFee(BigDecimal packageShippingFee) {
        this.packageShippingFee = packageShippingFee;
        return this;
    }

    public void setPackageShippingFee(BigDecimal packageShippingFee) {
        this.packageShippingFee = packageShippingFee;
    }

    public BigDecimal getPackageShippingFeeDiscount() {
        return packageShippingFeeDiscount;
    }

    public OrderPackages packageShippingFeeDiscount(BigDecimal packageShippingFeeDiscount) {
        this.packageShippingFeeDiscount = packageShippingFeeDiscount;
        return this;
    }

    public void setPackageShippingFeeDiscount(BigDecimal packageShippingFeeDiscount) {
        this.packageShippingFeeDiscount = packageShippingFeeDiscount;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public OrderPackages packagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
        return this;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public BigDecimal getPackageSubTotal() {
        return packageSubTotal;
    }

    public OrderPackages packageSubTotal(BigDecimal packageSubTotal) {
        this.packageSubTotal = packageSubTotal;
        return this;
    }

    public void setPackageSubTotal(BigDecimal packageSubTotal) {
        this.packageSubTotal = packageSubTotal;
    }

    public BigDecimal getPackageTaxAmount() {
        return packageTaxAmount;
    }

    public OrderPackages packageTaxAmount(BigDecimal packageTaxAmount) {
        this.packageTaxAmount = packageTaxAmount;
        return this;
    }

    public void setPackageTaxAmount(BigDecimal packageTaxAmount) {
        this.packageTaxAmount = packageTaxAmount;
    }

    public BigDecimal getPackageVoucherDiscount() {
        return packageVoucherDiscount;
    }

    public OrderPackages packageVoucherDiscount(BigDecimal packageVoucherDiscount) {
        this.packageVoucherDiscount = packageVoucherDiscount;
        return this;
    }

    public void setPackageVoucherDiscount(BigDecimal packageVoucherDiscount) {
        this.packageVoucherDiscount = packageVoucherDiscount;
    }

    public BigDecimal getPackagePromotionDiscount() {
        return packagePromotionDiscount;
    }

    public OrderPackages packagePromotionDiscount(BigDecimal packagePromotionDiscount) {
        this.packagePromotionDiscount = packagePromotionDiscount;
        return this;
    }

    public void setPackagePromotionDiscount(BigDecimal packagePromotionDiscount) {
        this.packagePromotionDiscount = packagePromotionDiscount;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public OrderPackages pickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
        return this;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public Instant getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public OrderPackages customerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
        return this;
    }

    public void setCustomerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public OrderPackages sellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
        return this;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerReview() {
        return sellerReview;
    }

    public OrderPackages sellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
        return this;
    }

    public void setSellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
    }

    public Integer getDeliveryRating() {
        return deliveryRating;
    }

    public OrderPackages deliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
        return this;
    }

    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryReview() {
        return deliveryReview;
    }

    public OrderPackages deliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
        return this;
    }

    public void setDeliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public Boolean isReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public OrderPackages reviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
        return this;
    }

    public void setReviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public Boolean isCompletedReview() {
        return completedReview;
    }

    public OrderPackages completedReview(Boolean completedReview) {
        this.completedReview = completedReview;
        return this;
    }

    public void setCompletedReview(Boolean completedReview) {
        this.completedReview = completedReview;
    }

    public String getOrderPackageDetails() {
        return orderPackageDetails;
    }

    public OrderPackages orderPackageDetails(String orderPackageDetails) {
        this.orderPackageDetails = orderPackageDetails;
        return this;
    }

    public void setOrderPackageDetails(String orderPackageDetails) {
        this.orderPackageDetails = orderPackageDetails;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public OrderPackages lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public OrderPackages lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Set<OrderLines> getOrderLineLists() {
        return orderLineLists;
    }

    public OrderPackages orderLineLists(Set<OrderLines> orderLines) {
        this.orderLineLists = orderLines;
        return this;
    }

    public OrderPackages addOrderLineList(OrderLines orderLines) {
        this.orderLineLists.add(orderLines);
        orderLines.setOrderPackage(this);
        return this;
    }

    public OrderPackages removeOrderLineList(OrderLines orderLines) {
        this.orderLineLists.remove(orderLines);
        orderLines.setOrderPackage(null);
        return this;
    }

    public void setOrderLineLists(Set<OrderLines> orderLines) {
        this.orderLineLists = orderLines;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public OrderPackages supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public OrderPackages deliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
        return this;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
    }

    public SpecialDeals getSpecialDeals() {
        return specialDeals;
    }

    public OrderPackages specialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
        return this;
    }

    public void setSpecialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
    }

    public Orders getOrder() {
        return order;
    }

    public OrderPackages order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPackages)) {
            return false;
        }
        return id != null && id.equals(((OrderPackages) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderPackages{" +
            "id=" + getId() +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", packageShippingFee=" + getPackageShippingFee() +
            ", packageShippingFeeDiscount=" + getPackageShippingFeeDiscount() +
            ", packagePrice=" + getPackagePrice() +
            ", packageSubTotal=" + getPackageSubTotal() +
            ", packageTaxAmount=" + getPackageTaxAmount() +
            ", packageVoucherDiscount=" + getPackageVoucherDiscount() +
            ", packagePromotionDiscount=" + getPackagePromotionDiscount() +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", sellerRating=" + getSellerRating() +
            ", sellerReview='" + getSellerReview() + "'" +
            ", deliveryRating=" + getDeliveryRating() +
            ", deliveryReview='" + getDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            ", orderPackageDetails='" + getOrderPackageDetails() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
