package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CountriesLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountriesLocalize} and its DTO {@link CountriesLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, CountriesMapper.class})
public interface CountriesLocalizeMapper extends EntityMapper<CountriesLocalizeDTO, CountriesLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    CountriesLocalizeDTO toDto(CountriesLocalize countriesLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "countryId", target = "country")
    CountriesLocalize toEntity(CountriesLocalizeDTO countriesLocalizeDTO);

    default CountriesLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        CountriesLocalize countriesLocalize = new CountriesLocalize();
        countriesLocalize.setId(id);
        return countriesLocalize;
    }
}
