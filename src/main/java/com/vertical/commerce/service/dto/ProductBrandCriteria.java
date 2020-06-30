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

/**
 * Criteria class for the {@link com.vertical.commerce.domain.ProductBrand} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ProductBrandResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-brands?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductBrandCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter shortLabel;

    private IntegerFilter sortOrder;

    private StringFilter iconFont;

    private StringFilter thumbnailUrl;

    private LongFilter supplierId;

    private LongFilter iconId;

    public ProductBrandCriteria() {
    }

    public ProductBrandCriteria(ProductBrandCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortLabel = other.shortLabel == null ? null : other.shortLabel.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.iconFont = other.iconFont == null ? null : other.iconFont.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.iconId = other.iconId == null ? null : other.iconId.copy();
    }

    @Override
    public ProductBrandCriteria copy() {
        return new ProductBrandCriteria(this);
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

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getIconId() {
        return iconId;
    }

    public void setIconId(LongFilter iconId) {
        this.iconId = iconId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductBrandCriteria that = (ProductBrandCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortLabel, that.shortLabel) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(iconFont, that.iconFont) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(iconId, that.iconId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        shortLabel,
        sortOrder,
        iconFont,
        thumbnailUrl,
        supplierId,
        iconId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductBrandCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortLabel != null ? "shortLabel=" + shortLabel + ", " : "") +
                (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
                (iconFont != null ? "iconFont=" + iconFont + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (iconId != null ? "iconId=" + iconId + ", " : "") +
            "}";
    }

}