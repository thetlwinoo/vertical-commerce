package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.PaymentMethods} entity.
 */
public class PaymentMethodsDTO implements Serializable {
    
    private Long id;

    private String name;

    private String code;

    private Boolean disabled;

    private Integer sortOrder;

    private String iconFont;

    private String iconPhoto;

    @NotNull
    private Instant validFrom;

    private Instant validTo;

    
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
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", iconPhoto='" + getIconPhoto() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
