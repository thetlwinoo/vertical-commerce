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
 * Criteria class for the {@link com.vertical.commerce.domain.Questions} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.QuestionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter customerQuestionOn;

    private InstantFilter supplierAnswerOn;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter supplierId;

    private LongFilter personId;

    private LongFilter productId;

    public QuestionsCriteria() {
    }

    public QuestionsCriteria(QuestionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.customerQuestionOn = other.customerQuestionOn == null ? null : other.customerQuestionOn.copy();
        this.supplierAnswerOn = other.supplierAnswerOn == null ? null : other.supplierAnswerOn.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public QuestionsCriteria copy() {
        return new QuestionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCustomerQuestionOn() {
        return customerQuestionOn;
    }

    public void setCustomerQuestionOn(InstantFilter customerQuestionOn) {
        this.customerQuestionOn = customerQuestionOn;
    }

    public InstantFilter getSupplierAnswerOn() {
        return supplierAnswerOn;
    }

    public void setSupplierAnswerOn(InstantFilter supplierAnswerOn) {
        this.supplierAnswerOn = supplierAnswerOn;
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

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionsCriteria that = (QuestionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(customerQuestionOn, that.customerQuestionOn) &&
            Objects.equals(supplierAnswerOn, that.supplierAnswerOn) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        customerQuestionOn,
        supplierAnswerOn,
        validFrom,
        validTo,
        supplierId,
        personId,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (customerQuestionOn != null ? "customerQuestionOn=" + customerQuestionOn + ", " : "") +
                (supplierAnswerOn != null ? "supplierAnswerOn=" + supplierAnswerOn + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
