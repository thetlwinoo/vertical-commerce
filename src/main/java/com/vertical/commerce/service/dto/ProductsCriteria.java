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

    private IntegerFilter totalStars;

    private IntegerFilter discountedPercentage;

    private BooleanFilter preferredInd;

    private BooleanFilter availableDeliveryInd;

    private BooleanFilter activeInd;

    private BooleanFilter questionsAboutProductInd;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private InstantFilter releaseDate;

    private InstantFilter availableDate;

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
        this.totalStars = other.totalStars == null ? null : other.totalStars.copy();
        this.discountedPercentage = other.discountedPercentage == null ? null : other.discountedPercentage.copy();
        this.preferredInd = other.preferredInd == null ? null : other.preferredInd.copy();
        this.availableDeliveryInd = other.availableDeliveryInd == null ? null : other.availableDeliveryInd.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.questionsAboutProductInd = other.questionsAboutProductInd == null ? null : other.questionsAboutProductInd.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.releaseDate = other.releaseDate == null ? null : other.releaseDate.copy();
        this.availableDate = other.availableDate == null ? null : other.availableDate.copy();
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

    public IntegerFilter getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(IntegerFilter totalStars) {
        this.totalStars = totalStars;
    }

    public IntegerFilter getDiscountedPercentage() {
        return discountedPercentage;
    }

    public void setDiscountedPercentage(IntegerFilter discountedPercentage) {
        this.discountedPercentage = discountedPercentage;
    }

    public BooleanFilter getPreferredInd() {
        return preferredInd;
    }

    public void setPreferredInd(BooleanFilter preferredInd) {
        this.preferredInd = preferredInd;
    }

    public BooleanFilter getAvailableDeliveryInd() {
        return availableDeliveryInd;
    }

    public void setAvailableDeliveryInd(BooleanFilter availableDeliveryInd) {
        this.availableDeliveryInd = availableDeliveryInd;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public BooleanFilter getQuestionsAboutProductInd() {
        return questionsAboutProductInd;
    }

    public void setQuestionsAboutProductInd(BooleanFilter questionsAboutProductInd) {
        this.questionsAboutProductInd = questionsAboutProductInd;
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
            Objects.equals(totalStars, that.totalStars) &&
            Objects.equals(discountedPercentage, that.discountedPercentage) &&
            Objects.equals(preferredInd, that.preferredInd) &&
            Objects.equals(availableDeliveryInd, that.availableDeliveryInd) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(questionsAboutProductInd, that.questionsAboutProductInd) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(availableDate, that.availableDate) &&
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
        totalStars,
        discountedPercentage,
        preferredInd,
        availableDeliveryInd,
        activeInd,
        questionsAboutProductInd,
        lastEditedBy,
        lastEditedWhen,
        releaseDate,
        availableDate,
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
                (totalStars != null ? "totalStars=" + totalStars + ", " : "") +
                (discountedPercentage != null ? "discountedPercentage=" + discountedPercentage + ", " : "") +
                (preferredInd != null ? "preferredInd=" + preferredInd + ", " : "") +
                (availableDeliveryInd != null ? "availableDeliveryInd=" + availableDeliveryInd + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (questionsAboutProductInd != null ? "questionsAboutProductInd=" + questionsAboutProductInd + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (releaseDate != null ? "releaseDate=" + releaseDate + ", " : "") +
                (availableDate != null ? "availableDate=" + availableDate + ", " : "") +
                (productDocumentId != null ? "productDocumentId=" + productDocumentId + ", " : "") +
                (stockItemListId != null ? "stockItemListId=" + stockItemListId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productBrandId != null ? "productBrandId=" + productBrandId + ", " : "") +
            "}";
    }

}
