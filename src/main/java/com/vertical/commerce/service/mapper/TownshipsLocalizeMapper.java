package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TownshipsLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TownshipsLocalize} and its DTO {@link TownshipsLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, TownshipsMapper.class})
public interface TownshipsLocalizeMapper extends EntityMapper<TownshipsLocalizeDTO, TownshipsLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "township.id", target = "townshipId")
    @Mapping(source = "township.name", target = "townshipName")
    TownshipsLocalizeDTO toDto(TownshipsLocalize townshipsLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "townshipId", target = "township")
    TownshipsLocalize toEntity(TownshipsLocalizeDTO townshipsLocalizeDTO);

    default TownshipsLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        TownshipsLocalize townshipsLocalize = new TownshipsLocalize();
        townshipsLocalize.setId(id);
        return townshipsLocalize;
    }
}
