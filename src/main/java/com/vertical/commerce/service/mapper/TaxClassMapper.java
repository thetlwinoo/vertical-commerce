package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TaxClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaxClass} and its DTO {@link TaxClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaxClassMapper extends EntityMapper<TaxClassDTO, TaxClass> {



    default TaxClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaxClass taxClass = new TaxClass();
        taxClass.setId(id);
        return taxClass;
    }
}
