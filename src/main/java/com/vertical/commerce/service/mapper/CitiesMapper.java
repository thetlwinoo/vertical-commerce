package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CitiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cities} and its DTO {@link CitiesDTO}.
 */
@Mapper(componentModel = "spring", uses = {RegionsMapper.class})
public interface CitiesMapper extends EntityMapper<CitiesDTO, Cities> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    CitiesDTO toDto(Cities cities);

    @Mapping(source = "regionId", target = "region")
    Cities toEntity(CitiesDTO citiesDTO);

    default Cities fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cities cities = new Cities();
        cities.setId(id);
        return cities;
    }
}
