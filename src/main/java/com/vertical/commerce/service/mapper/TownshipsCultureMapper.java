package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TownshipsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TownshipsCulture} and its DTO {@link TownshipsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, TownshipsMapper.class})
public interface TownshipsCultureMapper extends EntityMapper<TownshipsCultureDTO, TownshipsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "township.id", target = "townshipId")
    @Mapping(source = "township.name", target = "townshipName")
    TownshipsCultureDTO toDto(TownshipsCulture townshipsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "townshipId", target = "township")
    TownshipsCulture toEntity(TownshipsCultureDTO townshipsCultureDTO);

    default TownshipsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        TownshipsCulture townshipsCulture = new TownshipsCulture();
        townshipsCulture.setId(id);
        return townshipsCulture;
    }
}
