package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.MaterialsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MaterialsCulture} and its DTO {@link MaterialsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, MaterialsMapper.class})
public interface MaterialsCultureMapper extends EntityMapper<MaterialsCultureDTO, MaterialsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "material.name", target = "materialName")
    MaterialsCultureDTO toDto(MaterialsCulture materialsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "materialId", target = "material")
    MaterialsCulture toEntity(MaterialsCultureDTO materialsCultureDTO);

    default MaterialsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        MaterialsCulture materialsCulture = new MaterialsCulture();
        materialsCulture.setId(id);
        return materialsCulture;
    }
}
