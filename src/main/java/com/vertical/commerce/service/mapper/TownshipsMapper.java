package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TownshipsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Townships} and its DTO {@link TownshipsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CitiesMapper.class})
public interface TownshipsMapper extends EntityMapper<TownshipsDTO, Townships> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    TownshipsDTO toDto(Townships townships);

    @Mapping(source = "cityId", target = "city")
    Townships toEntity(TownshipsDTO townshipsDTO);

    default Townships fromId(Long id) {
        if (id == null) {
            return null;
        }
        Townships townships = new Townships();
        townships.setId(id);
        return townships;
    }
}
