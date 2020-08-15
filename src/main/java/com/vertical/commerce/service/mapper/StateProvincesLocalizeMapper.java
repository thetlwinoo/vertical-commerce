package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.StateProvincesLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StateProvincesLocalize} and its DTO {@link StateProvincesLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, StateProvincesMapper.class})
public interface StateProvincesLocalizeMapper extends EntityMapper<StateProvincesLocalizeDTO, StateProvincesLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "stateProvince.id", target = "stateProvinceId")
    @Mapping(source = "stateProvince.name", target = "stateProvinceName")
    StateProvincesLocalizeDTO toDto(StateProvincesLocalize stateProvincesLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "stateProvinceId", target = "stateProvince")
    StateProvincesLocalize toEntity(StateProvincesLocalizeDTO stateProvincesLocalizeDTO);

    default StateProvincesLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        StateProvincesLocalize stateProvincesLocalize = new StateProvincesLocalize();
        stateProvincesLocalize.setId(id);
        return stateProvincesLocalize;
    }
}
