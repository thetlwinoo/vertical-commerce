package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TownsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Towns} and its DTO {@link TownsDTO}.
 */
@Mapper(componentModel = "spring", uses = {TownshipsMapper.class})
public interface TownsMapper extends EntityMapper<TownsDTO, Towns> {

    @Mapping(source = "township.id", target = "townshipId")
    @Mapping(source = "township.name", target = "townshipName")
    TownsDTO toDto(Towns towns);

    @Mapping(source = "townshipId", target = "township")
    Towns toEntity(TownsDTO townsDTO);

    default Towns fromId(Long id) {
        if (id == null) {
            return null;
        }
        Towns towns = new Towns();
        towns.setId(id);
        return towns;
    }
}
