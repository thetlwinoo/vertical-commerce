package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSet} and its DTO {@link ProductSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductSetMapper extends EntityMapper<ProductSetDTO, ProductSet> {



    default ProductSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSet productSet = new ProductSet();
        productSet.setId(id);
        return productSet;
    }
}
