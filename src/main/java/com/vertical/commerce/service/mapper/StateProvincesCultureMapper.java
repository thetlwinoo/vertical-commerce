package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.StateProvincesCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StateProvincesCulture} and its DTO {@link StateProvincesCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, StateProvincesMapper.class})
public interface StateProvincesCultureMapper extends EntityMapper<StateProvincesCultureDTO, StateProvincesCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "stateProvince.id", target = "stateProvinceId")
    @Mapping(source = "stateProvince.name", target = "stateProvinceName")
    StateProvincesCultureDTO toDto(StateProvincesCulture stateProvincesCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "stateProvinceId", target = "stateProvince")
    StateProvincesCulture toEntity(StateProvincesCultureDTO stateProvincesCultureDTO);

    default StateProvincesCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        StateProvincesCulture stateProvincesCulture = new StateProvincesCulture();
        stateProvincesCulture.setId(id);
        return stateProvincesCulture;
    }
}
