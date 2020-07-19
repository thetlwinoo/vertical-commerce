package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.RegionsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegionsCulture} and its DTO {@link RegionsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, RegionsMapper.class})
public interface RegionsCultureMapper extends EntityMapper<RegionsCultureDTO, RegionsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    RegionsCultureDTO toDto(RegionsCulture regionsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "regionId", target = "region")
    RegionsCulture toEntity(RegionsCultureDTO regionsCultureDTO);

    default RegionsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegionsCulture regionsCulture = new RegionsCulture();
        regionsCulture.setId(id);
        return regionsCulture;
    }
}
