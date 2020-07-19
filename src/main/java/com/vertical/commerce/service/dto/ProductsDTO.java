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

    @Lob
    private String cultureDetails;

    private String handle;

    private String searchDetails;

    private String productNumber;

    private Integer sellCount;

    @Lob
    private String productDetails;

    private Integer totalWishlist;

    private Integer overallRating;

    private Boolean preferredInd;

    private Boolean freeShippingInd;

    private Boolean madeInMyanmarInd;

    private Boolean questionsAboutProductInd;

    @NotNull
    private Instant releaseDate;

    @NotNull
    private Instant availableDate;

    @NotNull
    private Boolean activeFlag;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


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

    public String getCultureDetails() {
        return cultureDetails;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public Integer getTotalWishlist() {
        return totalWishlist;
    }

    public void setTotalWishlist(Integer totalWishlist) {
        this.totalWishlist = totalWishlist;
    }

    public Integer getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Integer overallRating) {
        this.overallRating = overallRating;
    }

    public Boolean isPreferredInd() {
        return preferredInd;
    }

    public void setPreferredInd(Boolean preferredInd) {
        this.preferredInd = preferredInd;
    }

    public Boolean isFreeShippingInd() {
        return freeShippingInd;
    }

    public void setFreeShippingInd(Boolean freeShippingInd) {
        this.freeShippingInd = freeShippingInd;
    }

    public Boolean isMadeInMyanmarInd() {
        return madeInMyanmarInd;
    }

    public void setMadeInMyanmarInd(Boolean madeInMyanmarInd) {
        this.madeInMyanmarInd = madeInMyanmarInd;
    }

    public Boolean isQuestionsAboutProductInd() {
        return questionsAboutProductInd;
    }

    public void setQuestionsAboutProductInd(Boolean questionsAboutProductInd) {
        this.questionsAboutProductInd = questionsAboutProductInd;
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

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
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

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getProductDocumentId() {
        return productDocumentId;
    }

    public void setProductDocumentId(Long productDocumentsId) {
        this.productDocumentId = productDocumentsId;
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
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", handle='" + getHandle() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", sellCount=" + getSellCount() +
            ", productDetails='" + getProductDetails() + "'" +
            ", totalWishlist=" + getTotalWishlist() +
            ", overallRating=" + getOverallRating() +
            ", preferredInd='" + isPreferredInd() + "'" +
            ", freeShippingInd='" + isFreeShippingInd() + "'" +
            ", madeInMyanmarInd='" + isMadeInMyanmarInd() + "'" +
            ", questionsAboutProductInd='" + isQuestionsAboutProductInd() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", availableDate='" + getAvailableDate() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
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
