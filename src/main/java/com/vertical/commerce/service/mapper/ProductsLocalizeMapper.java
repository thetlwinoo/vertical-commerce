package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductsLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductsLocalize} and its DTO {@link ProductsLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductsMapper.class})
public interface ProductsLocalizeMapper extends EntityMapper<ProductsLocalizeDTO, ProductsLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductsLocalizeDTO toDto(ProductsLocalize productsLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productId", target = "product")
    ProductsLocalize toEntity(ProductsLocalizeDTO productsLocalizeDTO);

    default ProductsLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductsLocalize productsLocalize = new ProductsLocalize();
        productsLocalize.setId(id);
        return productsLocalize;
    }
}
