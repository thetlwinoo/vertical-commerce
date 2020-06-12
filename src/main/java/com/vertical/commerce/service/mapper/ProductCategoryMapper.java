package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {PhotosMapper.class})
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "icon.id", target = "iconId")
    @Mapping(source = "icon.thumbnailUrl", target = "iconThumbnailUrl")
    ProductCategoryDTO toDto(ProductCategory productCategory);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(source = "iconId", target = "icon")
    ProductCategory toEntity(ProductCategoryDTO productCategoryDTO);

    default ProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        return productCategory;
    }
}
