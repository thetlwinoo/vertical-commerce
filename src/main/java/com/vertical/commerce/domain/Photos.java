package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Photos.
 */
@Entity
@Table(name = "photos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Photos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "blob_id", nullable = false)
    private String blobId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "uid")
    private String uid;

    @Column(name = "size")
    private Integer size;

    @Column(name = "name")
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private String status;

    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "percent")
    private Integer percent;

    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "default_ind", nullable = false)
    private Boolean defaultInd;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "photoLists", allowSetters = true)
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "bannerLists", allowSetters = true)
    private Suppliers supplierBanner;

    @ManyToOne
    @JsonIgnoreProperties(value = "documentLists", allowSetters = true)
    private Suppliers supplierDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlobId() {
        return blobId;
    }

    public Photos blobId(String blobId) {
        this.blobId = blobId;
        return this;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    public Integer getPriority() {
        return priority;
    }

    public Photos priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUid() {
        return uid;
    }

    public Photos uid(String uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getSize() {
        return size;
    }

    public Photos size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Photos name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public Photos fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public Photos url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public Photos status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public Photos thumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getPercent() {
        return percent;
    }

    public Photos percent(Integer percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getType() {
        return type;
    }

    public Photos type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public Photos defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public Photos activeFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getLastModified() {
        return lastModified;
    }

    public Photos lastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Photos lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public Photos stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public Suppliers getSupplierBanner() {
        return supplierBanner;
    }

    public Photos supplierBanner(Suppliers suppliers) {
        this.supplierBanner = suppliers;
        return this;
    }

    public void setSupplierBanner(Suppliers suppliers) {
        this.supplierBanner = suppliers;
    }

    public Suppliers getSupplierDocument() {
        return supplierDocument;
    }

    public Photos supplierDocument(Suppliers suppliers) {
        this.supplierDocument = suppliers;
        return this;
    }

    public void setSupplierDocument(Suppliers suppliers) {
        this.supplierDocument = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photos)) {
            return false;
        }
        return id != null && id.equals(((Photos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photos{" +
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
            "}";
    }
}
