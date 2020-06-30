package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.DiscountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discount} and its DTO {@link DiscountDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, DiscountTypesMapper.class})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "discountType.id", target = "discountTypeId")
    @Mapping(source = "discountType.name", target = "discountTypeName")
    DiscountDTO toDto(Discount discount);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "discountTypeId", target = "discountType")
    Discount toEntity(DiscountDTO discountDTO);

    default Discount fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discount discount = new Discount();
        discount.setId(id);
        return discount;
    }
}
