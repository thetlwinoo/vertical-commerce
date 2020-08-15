package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.TownshipsLocalize} entity.
 */
public class TownshipsLocalizeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long townshipId;

    private String townshipName;
    
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

    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipsId) {
        this.townshipId = townshipsId;
    }

    public String getTownshipName() {
        return townshipName;
    }

    public void setTownshipName(String townshipsName) {
        this.townshipName = townshipsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownshipsLocalizeDTO)) {
            return false;
        }

        return id != null && id.equals(((TownshipsLocalizeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownshipsLocalizeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", townshipId=" + getTownshipId() +
            ", townshipName='" + getTownshipName() + "'" +
            "}";
    }
}
