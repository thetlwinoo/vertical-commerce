package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.PaymentMethods} entity.
 */
public class PaymentMethodsDTO implements Serializable {
    
    private Long id;

    private String name;

    private String code;

    private Boolean disabled;

    private Boolean activeInd;

    private Integer sortOrder;

    private String iconFont;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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
        if (!(o instanceof PaymentMethodsDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentMethodsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", disabled='" + isDisabled() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", iconId=" + getIconId() +
            ", iconThumbnailUrl='" + getIconThumbnailUrl() + "'" +
            "}";
    }
}
