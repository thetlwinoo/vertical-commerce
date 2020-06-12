package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ShipMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShipMethod} and its DTO {@link ShipMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipMethodMapper extends EntityMapper<ShipMethodDTO, ShipMethod> {



    default ShipMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipMethod shipMethod = new ShipMethod();
        shipMethod.setId(id);
        return shipMethod;
    }
}
