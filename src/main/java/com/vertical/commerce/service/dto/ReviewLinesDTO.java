package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ReviewLines} entity.
 */
public class ReviewLinesDTO implements Serializable {
    
    private Long id;

    private Integer stockItemRating;

    @Lob
    private String stockItemReview;

    private Instant customerReviewedOn;

    private Boolean completedReviewLine;

    @Lob
    private String supplierResponse;

    private Instant supplierResponseOn;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long reviewImageId;

    private String reviewImageThumbnailUrl;

    private Long reviewId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockItemRating() {
        return stockItemRating;
    }

    public void setStockItemRating(Integer stockItemRating) {
        this.stockItemRating = stockItemRating;
    }

    public String getStockItemReview() {
        return stockItemReview;
    }

    public void setStockItemReview(String stockItemReview) {
        this.stockItemReview = stockItemReview;
    }

    public Instant getCustomerReviewedOn() {
        return customerReviewedOn;
    }

    public void setCustomerReviewedOn(Instant customerReviewedOn) {
        this.customerReviewedOn = customerReviewedOn;
    }

    public Boolean isCompletedReviewLine() {
        return completedReviewLine;
    }

    public void setCompletedReviewLine(Boolean completedReviewLine) {
        this.completedReviewLine = completedReviewLine;
    }

    public String getSupplierResponse() {
        return supplierResponse;
    }

    public void setSupplierResponse(String supplierResponse) {
        this.supplierResponse = supplierResponse;
    }

    public Instant getSupplierResponseOn() {
        return supplierResponseOn;
    }

    public void setSupplierResponseOn(Instant supplierResponseOn) {
        this.supplierResponseOn = supplierResponseOn;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getReviewImageId() {
        return reviewImageId;
    }

    public void setReviewImageId(Long photosId) {
        this.reviewImageId = photosId;
    }

    public String getReviewImageThumbnailUrl() {
        return reviewImageThumbnailUrl;
    }

    public void setReviewImageThumbnailUrl(String photosThumbnailUrl) {
        this.reviewImageThumbnailUrl = photosThumbnailUrl;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewsId) {
        this.reviewId = reviewsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewLinesDTO)) {
            return false;
        }

        return id != null && id.equals(((ReviewLinesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewLinesDTO{" +
            "id=" + getId() +
            ", stockItemRating=" + getStockItemRating() +
            ", stockItemReview='" + getStockItemReview() + "'" +
            ", customerReviewedOn='" + getCustomerReviewedOn() + "'" +
            ", completedReviewLine='" + isCompletedReviewLine() + "'" +
            ", supplierResponse='" + getSupplierResponse() + "'" +
            ", supplierResponseOn='" + getSupplierResponseOn() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", reviewImageId=" + getReviewImageId() +
            ", reviewImageThumbnailUrl='" + getReviewImageThumbnailUrl() + "'" +
            ", reviewId=" + getReviewId() +
            "}";
    }
}
