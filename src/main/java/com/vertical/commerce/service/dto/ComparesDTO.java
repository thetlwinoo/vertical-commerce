package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Compares} entity.
 */
public class ComparesDTO implements Serializable {
    
    private Long id;


    private Long compareUserId;

    private String compareUserFullName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompareUserId() {
        return compareUserId;
    }

    public void setCompareUserId(Long peopleId) {
        this.compareUserId = peopleId;
    }

    public String getCompareUserFullName() {
        return compareUserFullName;
    }

    public void setCompareUserFullName(String peopleFullName) {
        this.compareUserFullName = peopleFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComparesDTO)) {
            return false;
        }

        return id != null && id.equals(((ComparesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComparesDTO{" +
            "id=" + getId() +
            ", compareUserId=" + getCompareUserId() +
            ", compareUserFullName='" + getCompareUserFullName() + "'" +
            "}";
    }
}
