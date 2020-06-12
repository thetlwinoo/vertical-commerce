package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Campaign} entity.
 */
public class CampaignDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String shortLabel;

    private Integer sortOrder;

    private String iconFont;

    private String thumbnailUrl;


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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
        if (!(o instanceof CampaignDTO)) {
            return false;
        }

        return id != null && id.equals(((CampaignDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", iconId=" + getIconId() +
            ", iconThumbnailUrl='" + getIconThumbnailUrl() + "'" +
            "}";
    }
}
