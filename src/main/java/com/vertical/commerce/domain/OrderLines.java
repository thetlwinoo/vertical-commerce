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

import com.vertical.commerce.domain.enumeration.OrderLineStatus;

/**
 * A OrderLines.
 */
@Entity
@Table(name = "order_lines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "unit_price_discount", precision = 21, scale = 2)
    private BigDecimal unitPriceDiscount;

    @Column(name = "line_total", precision = 21, scale = 2)
    private BigDecimal lineTotal;

    @Column(name = "tax_rate", precision = 21, scale = 2)
    private BigDecimal taxRate;

    @Column(name = "picked_quantity")
    private Integer pickedQuantity;

    @Column(name = "picking_completed_when")
    private Instant pickingCompletedWhen;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderLineStatus status;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "line_rating")
    private Integer lineRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "line_review")
    private String lineReview;

    @Column(name = "customer_reviewed_on")
    private Instant customerReviewedOn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "supplier_response")
    private String supplierResponse;

    @Column(name = "supplier_response_on")
    private Instant supplierResponseOn;

    @Column(name = "like_count")
    private Integer likeCount;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderLines", allowSetters = true)
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderLines", allowSetters = true)
    private PackageTypes packageType;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderLines", allowSetters = true)
    private Photos reviewImage;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderLineLists", allowSetters = true)
    private Orders order;

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

    public OrderLines quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public OrderLines description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public OrderLines unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPriceDiscount() {
        return unitPriceDiscount;
    }

    public OrderLines unitPriceDiscount(BigDecimal unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
        return this;
    }

    public void setUnitPriceDiscount(BigDecimal unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public OrderLines lineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
        return this;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public OrderLines taxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getPickedQuantity() {
        return pickedQuantity;
    }

    public OrderLines pickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
        return this;
    }

    public void setPickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public OrderLines pickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
        return this;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderLineStatus getStatus() {
        return status;
    }

    public OrderLines status(OrderLineStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderLineStatus status) {
        this.status = status;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public OrderLines thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getLineRating() {
        return lineRating;
    }

    public OrderLines lineRating(Integer lineRating) {
        this.lineRating = lineRating;
        return this;
    }

    public void setLineRating(Integer lineRating) {
        this.lineRating = lineRating;
    }

    public String getLineReview() {
        return lineReview;
    }

    public OrderLines lineReview(String lineReview) {
        this.lineReview = lineReview;
        return this;
    }

    public void setLineReview(String lineReview) {
        this.lineReview = lineReview;
    }

    public Instant getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public OrderLines customerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
        return this;
    }

    public void setCustomerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public String getSupplierResponse() {
        return supplierResponse;
    }

    public OrderLines supplierResponse(String supplierResponse) {
        this.supplierResponse = supplierResponse;
        return this;
    }

    public void setSupplierResponse(String supplierResponse) {
        this.supplierResponse = supplierResponse;
    }

    public Instant getSupplierResponseOn() {
        return supplierResponseOn;
    }

    public OrderLines supplierResponseOn(Instant supplierResponseOn) {
        this.supplierResponseOn = supplierResponseOn;
        return this;
    }

    public void setSupplierResponseOn(Instant supplierResponseOn) {
        this.supplierResponseOn = supplierResponseOn;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public OrderLines likeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public OrderLines lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public OrderLines lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public OrderLines supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public OrderLines stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public PackageTypes getPackageType() {
        return packageType;
    }

    public OrderLines packageType(PackageTypes packageTypes) {
        this.packageType = packageTypes;
        return this;
    }

    public void setPackageType(PackageTypes packageTypes) {
        this.packageType = packageTypes;
    }

    public Photos getReviewImage() {
        return reviewImage;
    }

    public OrderLines reviewImage(Photos photos) {
        this.reviewImage = photos;
        return this;
    }

    public void setReviewImage(Photos photos) {
        this.reviewImage = photos;
    }

    public Orders getOrder() {
        return order;
    }

    public OrderLines order(Orders orders) {
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
        if (!(o instanceof OrderLines)) {
            return false;
        }
        return id != null && id.equals(((OrderLines) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLines{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", description='" + getDescription() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", unitPriceDiscount=" + getUnitPriceDiscount() +
            ", lineTotal=" + getLineTotal() +
            ", taxRate=" + getTaxRate() +
            ", pickedQuantity=" + getPickedQuantity() +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", status='" + getStatus() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", lineRating=" + getLineRating() +
            ", lineReview='" + getLineReview() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", supplierResponse='" + getSupplierResponse() + "'" +
            ", supplierResponseOn='" + getSupplierResponseOn() + "'" +
            ", likeCount=" + getLikeCount() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
