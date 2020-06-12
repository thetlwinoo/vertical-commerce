package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.BusinessEntityContact} entity.
 */
public class BusinessEntityContactDTO implements Serializable {
    
    private Long id;


    private Long personId;

    private Long contactTypeId;

    private String contactTypeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public Long getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Long contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessEntityContactDTO)) {
            return false;
        }

        return id != null && id.equals(((BusinessEntityContactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessEntityContactDTO{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            ", contactTypeId=" + getContactTypeId() +
            ", contactTypeName='" + getContactTypeName() + "'" +
            "}";
    }
}
