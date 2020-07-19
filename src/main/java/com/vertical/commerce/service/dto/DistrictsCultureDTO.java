package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.DistrictsCulture} entity.
 */
public class DistrictsCultureDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long distinctId;

    private String distinctName;
    
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

    public Long getDistinctId() {
        return distinctId;
    }

    public void setDistinctId(Long districtsId) {
        this.distinctId = districtsId;
    }

    public String getDistinctName() {
        return distinctName;
    }

    public void setDistinctName(String districtsName) {
        this.distinctName = districtsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictsCultureDTO)) {
            return false;
        }

        return id != null && id.equals(((DistrictsCultureDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictsCultureDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", distinctId=" + getDistinctId() +
            ", distinctName='" + getDistinctName() + "'" +
            "}";
    }
}
