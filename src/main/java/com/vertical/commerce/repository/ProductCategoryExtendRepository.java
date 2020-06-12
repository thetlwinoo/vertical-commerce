package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryExtendRepository extends ProductCategoryRepository {
    List<ProductCategory> findAllByParentIdIsNullOrderBySortOrder();

    List<ProductCategory> findAllByParentIdIsNullAndShowInNavIndIsTrueOrderBySortOrder();

    List<ProductCategory> findAllByParentIdOrderBySortOrder(Long parentId);

    ProductCategory findProductCategoryByNameContaining(String categoryName);
}
