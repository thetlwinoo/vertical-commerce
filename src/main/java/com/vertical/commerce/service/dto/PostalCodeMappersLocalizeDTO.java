package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.PostalCodeMappersLocalize} entity.
 */
public class PostalCodeMappersLocalizeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long postalCodeMapperId;

    private String postalCodeMapperName;
    
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

    public Long getCultureId() {
        return cultureId;
    }

    public void setCultureId(Long cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureCode() {
        return cultureCode;
    }

    public void setCultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
    }

    public Long getPostalCodeMapperId() {
        return postalCodeMapperId;
    }

    public void setPostalCodeMapperId(Long postalCodeMappersId) {
        this.postalCodeMapperId = postalCodeMappersId;
    }

    public String getPostalCodeMapperName() {
        return postalCodeMapperName;
    }

    public void setPostalCodeMapperName(String postalCodeMappersName) {
        this.postalCodeMapperName = postalCodeMappersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostalCodeMappersLocalizeDTO)) {
            return false;
        }

        return id != null && id.equals(((PostalCodeMappersLocalizeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostalCodeMappersLocalizeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", postalCodeMapperId=" + getPostalCodeMapperId() +
            ", postalCodeMapperName='" + getPostalCodeMapperName() + "'" +
            "}";
    }
}
