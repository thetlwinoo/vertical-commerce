package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.DistrictsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Districts} and its DTO {@link DistrictsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistrictsMapper extends EntityMapper<DistrictsDTO, Districts> {



    default Districts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Districts districts = new Districts();
        districts.setId(id);
        return districts;
    }
}
