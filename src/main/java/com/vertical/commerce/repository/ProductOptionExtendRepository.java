package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductOption;

import java.util.List;

public interface ProductOptionExtendRepository extends ProductOptionRepository {
    List<ProductOption> findAllByProductOptionSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
