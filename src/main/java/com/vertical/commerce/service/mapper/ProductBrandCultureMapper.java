package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductBrandCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductBrandCulture} and its DTO {@link ProductBrandCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductBrandMapper.class})
public interface ProductBrandCultureMapper extends EntityMapper<ProductBrandCultureDTO, ProductBrandCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "productBrand.id", target = "productBrandId")
    @Mapping(source = "productBrand.name", target = "productBrandName")
    ProductBrandCultureDTO toDto(ProductBrandCulture productBrandCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productBrandId", target = "productBrand")
    ProductBrandCulture toEntity(ProductBrandCultureDTO productBrandCultureDTO);

    default ProductBrandCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductBrandCulture productBrandCulture = new ProductBrandCulture();
        productBrandCulture.setId(id);
        return productBrandCulture;
    }
}
