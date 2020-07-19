package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.DiscountDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiscountDetails} and its DTO {@link DiscountDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {DiscountsMapper.class, StockItemsMapper.class, ProductCategoryMapper.class})
public interface DiscountDetailsMapper extends EntityMapper<DiscountDetailsDTO, DiscountDetails> {

    @Mapping(source = "discount.id", target = "discountId")
    @Mapping(source = "discount.name", target = "discountName")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    DiscountDetailsDTO toDto(DiscountDetails discountDetails);

    @Mapping(source = "discountId", target = "discount")
    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "productCategoryId", target = "productCategory")
    DiscountDetails toEntity(DiscountDetailsDTO discountDetailsDTO);

    default DiscountDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        DiscountDetails discountDetails = new DiscountDetails();
        discountDetails.setId(id);
        return discountDetails;
    }
}
