package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductDocumentsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDocumentsCulture} and its DTO {@link ProductDocumentsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, ProductDocumentsMapper.class})
public interface ProductDocumentsCultureMapper extends EntityMapper<ProductDocumentsCultureDTO, ProductDocumentsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "productDocument.id", target = "productDocumentId")
    ProductDocumentsCultureDTO toDto(ProductDocumentsCulture productDocumentsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "productDocumentId", target = "productDocument")
    ProductDocumentsCulture toEntity(ProductDocumentsCultureDTO productDocumentsCultureDTO);

    default ProductDocumentsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductDocumentsCulture productDocumentsCulture = new ProductDocumentsCulture();
        productDocumentsCulture.setId(id);
        return productDocumentsCulture;
    }
}
