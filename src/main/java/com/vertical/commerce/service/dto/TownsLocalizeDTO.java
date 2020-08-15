package com.vertical.commerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.TownsLocalize} entity.
 */
public class TownsLocalizeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long cultureId;

    private String cultureCode;

    private Long townId;

    private String townName;
    
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

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townsId) {
        this.townId = townsId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townsName) {
        this.townName = townsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownsLocalizeDTO)) {
            return false;
        }

        return id != null && id.equals(((TownsLocalizeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownsLocalizeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureId=" + getCultureId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", townId=" + getTownId() +
            ", townName='" + getTownName() + "'" +
            "}";
    }
}
