package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductsCulture} and its DTO {@link ProductsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductsMapper.class})
public interface ProductsCultureMapper extends EntityMapper<ProductsCultureDTO, ProductsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductsCultureDTO toDto(ProductsCulture productsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productId", target = "product")
    ProductsCulture toEntity(ProductsCultureDTO productsCultureDTO);

    default ProductsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductsCulture productsCulture = new ProductsCulture();
        productsCulture.setId(id);
        return productsCulture;
    }
}
