package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TownsLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TownsLocalize} and its DTO {@link TownsLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, TownsMapper.class})
public interface TownsLocalizeMapper extends EntityMapper<TownsLocalizeDTO, TownsLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "town.id", target = "townId")
    @Mapping(source = "town.name", target = "townName")
    TownsLocalizeDTO toDto(TownsLocalize townsLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "townId", target = "town")
    TownsLocalize toEntity(TownsLocalizeDTO townsLocalizeDTO);

    default TownsLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        TownsLocalize townsLocalize = new TownsLocalize();
        townsLocalize.setId(id);
        return townsLocalize;
    }
}
