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
 * Criteria class for the {@link com.vertical.commerce.domain.ProductCategory} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ProductCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter shortLabel;

    private IntegerFilter sortOrder;

    private StringFilter iconFont;

    private StringFilter iconPhoto;

    private BooleanFilter justForYouInd;

    private BooleanFilter showInNavInd;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter parentId;

    public ProductCategoryCriteria() {
    }

    public ProductCategoryCriteria(ProductCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortLabel = other.shortLabel == null ? null : other.shortLabel.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.iconFont = other.iconFont == null ? null : other.iconFont.copy();
        this.iconPhoto = other.iconPhoto == null ? null : other.iconPhoto.copy();
        this.justForYouInd = other.justForYouInd == null ? null : other.justForYouInd.copy();
        this.showInNavInd = other.showInNavInd == null ? null : other.showInNavInd.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public ProductCategoryCriteria copy() {
        return new ProductCategoryCriteria(this);
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

    public StringFilter getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(StringFilter shortLabel) {
        this.shortLabel = shortLabel;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public StringFilter getIconFont() {
        return iconFont;
    }

    public void setIconFont(StringFilter iconFont) {
        this.iconFont = iconFont;
    }

    public StringFilter getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(StringFilter iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public BooleanFilter getJustForYouInd() {
        return justForYouInd;
    }

    public void setJustForYouInd(BooleanFilter justForYouInd) {
        this.justForYouInd = justForYouInd;
    }

    public BooleanFilter getShowInNavInd() {
        return showInNavInd;
    }

    public void setShowInNavInd(BooleanFilter showInNavInd) {
        this.showInNavInd = showInNavInd;
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

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCategoryCriteria that = (ProductCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortLabel, that.shortLabel) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(iconFont, that.iconFont) &&
            Objects.equals(iconPhoto, that.iconPhoto) &&
            Objects.equals(justForYouInd, that.justForYouInd) &&
            Objects.equals(showInNavInd, that.showInNavInd) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        shortLabel,
        sortOrder,
        iconFont,
        iconPhoto,
        justForYouInd,
        showInNavInd,
        validFrom,
        validTo,
        parentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortLabel != null ? "shortLabel=" + shortLabel + ", " : "") +
                (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
                (iconFont != null ? "iconFont=" + iconFont + ", " : "") +
                (iconPhoto != null ? "iconPhoto=" + iconPhoto + ", " : "") +
                (justForYouInd != null ? "justForYouInd=" + justForYouInd + ", " : "") +
                (showInNavInd != null ? "showInNavInd=" + showInNavInd + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
