package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CitiesCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CitiesCulture} and its DTO {@link CitiesCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, CitiesMapper.class})
public interface CitiesCultureMapper extends EntityMapper<CitiesCultureDTO, CitiesCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    CitiesCultureDTO toDto(CitiesCulture citiesCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "cityId", target = "city")
    CitiesCulture toEntity(CitiesCultureDTO citiesCultureDTO);

    default CitiesCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        CitiesCulture citiesCulture = new CitiesCulture();
        citiesCulture.setId(id);
        return citiesCulture;
    }
}
