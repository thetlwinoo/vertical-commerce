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
 * Criteria class for the {@link com.vertical.commerce.domain.Receipts} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.ReceiptsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /receipts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReceiptsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter receiptNo;

    private InstantFilter issuedDate;

    private IntegerFilter printCount;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter orderId;

    private LongFilter invoiceId;

    public ReceiptsCriteria() {
    }

    public ReceiptsCriteria(ReceiptsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.receiptNo = other.receiptNo == null ? null : other.receiptNo.copy();
        this.issuedDate = other.issuedDate == null ? null : other.issuedDate.copy();
        this.printCount = other.printCount == null ? null : other.printCount.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
    }

    @Override
    public ReceiptsCriteria copy() {
        return new ReceiptsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(StringFilter receiptNo) {
        this.receiptNo = receiptNo;
    }

    public InstantFilter getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(InstantFilter issuedDate) {
        this.issuedDate = issuedDate;
    }

    public IntegerFilter getPrintCount() {
        return printCount;
    }

    public void setPrintCount(IntegerFilter printCount) {
        this.printCount = printCount;
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

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReceiptsCriteria that = (ReceiptsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(receiptNo, that.receiptNo) &&
            Objects.equals(issuedDate, that.issuedDate) &&
            Objects.equals(printCount, that.printCount) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        receiptNo,
        issuedDate,
        printCount,
        lastEditedBy,
        lastEditedWhen,
        orderId,
        invoiceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (receiptNo != null ? "receiptNo=" + receiptNo + ", " : "") +
                (issuedDate != null ? "issuedDate=" + issuedDate + ", " : "") +
                (printCount != null ? "printCount=" + printCount + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            "}";
    }

}
