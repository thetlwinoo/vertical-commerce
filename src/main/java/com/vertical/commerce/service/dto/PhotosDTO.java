package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Photos} entity.
 */
public class PhotosDTO implements Serializable {
    
    private Long id;

    private String blobId;

    @NotNull
    private String thumbnailUrl;

    @NotNull
    private String originalUrl;

    private String bannerTallUrl;

    private String bannerWideUrl;

    private String circleUrl;

    private String sharpenedUrl;

    private String squareUrl;

    private String watermarkUrl;

    private Integer priority;

    @NotNull
    private Boolean defaultInd;


    private Long stockItemId;
    
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getBannerTallUrl() {
        return bannerTallUrl;
    }

    public void setBannerTallUrl(String bannerTallUrl) {
        this.bannerTallUrl = bannerTallUrl;
    }

    public String getBannerWideUrl() {
        return bannerWideUrl;
    }

    public void setBannerWideUrl(String bannerWideUrl) {
        this.bannerWideUrl = bannerWideUrl;
    }

    public String getCircleUrl() {
        return circleUrl;
    }

    public void setCircleUrl(String circleUrl) {
        this.circleUrl = circleUrl;
    }

    public String getSharpenedUrl() {
        return sharpenedUrl;
    }

    public void setSharpenedUrl(String sharpenedUrl) {
        this.sharpenedUrl = sharpenedUrl;
    }

    public String getSquareUrl() {
        return squareUrl;
    }

    public void setSquareUrl(String squareUrl) {
        this.squareUrl = squareUrl;
    }

    public String getWatermarkUrl() {
        return watermarkUrl;
    }

    public void setWatermarkUrl(String watermarkUrl) {
        this.watermarkUrl = watermarkUrl;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
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
            ", stockItemId=" + getStockItemId() +
            "}";
    }
}
