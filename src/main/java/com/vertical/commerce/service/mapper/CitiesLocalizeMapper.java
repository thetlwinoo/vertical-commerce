package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CitiesLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CitiesLocalize} and its DTO {@link CitiesLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, CitiesMapper.class})
public interface CitiesLocalizeMapper extends EntityMapper<CitiesLocalizeDTO, CitiesLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    CitiesLocalizeDTO toDto(CitiesLocalize citiesLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "cityId", target = "city")
    CitiesLocalize toEntity(CitiesLocalizeDTO citiesLocalizeDTO);

    default CitiesLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        CitiesLocalize citiesLocalize = new CitiesLocalize();
        citiesLocalize.setId(id);
        return citiesLocalize;
    }
}
