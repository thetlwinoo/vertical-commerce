package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.DistrictsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DistrictsCulture} and its DTO {@link DistrictsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, DistrictsMapper.class})
public interface DistrictsCultureMapper extends EntityMapper<DistrictsCultureDTO, DistrictsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "distinct.id", target = "distinctId")
    @Mapping(source = "distinct.name", target = "distinctName")
    DistrictsCultureDTO toDto(DistrictsCulture districtsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "distinctId", target = "distinct")
    DistrictsCulture toEntity(DistrictsCultureDTO districtsCultureDTO);

    default DistrictsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        DistrictsCulture districtsCulture = new DistrictsCulture();
        districtsCulture.setId(id);
        return districtsCulture;
    }
}
