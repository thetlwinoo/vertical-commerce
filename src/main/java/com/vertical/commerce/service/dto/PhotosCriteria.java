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

    private StringFilter thumbnailUrl;

    private StringFilter originalUrl;

    private StringFilter bannerTallUrl;

    private StringFilter bannerWideUrl;

    private StringFilter circleUrl;

    private StringFilter sharpenedUrl;

    private StringFilter squareUrl;

    private StringFilter watermarkUrl;

    private IntegerFilter priority;

    private BooleanFilter defaultInd;

    private LongFilter stockItemId;

    public PhotosCriteria() {
    }

    public PhotosCriteria(PhotosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.blobId = other.blobId == null ? null : other.blobId.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.originalUrl = other.originalUrl == null ? null : other.originalUrl.copy();
        this.bannerTallUrl = other.bannerTallUrl == null ? null : other.bannerTallUrl.copy();
        this.bannerWideUrl = other.bannerWideUrl == null ? null : other.bannerWideUrl.copy();
        this.circleUrl = other.circleUrl == null ? null : other.circleUrl.copy();
        this.sharpenedUrl = other.sharpenedUrl == null ? null : other.sharpenedUrl.copy();
        this.squareUrl = other.squareUrl == null ? null : other.squareUrl.copy();
        this.watermarkUrl = other.watermarkUrl == null ? null : other.watermarkUrl.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
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

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public StringFilter getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(StringFilter originalUrl) {
        this.originalUrl = originalUrl;
    }

    public StringFilter getBannerTallUrl() {
        return bannerTallUrl;
    }

    public void setBannerTallUrl(StringFilter bannerTallUrl) {
        this.bannerTallUrl = bannerTallUrl;
    }

    public StringFilter getBannerWideUrl() {
        return bannerWideUrl;
    }

    public void setBannerWideUrl(StringFilter bannerWideUrl) {
        this.bannerWideUrl = bannerWideUrl;
    }

    public StringFilter getCircleUrl() {
        return circleUrl;
    }

    public void setCircleUrl(StringFilter circleUrl) {
        this.circleUrl = circleUrl;
    }

    public StringFilter getSharpenedUrl() {
        return sharpenedUrl;
    }

    public void setSharpenedUrl(StringFilter sharpenedUrl) {
        this.sharpenedUrl = sharpenedUrl;
    }

    public StringFilter getSquareUrl() {
        return squareUrl;
    }

    public void setSquareUrl(StringFilter squareUrl) {
        this.squareUrl = squareUrl;
    }

    public StringFilter getWatermarkUrl() {
        return watermarkUrl;
    }

    public void setWatermarkUrl(StringFilter watermarkUrl) {
        this.watermarkUrl = watermarkUrl;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public BooleanFilter getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(BooleanFilter defaultInd) {
        this.defaultInd = defaultInd;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
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
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(originalUrl, that.originalUrl) &&
            Objects.equals(bannerTallUrl, that.bannerTallUrl) &&
            Objects.equals(bannerWideUrl, that.bannerWideUrl) &&
            Objects.equals(circleUrl, that.circleUrl) &&
            Objects.equals(sharpenedUrl, that.sharpenedUrl) &&
            Objects.equals(squareUrl, that.squareUrl) &&
            Objects.equals(watermarkUrl, that.watermarkUrl) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(stockItemId, that.stockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        blobId,
        thumbnailUrl,
        originalUrl,
        bannerTallUrl,
        bannerWideUrl,
        circleUrl,
        sharpenedUrl,
        squareUrl,
        watermarkUrl,
        priority,
        defaultInd,
        stockItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (blobId != null ? "blobId=" + blobId + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (originalUrl != null ? "originalUrl=" + originalUrl + ", " : "") +
                (bannerTallUrl != null ? "bannerTallUrl=" + bannerTallUrl + ", " : "") +
                (bannerWideUrl != null ? "bannerWideUrl=" + bannerWideUrl + ", " : "") +
                (circleUrl != null ? "circleUrl=" + circleUrl + ", " : "") +
                (sharpenedUrl != null ? "sharpenedUrl=" + sharpenedUrl + ", " : "") +
                (squareUrl != null ? "squareUrl=" + squareUrl + ", " : "") +
                (watermarkUrl != null ? "watermarkUrl=" + watermarkUrl + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
            "}";
    }

}
