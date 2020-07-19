package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductCategoryCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategoryCulture} and its DTO {@link ProductCategoryCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductCategoryMapper.class})
public interface ProductCategoryCultureMapper extends EntityMapper<ProductCategoryCultureDTO, ProductCategoryCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    ProductCategoryCultureDTO toDto(ProductCategoryCulture productCategoryCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productCategoryId", target = "productCategory")
    ProductCategoryCulture toEntity(ProductCategoryCultureDTO productCategoryCultureDTO);

    default ProductCategoryCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategoryCulture productCategoryCulture = new ProductCategoryCulture();
        productCategoryCulture.setId(id);
        return productCategoryCulture;
    }
}
