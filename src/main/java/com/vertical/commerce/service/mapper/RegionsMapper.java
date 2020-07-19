package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.RegionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Regions} and its DTO {@link RegionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateProvincesMapper.class})
public interface RegionsMapper extends EntityMapper<RegionsDTO, Regions> {

    @Mapping(source = "stateProvince.id", target = "stateProvinceId")
    @Mapping(source = "stateProvince.name", target = "stateProvinceName")
    RegionsDTO toDto(Regions regions);

    @Mapping(source = "stateProvinceId", target = "stateProvince")
    Regions toEntity(RegionsDTO regionsDTO);

    default Regions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regions regions = new Regions();
        regions.setId(id);
        return regions;
    }
}
