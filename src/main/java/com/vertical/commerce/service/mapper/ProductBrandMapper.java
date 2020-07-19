package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductBrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductBrand} and its DTO {@link ProductBrandDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductBrandMapper extends EntityMapper<ProductBrandDTO, ProductBrand> {



    default ProductBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(id);
        return productBrand;
    }
}
