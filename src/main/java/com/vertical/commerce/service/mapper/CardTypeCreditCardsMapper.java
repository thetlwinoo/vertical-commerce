package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CardTypeCreditCardsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CardTypeCreditCards} and its DTO {@link CardTypeCreditCardsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardTypeCreditCardsMapper extends EntityMapper<CardTypeCreditCardsDTO, CardTypeCreditCards> {



    default CardTypeCreditCards fromId(Long id) {
        if (id == null) {
            return null;
        }
        CardTypeCreditCards cardTypeCreditCards = new CardTypeCreditCards();
        cardTypeCreditCards.setId(id);
        return cardTypeCreditCards;
    }
}
