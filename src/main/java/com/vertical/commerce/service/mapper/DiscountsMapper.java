package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.DiscountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discounts} and its DTO {@link DiscountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, DiscountTypesMapper.class})
public interface DiscountsMapper extends EntityMapper<DiscountsDTO, Discounts> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "discountType.id", target = "discountTypeId")
    @Mapping(source = "discountType.name", target = "discountTypeName")
    DiscountsDTO toDto(Discounts discounts);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "discountTypeId", target = "discountType")
    Discounts toEntity(DiscountsDTO discountsDTO);

    default Discounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discounts discounts = new Discounts();
        discounts.setId(id);
        return discounts;
    }
}
