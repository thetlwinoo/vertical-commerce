package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CardsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cards} and its DTO {@link CardsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardsMapper extends EntityMapper<CardsDTO, Cards> {



    default Cards fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cards cards = new Cards();
        cards.setId(id);
        return cards;
    }
}
