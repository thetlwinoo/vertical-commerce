package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ProductListPriceHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductListPriceHistory} and its DTO {@link ProductListPriceHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class})
public interface ProductListPriceHistoryMapper extends EntityMapper<ProductListPriceHistoryDTO, ProductListPriceHistory> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductListPriceHistoryDTO toDto(ProductListPriceHistory productListPriceHistory);

    @Mapping(source = "productId", target = "product")
    ProductListPriceHistory toEntity(ProductListPriceHistoryDTO productListPriceHistoryDTO);

    default ProductListPriceHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductListPriceHistory productListPriceHistory = new ProductListPriceHistory();
        productListPriceHistory.setId(id);
        return productListPriceHistory;
    }
}
