package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductBrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductBrand} and its DTO {@link ProductBrandDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, PhotosMapper.class})
public interface ProductBrandMapper extends EntityMapper<ProductBrandDTO, ProductBrand> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "icon.id", target = "iconId")
    @Mapping(source = "icon.thumbnailUrl", target = "iconThumbnailUrl")
    ProductBrandDTO toDto(ProductBrand productBrand);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "iconId", target = "icon")
    ProductBrand toEntity(ProductBrandDTO productBrandDTO);

    default ProductBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(id);
        return productBrand;
    }
}
