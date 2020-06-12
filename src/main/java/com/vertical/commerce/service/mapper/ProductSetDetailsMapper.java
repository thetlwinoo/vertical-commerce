package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductSetDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSetDetails} and its DTO {@link ProductSetDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductSetMapper.class, ProductsMapper.class, ProductCategoryMapper.class})
public interface ProductSetDetailsMapper extends EntityMapper<ProductSetDetailsDTO, ProductSetDetails> {

    @Mapping(source = "productSet.id", target = "productSetId")
    @Mapping(source = "productSet.name", target = "productSetName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    ProductSetDetailsDTO toDto(ProductSetDetails productSetDetails);

    @Mapping(source = "productSetId", target = "productSet")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "productCategoryId", target = "productCategory")
    ProductSetDetails toEntity(ProductSetDetailsDTO productSetDetailsDTO);

    default ProductSetDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSetDetails productSetDetails = new ProductSetDetails();
        productSetDetails.setId(id);
        return productSetDetails;
    }
}
