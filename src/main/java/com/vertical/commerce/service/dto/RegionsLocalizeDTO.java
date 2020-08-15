package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.RegionsLocalize} entity.
 */
public class RegionsLocalizeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long regionId;

    private String regionName;
    
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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionsId) {
        this.regionId = regionsId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionsName) {
        this.regionName = regionsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionsLocalizeDTO)) {
            return false;
        }

        return id != null && id.equals(((RegionsLocalizeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegionsLocalizeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", regionId=" + getRegionId() +
            ", regionName='" + getRegionName() + "'" +
            "}";
    }
}
