package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.StateProvincesCulture} entity.
 */
public class StateProvincesCultureDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long stateProvinceId;

    private String stateProvinceName;
    
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

    public Long getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(Long stateProvincesId) {
        this.stateProvinceId = stateProvincesId;
    }

    public String getStateProvinceName() {
        return stateProvinceName;
    }

    public void setStateProvinceName(String stateProvincesName) {
        this.stateProvinceName = stateProvincesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateProvincesCultureDTO)) {
            return false;
        }

        return id != null && id.equals(((StateProvincesCultureDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateProvincesCultureDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", stateProvinceId=" + getStateProvinceId() +
            ", stateProvinceName='" + getStateProvinceName() + "'" +
            "}";
    }
}
