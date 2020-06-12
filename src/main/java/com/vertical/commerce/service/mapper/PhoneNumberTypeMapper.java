package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.PhoneNumberTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhoneNumberType} and its DTO {@link PhoneNumberTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhoneNumberTypeMapper extends EntityMapper<PhoneNumberTypeDTO, PhoneNumberType> {



    default PhoneNumberType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PhoneNumberType phoneNumberType = new PhoneNumberType();
        phoneNumberType.setId(id);
        return phoneNumberType;
    }
}
