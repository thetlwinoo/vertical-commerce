package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.MaterialsLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MaterialsLocalize} and its DTO {@link MaterialsLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, MaterialsMapper.class})
public interface MaterialsLocalizeMapper extends EntityMapper<MaterialsLocalizeDTO, MaterialsLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "material.name", target = "materialName")
    MaterialsLocalizeDTO toDto(MaterialsLocalize materialsLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "materialId", target = "material")
    MaterialsLocalize toEntity(MaterialsLocalizeDTO materialsLocalizeDTO);

    default MaterialsLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        MaterialsLocalize materialsLocalize = new MaterialsLocalize();
        materialsLocalize.setId(id);
        return materialsLocalize;
    }
}
