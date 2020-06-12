package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.BusinessEntityAddress} entity.
 */
public class BusinessEntityAddressDTO implements Serializable {
    
    private Long id;


    private Long addressId;

    private Long personId;

    private Long addressTypeId;

    private String addressTypeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressesId) {
        this.addressId = addressesId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public Long getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Long addressTypesId) {
        this.addressTypeId = addressTypesId;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypesName) {
        this.addressTypeName = addressTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessEntityAddressDTO)) {
            return false;
        }

        return id != null && id.equals(((BusinessEntityAddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessEntityAddressDTO{" +
            "id=" + getId() +
            ", addressId=" + getAddressId() +
            ", personId=" + getPersonId() +
            ", addressTypeId=" + getAddressTypeId() +
            ", addressTypeName='" + getAddressTypeName() + "'" +
            "}";
    }
}
