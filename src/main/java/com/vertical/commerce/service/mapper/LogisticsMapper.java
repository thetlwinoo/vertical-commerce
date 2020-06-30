package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.LogisticsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Logistics} and its DTO {@link LogisticsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogisticsMapper extends EntityMapper<LogisticsDTO, Logistics> {



    default Logistics fromId(Long id) {
        if (id == null) {
            return null;
        }
        Logistics logistics = new Logistics();
        logistics.setId(id);
        return logistics;
    }
}
