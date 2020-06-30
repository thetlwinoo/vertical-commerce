package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.DiscountTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiscountTypes} and its DTO {@link DiscountTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiscountTypesMapper extends EntityMapper<DiscountTypesDTO, DiscountTypes> {



    default DiscountTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        DiscountTypes discountTypes = new DiscountTypes();
        discountTypes.setId(id);
        return discountTypes;
    }
}
