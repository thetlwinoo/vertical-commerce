package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductAttribute;

import java.util.List;

public interface ProductAttributeExtendRepository extends ProductAttributeRepository {
    List<ProductAttribute> findAllByProductAttributeSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
