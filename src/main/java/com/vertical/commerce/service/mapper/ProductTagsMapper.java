package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductTagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTags} and its DTO {@link ProductTagsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductTagsMapper extends EntityMapper<ProductTagsDTO, ProductTags> {



    default ProductTags fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductTags productTags = new ProductTags();
        productTags.setId(id);
        return productTags;
    }
}
