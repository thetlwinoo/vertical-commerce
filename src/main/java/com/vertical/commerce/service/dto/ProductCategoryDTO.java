package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductCategory} entity.
 */
public class ProductCategoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @Lob
    private String cultureDetails;

    private String shortLabel;

    private Integer sortOrder;

    private String iconFont;

    private String iconPhoto;

    private Boolean justForYouInd;

    private Boolean showInNavInd;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long parentId;

    private String parentName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCultureDetails() {
        return cultureDetails;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIconFont() {
        return iconFont;
    }

    public void setIconFont(String iconFont) {
        this.iconFont = iconFont;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public Boolean isJustForYouInd() {
        return justForYouInd;
    }

    public void setJustForYouInd(Boolean justForYouInd) {
        this.justForYouInd = justForYouInd;
    }

    public Boolean isShowInNavInd() {
        return showInNavInd;
    }

    public void setShowInNavInd(Boolean showInNavInd) {
        this.showInNavInd = showInNavInd;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long productCategoryId) {
        this.parentId = productCategoryId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String productCategoryName) {
        this.parentName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductCategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", iconPhoto='" + getIconPhoto() + "'" +
            ", justForYouInd='" + isJustForYouInd() + "'" +
            ", showInNavInd='" + isShowInNavInd() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", parentId=" + getParentId() +
            ", parentName='" + getParentName() + "'" +
            "}";
    }
}
