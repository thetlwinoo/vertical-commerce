package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Products} entity.
 */
public class ProductsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String handle;

    private String productNumber;

    private String searchDetails;

    private Integer sellCount;

    @Lob
    private String stockItemString;

    private Integer totalWishlist;

    private Integer totalStars;

    private Integer discountedPercentage;

    private Boolean preferredInd;

    private Boolean availableDeliveryInd;

    private Boolean activeInd;

    private Boolean questionsAboutProductInd;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;

    @NotNull
    private Instant releaseDate;

    @NotNull
    private Instant availableDate;


    private Long productDocumentId;

    private Long supplierId;

    private String supplierName;

    private Long productCategoryId;

    private String productCategoryName;

    private Long productBrandId;

    private String productBrandName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getStockItemString() {
        return stockItemString;
    }

    public void setStockItemString(String stockItemString) {
        this.stockItemString = stockItemString;
    }

    public Integer getTotalWishlist() {
        return totalWishlist;
    }

    public void setTotalWishlist(Integer totalWishlist) {
        this.totalWishlist = totalWishlist;
    }

    public Integer getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(Integer totalStars) {
        this.totalStars = totalStars;
    }

    public Integer getDiscountedPercentage() {
        return discountedPercentage;
    }

    public void setDiscountedPercentage(Integer discountedPercentage) {
        this.discountedPercentage = discountedPercentage;
    }

    public Boolean isPreferredInd() {
        return preferredInd;
    }

    public void setPreferredInd(Boolean preferredInd) {
        this.preferredInd = preferredInd;
    }

    public Boolean isAvailableDeliveryInd() {
        return availableDeliveryInd;
    }

    public void setAvailableDeliveryInd(Boolean availableDeliveryInd) {
        this.availableDeliveryInd = availableDeliveryInd;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public Boolean isQuestionsAboutProductInd() {
        return questionsAboutProductInd;
    }

    public void setQuestionsAboutProductInd(Boolean questionsAboutProductInd) {
        this.questionsAboutProductInd = questionsAboutProductInd;
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

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Instant getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Instant availableDate) {
        this.availableDate = availableDate;
    }

    public Long getProductDocumentId() {
        return productDocumentId;
    }

    public void setProductDocumentId(Long productDocumentId) {
        this.productDocumentId = productDocumentId;
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

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(Long productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", sellCount=" + getSellCount() +
            ", stockItemString='" + getStockItemString() + "'" +
            ", totalWishlist=" + getTotalWishlist() +
            ", totalStars=" + getTotalStars() +
            ", discountedPercentage=" + getDiscountedPercentage() +
            ", preferredInd='" + isPreferredInd() + "'" +
            ", availableDeliveryInd='" + isAvailableDeliveryInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", questionsAboutProductInd='" + isQuestionsAboutProductInd() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", availableDate='" + getAvailableDate() + "'" +
            ", productDocumentId=" + getProductDocumentId() +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            ", productBrandId=" + getProductBrandId() +
            ", productBrandName='" + getProductBrandName() + "'" +
            "}";
    }
}
