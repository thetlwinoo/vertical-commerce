package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductDocumentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDocuments} and its DTO {@link ProductDocumentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, WarrantyTypesMapper.class})
public interface ProductDocumentsMapper extends EntityMapper<ProductDocumentsDTO, ProductDocuments> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "warrantyType.id", target = "warrantyTypeId")
    @Mapping(source = "warrantyType.name", target = "warrantyTypeName")
    ProductDocumentsDTO toDto(ProductDocuments productDocuments);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "warrantyTypeId", target = "warrantyType")
    ProductDocuments toEntity(ProductDocumentsDTO productDocumentsDTO);

    default ProductDocuments fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductDocuments productDocuments = new ProductDocuments();
        productDocuments.setId(id);
        return productDocuments;
    }
}
