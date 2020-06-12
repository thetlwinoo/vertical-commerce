package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductCategory} entity.
 */
public class ProductCategoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String shortLabel;

    private Integer sortOrder;

    private String iconFont;

    private Boolean justForYouInd;

    private Boolean showInNavInd;

    private Boolean activeInd;


    private Long parentId;

    private String parentName;

    private Long iconId;

    private String iconThumbnailUrl;
    
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

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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

    public Long getIconId() {
        return iconId;
    }

    public void setIconId(Long photosId) {
        this.iconId = photosId;
    }

    public String getIconThumbnailUrl() {
        return iconThumbnailUrl;
    }

    public void setIconThumbnailUrl(String photosThumbnailUrl) {
        this.iconThumbnailUrl = photosThumbnailUrl;
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
            ", shortLabel='" + getShortLabel() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", justForYouInd='" + isJustForYouInd() + "'" +
            ", showInNavInd='" + isShowInNavInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", parentId=" + getParentId() +
            ", parentName='" + getParentName() + "'" +
            ", iconId=" + getIconId() +
            ", iconThumbnailUrl='" + getIconThumbnailUrl() + "'" +
            "}";
    }
}
