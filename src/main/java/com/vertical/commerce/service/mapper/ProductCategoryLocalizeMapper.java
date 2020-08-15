package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategoryLocalize} and its DTO {@link ProductCategoryLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductCategoryMapper.class})
public interface ProductCategoryLocalizeMapper extends EntityMapper<ProductCategoryLocalizeDTO, ProductCategoryLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    ProductCategoryLocalizeDTO toDto(ProductCategoryLocalize productCategoryLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productCategoryId", target = "productCategory")
    ProductCategoryLocalize toEntity(ProductCategoryLocalizeDTO productCategoryLocalizeDTO);

    default ProductCategoryLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategoryLocalize productCategoryLocalize = new ProductCategoryLocalize();
        productCategoryLocalize.setId(id);
        return productCategoryLocalize;
    }
}
