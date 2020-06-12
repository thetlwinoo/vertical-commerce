package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.SystemParameters} entity.
 */
public class SystemParametersDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String applicationSettings;


    private Long deliveryCityId;

    private String deliveryCityName;

    private Long postalCityId;

    private String postalCityName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationSettings() {
        return applicationSettings;
    }

    public void setApplicationSettings(String applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    public Long getDeliveryCityId() {
        return deliveryCityId;
    }

    public void setDeliveryCityId(Long citiesId) {
        this.deliveryCityId = citiesId;
    }

    public String getDeliveryCityName() {
        return deliveryCityName;
    }

    public void setDeliveryCityName(String citiesName) {
        this.deliveryCityName = citiesName;
    }

    public Long getPostalCityId() {
        return postalCityId;
    }

    public void setPostalCityId(Long citiesId) {
        this.postalCityId = citiesId;
    }

    public String getPostalCityName() {
        return postalCityName;
    }

    public void setPostalCityName(String citiesName) {
        this.postalCityName = citiesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemParametersDTO)) {
            return false;
        }

        return id != null && id.equals(((SystemParametersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemParametersDTO{" +
            "id=" + getId() +
            ", applicationSettings='" + getApplicationSettings() + "'" +
            ", deliveryCityId=" + getDeliveryCityId() +
            ", deliveryCityName='" + getDeliveryCityName() + "'" +
            ", postalCityId=" + getPostalCityId() +
            ", postalCityName='" + getPostalCityName() + "'" +
            "}";
    }
}
