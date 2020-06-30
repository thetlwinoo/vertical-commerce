package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Zone} and its DTO {@link ZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {CitiesMapper.class})
public interface ZoneMapper extends EntityMapper<ZoneDTO, Zone> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    ZoneDTO toDto(Zone zone);

    @Mapping(source = "cityId", target = "city")
    Zone toEntity(ZoneDTO zoneDTO);

    default Zone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Zone zone = new Zone();
        zone.setId(id);
        return zone;
    }
}
