package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CountriesCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountriesCulture} and its DTO {@link CountriesCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, CountriesMapper.class})
public interface CountriesCultureMapper extends EntityMapper<CountriesCultureDTO, CountriesCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    CountriesCultureDTO toDto(CountriesCulture countriesCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "countryId", target = "country")
    CountriesCulture toEntity(CountriesCultureDTO countriesCultureDTO);

    default CountriesCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        CountriesCulture countriesCulture = new CountriesCulture();
        countriesCulture.setId(id);
        return countriesCulture;
    }
}
