package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.BarcodeTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BarcodeTypes} and its DTO {@link BarcodeTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BarcodeTypesMapper extends EntityMapper<BarcodeTypesDTO, BarcodeTypes> {



    default BarcodeTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        BarcodeTypes barcodeTypes = new BarcodeTypes();
        barcodeTypes.setId(id);
        return barcodeTypes;
    }
}
