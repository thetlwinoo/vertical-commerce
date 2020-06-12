package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

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

    @Column(name = "blob_id")
    private String blobId;

    @NotNull
    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @NotNull
    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "banner_tall_url")
    private String bannerTallUrl;

    @Column(name = "banner_wide_url")
    private String bannerWideUrl;

    @Column(name = "circle_url")
    private String circleUrl;

    @Column(name = "sharpened_url")
    private String sharpenedUrl;

    @Column(name = "square_url")
    private String squareUrl;

    @Column(name = "watermark_url")
    private String watermarkUrl;

    @Column(name = "priority")
    private Integer priority;

    @NotNull
    @Column(name = "default_ind", nullable = false)
    private Boolean defaultInd;

    @ManyToOne
    @JsonIgnoreProperties(value = "photoLists", allowSetters = true)
    private StockItems stockItem;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Photos thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Photos originalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getBannerTallUrl() {
        return bannerTallUrl;
    }

    public Photos bannerTallUrl(String bannerTallUrl) {
        this.bannerTallUrl = bannerTallUrl;
        return this;
    }

    public void setBannerTallUrl(String bannerTallUrl) {
        this.bannerTallUrl = bannerTallUrl;
    }

    public String getBannerWideUrl() {
        return bannerWideUrl;
    }

    public Photos bannerWideUrl(String bannerWideUrl) {
        this.bannerWideUrl = bannerWideUrl;
        return this;
    }

    public void setBannerWideUrl(String bannerWideUrl) {
        this.bannerWideUrl = bannerWideUrl;
    }

    public String getCircleUrl() {
        return circleUrl;
    }

    public Photos circleUrl(String circleUrl) {
        this.circleUrl = circleUrl;
        return this;
    }

    public void setCircleUrl(String circleUrl) {
        this.circleUrl = circleUrl;
    }

    public String getSharpenedUrl() {
        return sharpenedUrl;
    }

    public Photos sharpenedUrl(String sharpenedUrl) {
        this.sharpenedUrl = sharpenedUrl;
        return this;
    }

    public void setSharpenedUrl(String sharpenedUrl) {
        this.sharpenedUrl = sharpenedUrl;
    }

    public String getSquareUrl() {
        return squareUrl;
    }

    public Photos squareUrl(String squareUrl) {
        this.squareUrl = squareUrl;
        return this;
    }

    public void setSquareUrl(String squareUrl) {
        this.squareUrl = squareUrl;
    }

    public String getWatermarkUrl() {
        return watermarkUrl;
    }

    public Photos watermarkUrl(String watermarkUrl) {
        this.watermarkUrl = watermarkUrl;
        return this;
    }

    public void setWatermarkUrl(String watermarkUrl) {
        this.watermarkUrl = watermarkUrl;
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
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", originalUrl='" + getOriginalUrl() + "'" +
            ", bannerTallUrl='" + getBannerTallUrl() + "'" +
            ", bannerWideUrl='" + getBannerWideUrl() + "'" +
            ", circleUrl='" + getCircleUrl() + "'" +
            ", sharpenedUrl='" + getSharpenedUrl() + "'" +
            ", squareUrl='" + getSquareUrl() + "'" +
            ", watermarkUrl='" + getWatermarkUrl() + "'" +
            ", priority=" + getPriority() +
            ", defaultInd='" + isDefaultInd() + "'" +
            "}";
    }
}
