package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Lob;
import com.vertical.commerce.domain.enumeration.OrderLineStatus;

/**
 * A DTO for the {@link com.vertical.commerce.domain.OrderLines} entity.
 */
public class OrderLinesDTO implements Serializable {
    
    private Long id;

    private String description;

    @NotNull
    private Integer quantity;

    private BigDecimal taxRate;

    private BigDecimal unitPrice;

    private BigDecimal unitPriceDiscount;

    private Integer pickedQuantity;

    private Instant pickingCompletedWhen;

    @NotNull
    private OrderLineStatus status;

    private Integer lineRating;

    @Lob
    private String lineReview;

    private String stockItemPhoto;

    private String reviewPhoto;

    private Instant customerReviewedOn;

    @Lob
    private String supplierResponse;

    private Instant supplierResponseOn;

    private Integer likeCount;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long stockItemId;

    private String stockItemName;

    private Long packageTypeId;

    private String packageTypeName;

    private Long supplierId;

    private String supplierName;

    private Long orderPackageId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPriceDiscount() {
        return unitPriceDiscount;
    }

    public void setUnitPriceDiscount(BigDecimal unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
    }

    public Integer getPickedQuantity() {
        return pickedQuantity;
    }

    public void setPickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(OrderLineStatus status) {
        this.status = status;
    }

    public Integer getLineRating() {
        return lineRating;
    }

    public void setLineRating(Integer lineRating) {
        this.lineRating = lineRating;
    }

    public String getLineReview() {
        return lineReview;
    }

    public void setLineReview(String lineReview) {
        this.lineReview = lineReview;
    }

    public String getStockItemPhoto() {
        return stockItemPhoto;
    }

    public void setStockItemPhoto(String stockItemPhoto) {
        this.stockItemPhoto = stockItemPhoto;
    }

    public String getReviewPhoto() {
        return reviewPhoto;
    }

    public void setReviewPhoto(String reviewPhoto) {
        this.reviewPhoto = reviewPhoto;
    }

    public Instant getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public void setCustomerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public String getSupplierResponse() {
        return supplierResponse;
    }

    public void setSupplierResponse(String supplierResponse) {
        this.supplierResponse = supplierResponse;
    }

    public Instant getSupplierResponseOn() {
        return supplierResponseOn;
    }

    public void setSupplierResponseOn(Instant supplierResponseOn) {
        this.supplierResponseOn = supplierResponseOn;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemsName) {
        this.stockItemName = stockItemsName;
    }

    public Long getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(Long packageTypesId) {
        this.packageTypeId = packageTypesId;
    }

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypesName) {
        this.packageTypeName = packageTypesName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getOrderPackageId() {
        return orderPackageId;
    }

    public void setOrderPackageId(Long orderPackagesId) {
        this.orderPackageId = orderPackagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLinesDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderLinesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLinesDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", taxRate=" + getTaxRate() +
            ", unitPrice=" + getUnitPrice() +
            ", unitPriceDiscount=" + getUnitPriceDiscount() +
            ", pickedQuantity=" + getPickedQuantity() +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", status='" + getStatus() + "'" +
            ", lineRating=" + getLineRating() +
            ", lineReview='" + getLineReview() + "'" +
            ", stockItemPhoto='" + getStockItemPhoto() + "'" +
            ", reviewPhoto='" + getReviewPhoto() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", supplierResponse='" + getSupplierResponse() + "'" +
            ", supplierResponseOn='" + getSupplierResponseOn() + "'" +
            ", likeCount=" + getLikeCount() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItemId=" + getStockItemId() +
            ", stockItemName='" + getStockItemName() + "'" +
            ", packageTypeId=" + getPackageTypeId() +
            ", packageTypeName='" + getPackageTypeName() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", orderPackageId=" + getOrderPackageId() +
            "}";
    }
}
