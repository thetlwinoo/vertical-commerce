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
 * Criteria class for the {@link com.vertical.commerce.domain.ProductDocumentsCulture} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ProductDocumentsCultureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-documents-cultures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductDocumentsCultureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter cultureId;

    private LongFilter productDocumentId;

    public ProductDocumentsCultureCriteria() {
    }

    public ProductDocumentsCultureCriteria(ProductDocumentsCultureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.cultureId = other.cultureId == null ? null : other.cultureId.copy();
        this.productDocumentId = other.productDocumentId == null ? null : other.productDocumentId.copy();
    }

    @Override
    public ProductDocumentsCultureCriteria copy() {
        return new ProductDocumentsCultureCriteria(this);
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

    public LongFilter getCultureId() {
        return cultureId;
    }

    public void setCultureId(LongFilter cultureId) {
        this.cultureId = cultureId;
    }

    public LongFilter getProductDocumentId() {
        return productDocumentId;
    }

    public void setProductDocumentId(LongFilter productDocumentId) {
        this.productDocumentId = productDocumentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductDocumentsCultureCriteria that = (ProductDocumentsCultureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(cultureId, that.cultureId) &&
            Objects.equals(productDocumentId, that.productDocumentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        cultureId,
        productDocumentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDocumentsCultureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (cultureId != null ? "cultureId=" + cultureId + ", " : "") +
                (productDocumentId != null ? "productDocumentId=" + productDocumentId + ", " : "") +
            "}";
    }

}
