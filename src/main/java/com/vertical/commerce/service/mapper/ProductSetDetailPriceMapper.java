package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductSetDetailPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSetDetailPrice} and its DTO {@link ProductSetDetailPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductSetDetailsMapper.class})
public interface ProductSetDetailPriceMapper extends EntityMapper<ProductSetDetailPriceDTO, ProductSetDetailPrice> {

    @Mapping(source = "productSetDetail.id", target = "productSetDetailId")
    ProductSetDetailPriceDTO toDto(ProductSetDetailPrice productSetDetailPrice);

    @Mapping(source = "productSetDetailId", target = "productSetDetail")
    ProductSetDetailPrice toEntity(ProductSetDetailPriceDTO productSetDetailPriceDTO);

    default ProductSetDetailPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSetDetailPrice productSetDetailPrice = new ProductSetDetailPrice();
        productSetDetailPrice.setId(id);
        return productSetDetailPrice;
    }
}
