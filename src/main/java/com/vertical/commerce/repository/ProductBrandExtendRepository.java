package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.service.dto.ProductBrandDTO;

public interface ProductBrandExtendRepository extends ProductBrandRepository {
    ProductBrand findDistinctTopByNameEquals(String name);

    Boolean existsByName(String name);
}
