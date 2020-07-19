package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TownsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TownsCulture} and its DTO {@link TownsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TownsCultureMapper extends EntityMapper<TownsCultureDTO, TownsCulture> {



    default TownsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        TownsCulture townsCulture = new TownsCulture();
        townsCulture.setId(id);
        return townsCulture;
    }
}
