package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.Products} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ProductsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter handle;

    private StringFilter searchDetails;

    private StringFilter productNumber;

    private IntegerFilter sellCount;

    private IntegerFilter totalWishlist;

    private IntegerFilter overallRating;

    private BooleanFilter preferredInd;

    private BooleanFilter freeShippingInd;

    private BooleanFilter madeInMyanmarInd;

    private BooleanFilter questionsAboutProductInd;

    private InstantFilter releaseDate;

    private InstantFilter availableDate;

    private BooleanFilter activeFlag;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter productDocumentId;

    private LongFilter stockItemListId;

    private LongFilter supplierId;

    private LongFilter productCategoryId;

    private LongFilter productBrandId;

    public ProductsCriteria() {
    }

    public ProductsCriteria(ProductsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.handle = other.handle == null ? null : other.handle.copy();
        this.searchDetails = other.searchDetails == null ? null : other.searchDetails.copy();
        this.productNumber = other.productNumber == null ? null : other.productNumber.copy();
        this.sellCount = other.sellCount == null ? null : other.sellCount.copy();
        this.totalWishlist = other.totalWishlist == null ? null : other.totalWishlist.copy();
        this.overallRating = other.overallRating == null ? null : other.overallRating.copy();
        this.preferredInd = other.preferredInd == null ? null : other.preferredInd.copy();
        this.freeShippingInd = other.freeShippingInd == null ? null : other.freeShippingInd.copy();
        this.madeInMyanmarInd = other.madeInMyanmarInd == null ? null : other.madeInMyanmarInd.copy();
        this.questionsAboutProductInd = other.questionsAboutProductInd == null ? null : other.questionsAboutProductInd.copy();
        this.releaseDate = other.releaseDate == null ? null : other.releaseDate.copy();
        this.availableDate = other.availableDate == null ? null : other.availableDate.copy();
        this.activeFlag = other.activeFlag == null ? null : other.activeFlag.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.productDocumentId = other.productDocumentId == null ? null : other.productDocumentId.copy();
        this.stockItemListId = other.stockItemListId == null ? null : other.stockItemListId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productBrandId = other.productBrandId == null ? null : other.productBrandId.copy();
    }

    @Override
    public ProductsCriteria copy() {
        return new ProductsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getHandle() {
        return handle;
    }

    public void setHandle(StringFilter handle) {
        this.handle = handle;
    }

    public StringFilter getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(StringFilter searchDetails) {
        this.searchDetails = searchDetails;
    }

    public StringFilter getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(StringFilter productNumber) {
        this.productNumber = productNumber;
    }

    public IntegerFilter getSellCount() {
        return sellCount;
    }

    public void setSellCount(IntegerFilter sellCount) {
        this.sellCount = sellCount;
    }

    public IntegerFilter getTotalWishlist() {
        return totalWishlist;
    }

    public void setTotalWishlist(IntegerFilter totalWishlist) {
        this.totalWishlist = totalWishlist;
    }

    public IntegerFilter getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(IntegerFilter overallRating) {
        this.overallRating = overallRating;
    }

    public BooleanFilter getPreferredInd() {
        return preferredInd;
    }

    public void setPreferredInd(BooleanFilter preferredInd) {
        this.preferredInd = preferredInd;
    }

    public BooleanFilter getFreeShippingInd() {
        return freeShippingInd;
    }

    public void setFreeShippingInd(BooleanFilter freeShippingInd) {
        this.freeShippingInd = freeShippingInd;
    }

    public BooleanFilter getMadeInMyanmarInd() {
        return madeInMyanmarInd;
    }

    public void setMadeInMyanmarInd(BooleanFilter madeInMyanmarInd) {
        this.madeInMyanmarInd = madeInMyanmarInd;
    }

    public BooleanFilter getQuestionsAboutProductInd() {
        return questionsAboutProductInd;
    }

    public void setQuestionsAboutProductInd(BooleanFilter questionsAboutProductInd) {
        this.questionsAboutProductInd = questionsAboutProductInd;
    }

    public InstantFilter getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(InstantFilter releaseDate) {
        this.releaseDate = releaseDate;
    }

    public InstantFilter getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(InstantFilter availableDate) {
        this.availableDate = availableDate;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getProductDocumentId() {
        return productDocumentId;
    }

    public void setProductDocumentId(LongFilter productDocumentId) {
        this.productDocumentId = productDocumentId;
    }

    public LongFilter getStockItemListId() {
        return stockItemListId;
    }

    public void setStockItemListId(LongFilter stockItemListId) {
        this.stockItemListId = stockItemListId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(LongFilter productBrandId) {
        this.productBrandId = productBrandId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductsCriteria that = (ProductsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(handle, that.handle) &&
            Objects.equals(searchDetails, that.searchDetails) &&
            Objects.equals(productNumber, that.productNumber) &&
            Objects.equals(sellCount, that.sellCount) &&
            Objects.equals(totalWishlist, that.totalWishlist) &&
            Objects.equals(overallRating, that.overallRating) &&
            Objects.equals(preferredInd, that.preferredInd) &&
            Objects.equals(freeShippingInd, that.freeShippingInd) &&
            Objects.equals(madeInMyanmarInd, that.madeInMyanmarInd) &&
            Objects.equals(questionsAboutProductInd, that.questionsAboutProductInd) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(availableDate, that.availableDate) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(productDocumentId, that.productDocumentId) &&
            Objects.equals(stockItemListId, that.stockItemListId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productBrandId, that.productBrandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        handle,
        searchDetails,
        productNumber,
        sellCount,
        totalWishlist,
        overallRating,
        preferredInd,
        freeShippingInd,
        madeInMyanmarInd,
        questionsAboutProductInd,
        releaseDate,
        availableDate,
        activeFlag,
        lastEditedBy,
        lastEditedWhen,
        validFrom,
        validTo,
        productDocumentId,
        stockItemListId,
        supplierId,
        productCategoryId,
        productBrandId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (handle != null ? "handle=" + handle + ", " : "") +
                (searchDetails != null ? "searchDetails=" + searchDetails + ", " : "") +
                (productNumber != null ? "productNumber=" + productNumber + ", " : "") +
                (sellCount != null ? "sellCount=" + sellCount + ", " : "") +
                (totalWishlist != null ? "totalWishlist=" + totalWishlist + ", " : "") +
                (overallRating != null ? "overallRating=" + overallRating + ", " : "") +
                (preferredInd != null ? "preferredInd=" + preferredInd + ", " : "") +
                (freeShippingInd != null ? "freeShippingInd=" + freeShippingInd + ", " : "") +
                (madeInMyanmarInd != null ? "madeInMyanmarInd=" + madeInMyanmarInd + ", " : "") +
                (questionsAboutProductInd != null ? "questionsAboutProductInd=" + questionsAboutProductInd + ", " : "") +
                (releaseDate != null ? "releaseDate=" + releaseDate + ", " : "") +
                (availableDate != null ? "availableDate=" + availableDate + ", " : "") +
                (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (productDocumentId != null ? "productDocumentId=" + productDocumentId + ", " : "") +
                (stockItemListId != null ? "stockItemListId=" + stockItemListId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productBrandId != null ? "productBrandId=" + productBrandId + ", " : "") +
            "}";
    }

}
