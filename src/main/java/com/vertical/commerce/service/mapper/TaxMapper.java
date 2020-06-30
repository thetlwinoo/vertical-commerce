package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TaxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tax} and its DTO {@link TaxDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaxClassMapper.class})
public interface TaxMapper extends EntityMapper<TaxDTO, Tax> {

    @Mapping(source = "taxClass.id", target = "taxClassId")
    @Mapping(source = "taxClass.name", target = "taxClassName")
    TaxDTO toDto(Tax tax);

    @Mapping(source = "taxClassId", target = "taxClass")
    Tax toEntity(TaxDTO taxDTO);

    default Tax fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tax tax = new Tax();
        tax.setId(id);
        return tax;
    }
}
