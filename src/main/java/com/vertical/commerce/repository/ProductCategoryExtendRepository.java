package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryExtendRepository extends ProductCategoryRepository {
    List<ProductCategory> findAllByParentIdIsNullOrderBySortOrder();

//    List<ProductCategory> findAllByParentIdIsNullAndShowInNavIndIsTrueOrderBySortOrder();

    List<ProductCategory> findAllByParentIdOrderBySortOrder(Long parentId);

    ProductCategory findProductCategoryByNameContaining(String categoryName);

    @Query(value = "SELECT * FROM product_category_tree(:idList)",nativeQuery = true)
    String getProductCategoryTree(@Param("idList") String idList);
}
