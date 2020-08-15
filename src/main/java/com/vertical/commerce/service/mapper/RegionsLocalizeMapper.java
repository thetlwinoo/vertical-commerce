package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.RegionsLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegionsLocalize} and its DTO {@link RegionsLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, RegionsMapper.class})
public interface RegionsLocalizeMapper extends EntityMapper<RegionsLocalizeDTO, RegionsLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    RegionsLocalizeDTO toDto(RegionsLocalize regionsLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "regionId", target = "region")
    RegionsLocalize toEntity(RegionsLocalizeDTO regionsLocalizeDTO);

    default RegionsLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegionsLocalize regionsLocalize = new RegionsLocalize();
        regionsLocalize.setId(id);
        return regionsLocalize;
    }
}
