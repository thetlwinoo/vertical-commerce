package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CardTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CardTypes} and its DTO {@link CardTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardTypesMapper extends EntityMapper<CardTypesDTO, CardTypes> {



    default CardTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        CardTypes cardTypes = new CardTypes();
        cardTypes.setId(id);
        return cardTypes;
    }
}
