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
 * Criteria class for the {@link com.vertical.commerce.domain.UploadTransactions} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.UploadTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /upload-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UploadTransactionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fileName;

    private IntegerFilter status;

    private StringFilter generatedCode;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter supplierId;

    private LongFilter actionTypeId;

    public UploadTransactionsCriteria() {
    }

    public UploadTransactionsCriteria(UploadTransactionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.generatedCode = other.generatedCode == null ? null : other.generatedCode.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.actionTypeId = other.actionTypeId == null ? null : other.actionTypeId.copy();
    }

    @Override
    public UploadTransactionsCriteria copy() {
        return new UploadTransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public StringFilter getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(StringFilter generatedCode) {
        this.generatedCode = generatedCode;
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

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(LongFilter actionTypeId) {
        this.actionTypeId = actionTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UploadTransactionsCriteria that = (UploadTransactionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(generatedCode, that.generatedCode) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(actionTypeId, that.actionTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fileName,
        status,
        generatedCode,
        lastEditedBy,
        lastEditedWhen,
        supplierId,
        actionTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UploadTransactionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (generatedCode != null ? "generatedCode=" + generatedCode + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (actionTypeId != null ? "actionTypeId=" + actionTypeId + ", " : "") +
            "}";
    }

}
