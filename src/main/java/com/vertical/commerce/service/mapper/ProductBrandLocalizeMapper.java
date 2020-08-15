package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductBrandLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductBrandLocalize} and its DTO {@link ProductBrandLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductBrandMapper.class})
public interface ProductBrandLocalizeMapper extends EntityMapper<ProductBrandLocalizeDTO, ProductBrandLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "productBrand.id", target = "productBrandId")
    @Mapping(source = "productBrand.name", target = "productBrandName")
    ProductBrandLocalizeDTO toDto(ProductBrandLocalize productBrandLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productBrandId", target = "productBrand")
    ProductBrandLocalize toEntity(ProductBrandLocalizeDTO productBrandLocalizeDTO);

    default ProductBrandLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductBrandLocalize productBrandLocalize = new ProductBrandLocalize();
        productBrandLocalize.setId(id);
        return productBrandLocalize;
    }
}
