package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CreditCardTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCardType} and its DTO {@link CreditCardTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditCardTypeMapper extends EntityMapper<CreditCardTypeDTO, CreditCardType> {



    default CreditCardType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditCardType creditCardType = new CreditCardType();
        creditCardType.setId(id);
        return creditCardType;
    }
}
