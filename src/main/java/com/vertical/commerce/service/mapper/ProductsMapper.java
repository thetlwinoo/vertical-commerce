package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Products} and its DTO {@link ProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, ProductCategoryMapper.class, ProductBrandMapper.class})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "productBrand.id", target = "productBrandId")
    @Mapping(source = "productBrand.name", target = "productBrandName")
    ProductsDTO toDto(Products products);

    @Mapping(target = "stockItemLists", ignore = true)
    @Mapping(target = "removeStockItemList", ignore = true)
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productBrandId", target = "productBrand")
    @Mapping(target = "productDocument", ignore = true)
    Products toEntity(ProductsDTO productsDTO);

    default Products fromId(Long id) {
        if (id == null) {
            return null;
        }
        Products products = new Products();
        products.setId(id);
        return products;
    }
}
