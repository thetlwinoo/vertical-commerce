package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Photos} entity.
 */
public class PhotosDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String blobId;

    private Integer priority;

    private String uid;

    private Integer size;

    private String name;

    private String fileName;

    private String url;

    private String status;

    private String thumbUrl;

    private Integer percent;

    private String type;

    @NotNull
    private Boolean defaultInd;

    private Boolean activeFlag;

    private String lastModified;

    private Instant lastModifiedDate;


    private Long stockItemId;

    private Long supplierBannerId;

    private Long supplierDocumentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public Long getSupplierBannerId() {
        return supplierBannerId;
    }

    public void setSupplierBannerId(Long suppliersId) {
        this.supplierBannerId = suppliersId;
    }

    public Long getSupplierDocumentId() {
        return supplierDocumentId;
    }

    public void setSupplierDocumentId(Long suppliersId) {
        this.supplierDocumentId = suppliersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotosDTO)) {
            return false;
        }

        return id != null && id.equals(((PhotosDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotosDTO{" +
            "id=" + getId() +
            ", blobId='" + getBlobId() + "'" +
            ", priority=" + getPriority() +
            ", uid='" + getUid() + "'" +
            ", size=" + getSize() +
            ", name='" + getName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", url='" + getUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", thumbUrl='" + getThumbUrl() + "'" +
            ", percent=" + getPercent() +
            ", type='" + getType() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", stockItemId=" + getStockItemId() +
            ", supplierBannerId=" + getSupplierBannerId() +
            ", supplierDocumentId=" + getSupplierDocumentId() +
            "}";
    }
}
