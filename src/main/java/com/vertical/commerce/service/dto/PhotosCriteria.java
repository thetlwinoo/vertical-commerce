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
 * Criteria class for the {@link com.vertical.commerce.domain.Photos} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.PhotosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /photos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhotosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter blobId;

    private IntegerFilter priority;

    private StringFilter uid;

    private IntegerFilter size;

    private StringFilter name;

    private StringFilter fileName;

    private StringFilter url;

    private StringFilter status;

    private StringFilter thumbUrl;

    private IntegerFilter percent;

    private StringFilter type;

    private BooleanFilter defaultInd;

    private BooleanFilter activeFlag;

    private StringFilter lastModified;

    private InstantFilter lastModifiedDate;

    private LongFilter stockItemId;

    private LongFilter supplierBannerId;

    private LongFilter supplierDocumentId;

    public PhotosCriteria() {
    }

    public PhotosCriteria(PhotosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.blobId = other.blobId == null ? null : other.blobId.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.size = other.size == null ? null : other.size.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.thumbUrl = other.thumbUrl == null ? null : other.thumbUrl.copy();
        this.percent = other.percent == null ? null : other.percent.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.activeFlag = other.activeFlag == null ? null : other.activeFlag.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.supplierBannerId = other.supplierBannerId == null ? null : other.supplierBannerId.copy();
        this.supplierDocumentId = other.supplierDocumentId == null ? null : other.supplierDocumentId.copy();
    }

    @Override
    public PhotosCriteria copy() {
        return new PhotosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBlobId() {
        return blobId;
    }

    public void setBlobId(StringFilter blobId) {
        this.blobId = blobId;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public StringFilter getUid() {
        return uid;
    }

    public void setUid(StringFilter uid) {
        this.uid = uid;
    }

    public IntegerFilter getSize() {
        return size;
    }

    public void setSize(IntegerFilter size) {
        this.size = size;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(StringFilter thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public IntegerFilter getPercent() {
        return percent;
    }

    public void setPercent(IntegerFilter percent) {
        this.percent = percent;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public BooleanFilter getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(BooleanFilter defaultInd) {
        this.defaultInd = defaultInd;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
    }

    public StringFilter getLastModified() {
        return lastModified;
    }

    public void setLastModified(StringFilter lastModified) {
        this.lastModified = lastModified;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getSupplierBannerId() {
        return supplierBannerId;
    }

    public void setSupplierBannerId(LongFilter supplierBannerId) {
        this.supplierBannerId = supplierBannerId;
    }

    public LongFilter getSupplierDocumentId() {
        return supplierDocumentId;
    }

    public void setSupplierDocumentId(LongFilter supplierDocumentId) {
        this.supplierDocumentId = supplierDocumentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhotosCriteria that = (PhotosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(blobId, that.blobId) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(size, that.size) &&
            Objects.equals(name, that.name) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(url, that.url) &&
            Objects.equals(status, that.status) &&
            Objects.equals(thumbUrl, that.thumbUrl) &&
            Objects.equals(percent, that.percent) &&
            Objects.equals(type, that.type) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(supplierBannerId, that.supplierBannerId) &&
            Objects.equals(supplierDocumentId, that.supplierDocumentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        blobId,
        priority,
        uid,
        size,
        name,
        fileName,
        url,
        status,
        thumbUrl,
        percent,
        type,
        defaultInd,
        activeFlag,
        lastModified,
        lastModifiedDate,
        stockItemId,
        supplierBannerId,
        supplierDocumentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (blobId != null ? "blobId=" + blobId + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (size != null ? "size=" + size + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (thumbUrl != null ? "thumbUrl=" + thumbUrl + ", " : "") +
                (percent != null ? "percent=" + percent + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (supplierBannerId != null ? "supplierBannerId=" + supplierBannerId + ", " : "") +
                (supplierDocumentId != null ? "supplierDocumentId=" + supplierDocumentId + ", " : "") +
            "}";
    }

}
